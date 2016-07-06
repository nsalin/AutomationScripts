package abstracts.helpers;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by alinnitu on 7/6/2016.
 */
public class RemoteWebDriverProvider {

    private RemoteWebDriverProvider() {
    }

    public static WebDriver get(String seleniumGridURL, String seleniumBrowser) throws MalformedURLException {
        RemoteWebDriver remoteWebDriver;

       remoteWebDriver = new RemoteWebDriver(new URL(seleniumGridURL), getCapabilitiesForBrowser(seleniumBrowser));
        remoteWebDriver.setFileDetector(new LocalFileDetector());
        return remoteWebDriver;
    }

    private static Capabilities getCapabilitiesForBrowser(String seleniumBrowser) {
        if (seleniumBrowser.equals("iexplorer"))
            return WebDriverCapabilitiesProvider.getIECapabilities();
        else if (seleniumBrowser.equals("firefox"))
            return WebDriverCapabilitiesProvider.getFirefoxCapabilities();
        else
            return WebDriverCapabilitiesProvider.getChromeCapabilities();
    }
}