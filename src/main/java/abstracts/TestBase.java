package abstracts;

import abstracts.helpers.DriverLocator;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.*;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;

/**
 * Created by alinnitu on 7/6/2016.
 */
public abstract class TestBase {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected DriverLocator driverLocator;
    protected WebDriver driver;
    protected String serverUrl;

    @Rule
    public TestName name = new TestName();

    @Rule
    public TestWatcher testWatcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            long currentTime = System.currentTimeMillis();
            saveBrowserLogs(description.getMethodName(), currentTime);
            takeScreenshot(description.getMethodName(), currentTime);
        }

        @Override
        protected void finished(Description description) {
            shutdownDriver();
        }
    };

    @Before
    public void beforeTestJunit() {
        logger.info("--------- Starting Test-Method: {} ---------", name.getMethodName());
        serverUrl = System.getProperty("serverToBeTestedUrl");
        initializeDriver();
        openInitialPage();
    }

    protected String getSubDomain() {
        return "manager";
    }

    protected void initializeDriver() {
        driverLocator = new DriverLocator();
        driver = driverLocator.bootUpSystemForTest();
        enableScreenshot();
    }

    protected void enableScreenshot() {
        if (driver instanceof RemoteWebDriver && ((HasCapabilities) driver).getCapabilities().getBrowserName().equalsIgnoreCase("chrome")) {
            driver.get("chrome://extensions-frame");
            WebElement checkbox;
            do {
                checkbox = driver.findElement(By.xpath("//input[@type='checkbox']/ancestor::label[@class='incognito-control']"));
            } while (!checkbox.isDisplayed());
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }

    protected void openInitialPage() {
        try {
            driver.get(serverUrl + getContextPath() + getStartPage());
        } catch (WebDriverException e) {
            logger.debug("Could not open the page: {}", e);
            throw new WebDriverException("unable to navigate to initial page! " + getContextPath() + getStartPage(), e);
        }
    }

    protected void shutdownDriver() {
        logger.info("--------- Finished Test-Method: {} ---------", name.getMethodName());
        if (driverLocator != null)
            driverLocator.shutDown();
    }

//    @BeforeSuite
//    public void beforeSuite(ITestContext context) {
//        String suiteName = context.getSuite().getXmlSuite().getName();
//        logger.info("Running suite {}", suiteName);
//    }
//
//    @AfterSuite
//    public void setupAfterSuite(ITestContext context) {
//        logger.info("Finished SUITE: " + context.getSuite().getXmlSuite().getName());
//    }
//
//    @BeforeTest
//    public void beforeTest(ITestContext context) {
//        if (context == null)
//            return;
//        logger.info("# Starting TEST: {}", context.getCurrentXmlTest().getName());
//        serverUrl = System.getProperty("serverToBeTestedUrl",
//                context.getCurrentXmlTest().getParameter("serverToBeTestedUrl"));
//        if (serverUrl == null) {
//            throw new IllegalStateException("No server url has been specified");
//        }
//        logger.info("Testing against {}", serverUrl);
//    }
//
//    @AfterTest
//    public void afterTest(ITestContext context) {
//        logger.info("# Finished TEST: {}" + context.getCurrentXmlTest().getName());
//    }
//
//    @BeforeMethod
//    public void beforeMethod(Method method) {
//        if (method != null)
//            logger.info("# Starting METHOD: {}", method.getName());
//        initializeDriver();
//        openInitialPage();
//    }
//
//    @AfterMethod
//    public void afterMethod(Method method) {
//        try {
//            logger.info("# Finished METHOD: {}", method.getName());
//            takeScreenshot(method.getName(), System.currentTimeMillis());
////            saveBrowserLogs();
//        } catch (Exception e) {
//            logger.debug("Error after test: {} ", e.getStackTrace());
//        } finally {
//            shutdownDriver();
//        }
//    }

    protected void takeScreenshot(String method, long currentTime) {
        try {
            File screenshot = new File(method + "-" + currentTime + ".png");
            FileUtils.moveFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), screenshot);
            logger.info("taken screenshot: {} is saved in {}" + screenshot, screenshot.getAbsolutePath());
        } catch (Exception e1) {
            logger.error("Unable to take screenshot");
        }
    }

    private void saveBrowserLogs(String methodName, long currentTime) {
        try {
            BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(methodName + "-" + currentTime + ".log"));
            writeUrlWithError(bufferedWriter);
            writeLogsWithErrors(bufferedWriter);
            bufferedWriter.close();
        } catch (IOException e) {
            logger.error("Error saving browser logs ", e);
        }
    }

    private void writeUrlWithError(BufferedWriter bufferedWriter) throws IOException {
        for (String url : collectWindowsUrls()) {
            bufferedWriter.write(url + "\n\n");
        }
    }

    private List<String> collectWindowsUrls() {
        if (driver != null)
            return driver.getWindowHandles().stream()
                    .map(e -> driver.switchTo().window(e)).collect(Collectors.toList()).stream()
                    .map(e -> e.getCurrentUrl()).collect(Collectors.toList());
        else
            return new ArrayList<>();
    }

    private void writeLogsWithErrors(BufferedWriter bufferedWriter) throws IOException {
        if (driver != null)
            for (LogEntry logEntry : driver.manage().logs().get(LogType.BROWSER)) {
                bufferedWriter.write(logEntry.getTimestamp() + ": " + logEntry.getMessage() + "\n");
            }
    }

    protected WebDriver clickBrowsersBackButton() {
        driver.navigate().back();
        acceptAlertIfAny();
        return driver;
    }

    protected WebDriver clickBrowsersRefreshButton() {
        driver.navigate().refresh();
        acceptAlertIfAny();
        return driver;
    }

    protected WebDriver acceptAlertIfAny() {
        try {
            new WebDriverWait(driver, 10).until(alertIsPresent());
            driver.switchTo().alert().accept();
            new WebDriverWait(driver, 10).until(not(alertIsPresent()));
        } catch (TimeoutException te) {
            logger.info("Timeout occurred while waiting for alert!", te);
        }
        return this.driver;
    }

    protected void switchToTabWithHandle(String handle) {
        driver.switchTo().window(handle);
    }

    protected List<String> getBrowserLogs() {
        return (ArrayList<String>) ((JavascriptExecutor) driver).executeScript("return window.logs");
    }

    protected String getSessionId() {
        List<String> logs = getBrowserLogs();
        String sessionId = logs.stream().filter(e -> e.contains("sessionId=")).collect(Collectors.toList()).toString();
        sessionId = sessionId.substring(sessionId.indexOf('\'') + 1, sessionId.indexOf(", ") - 1);
        return sessionId;
    }

    protected abstract String getStartPage();

    protected abstract String getContextPath();
}
