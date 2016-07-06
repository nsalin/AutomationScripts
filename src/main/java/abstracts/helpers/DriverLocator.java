package abstracts.helpers;

import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

/**
 * Created by alinnitu on 7/6/2016.
 */
public class DriverLocator {
    private WebDriver driver;

    public WebDriver bootUpSystemForTest() {
        driver = getSeleniumDriver();
        return driver;
    }

    public void shutDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private WebDriver getSeleniumDriver() {
        String seleniumGridUrl = System.getProperty("seleniumGridURL");
        String seleniumBrowser = System.getProperty("seleniumBrowser");
        return seleniumGridUrl != null ? getRemoteWebDriver(seleniumGridUrl, seleniumBrowser) : getLocalDriver(seleniumBrowser);
    }

    private WebDriver getRemoteWebDriver(String seleniumGridURL, String seleniumBrowser) {
        try {
            return RemoteWebDriverProvider.get(seleniumGridURL, seleniumBrowser);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private WebDriver getLocalDriver(String seleniumBrowser) {
        return LocalWebDriverProvider.get(seleniumBrowser);
    }
}