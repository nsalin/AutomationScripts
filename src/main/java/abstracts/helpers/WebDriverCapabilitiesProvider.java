package abstracts.helpers;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Arrays;
import java.util.logging.Level;

/**
 * Created by alinntiu on 7/6/2016.
 */
public class WebDriverCapabilitiesProvider {

    public static Capabilities getChromeCapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        capabilities.setCapability("jenkins\\.label", "welci");
        capabilities.setCapability("chrome.switches", Arrays.asList("--disable-impl-side-painting"));
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        capabilities.setCapability(CapabilityType.PLATFORM, Platform.WINDOWS);
        capabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
        return capabilities;
    }

    public static Capabilities getFirefoxCapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability(CapabilityType.PLATFORM, Platform.XP);
        capabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, false);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.CLIENT, Level.SEVERE);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
        return capabilities;
    }

    public static Capabilities getIECapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(CapabilityType.PLATFORM, Platform.XP);
        capabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, false);
        capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.CLIENT, Level.SEVERE);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
        return capabilities;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        options.addArguments("start-maximized");
        options.addArguments("ignore-certificate-errors");
        options.setExperimentalOption("excludeSwitches", Arrays.asList("test-type"));
        return options;
    }
}