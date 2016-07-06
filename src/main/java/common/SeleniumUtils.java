package common;


import com.googlecode.junittoolbox.PollingWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.log4testng.Logger;

import java.util.Random;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Alin on 4/25/2015.
 */
public class SeleniumUtils {
    public WebDriver driver;
    private PollingWait wait = new PollingWait().timeoutAfter(30, SECONDS)
            .pollEvery(10, SECONDS);
    protected static final Logger logger = Logger.getLogger(SeleniumUtils.class);

    public SeleniumUtils(WebDriver driver){this.driver = driver;}
    public SeleniumUtils(){}

    public Integer getRandomNumberFromList(java.util.List<WebElement> arrayListName) {
        Random random = new Random();
        return random.nextInt(arrayListName.size());
    }

    public void fillInput(WebElement element, String data){
        wait.until(element::isDisplayed);
        element.click();
        element.clear();
        element.sendKeys(data);
    }
    public void clickOnElement(WebElement element) {
        try {
            element.click();
        } catch (Exception var1) {
            logger.debug("WebElement.click() didn\'t worked!");

            try {
                ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
            } catch (Exception var2) {
                logger.debug("JavascriptExecutor.click() didn\'t worked!");

                try {
                    (new Actions(driver)).click(element).perform();
                } catch (Exception var3) {
                    logger.debug("Actions.click() didn\'t worked!");
                }
            }
        }

    }

    public String generateUsername(){
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public String generatePassword(){
        return generateUsername() + "@ghost.com";
    }

    public void waitUntilPageIsLoaded(WebElement webElement){
        wait.pollEvery(2, SECONDS).timeoutAfter(20, SECONDS).until(webElement::isDisplayed);
    }
}


