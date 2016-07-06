package abstracts.helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * Created by alinnitu on 7/6/2016.
 */
public class LocalWebDriverProvider {

    public static WebDriver get(String seleniumBrowser) {
        if (seleniumBrowser == null)
            return new ChromeDriver(WebDriverCapabilitiesProvider.getChromeCapabilities());
        else if (seleniumBrowser.equals("iexplorer"))
            return new InternetExplorerDriver(WebDriverCapabilitiesProvider.getIECapabilities());
        else if (seleniumBrowser.equals("firefox"))
            return new FirefoxDriver(WebDriverCapabilitiesProvider.getFirefoxCapabilities());
        else
            return new ChromeDriver(WebDriverCapabilitiesProvider.getChromeCapabilities());
    }
}