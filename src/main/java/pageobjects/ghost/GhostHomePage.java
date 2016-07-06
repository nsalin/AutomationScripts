package pageobjects.ghost;

import com.googlecode.junittoolbox.PollingWait;
import common.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Alin on 7/6/2016.
 */
public class GhostHomePage extends SeleniumUtils {
    @FindBy (xpath = "//a[contains(@class, 'ghost-logo current')]")
    private WebElement currentLogo;
    @FindBy (className = "login")
    private WebElement signInButton;
    @FindBy (id = "username")
    private WebElement username;
    @FindBy (id = "email")
    private WebElement email;
    @FindBy (id = "password")
    private WebElement password;
    @FindBy (xpath = "//button[contains(@class, 'home-signup-button button-add')]")
    private WebElement testItOutButton;
    @FindBy (xpath = "//section[contains(@class, 'build-with-box')]/a[contains(@href,'/developers')]")
    private WebElement ghostDevDocsLink;
    @FindBy (className = "//button[contains(@class, 'button-add large')]")
    private WebElement signUpHomeButton;

    //TODO add the footer links
    private WebDriver driver;
    private PollingWait wait = new PollingWait().timeoutAfter(5, SECONDS)
            .pollEvery(100, MILLISECONDS);

    public GhostHomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public GhostUserPage createAccount(String userName, String userEmail, String userPassword){
        fillInput(username, userName);
        fillInput(email, userEmail);
        fillInput(password, userPassword);
        clickOnElement(testItOutButton);

        return new GhostUserPage(driver);
    }

    public GhostHomePage goToHomePage(){
        clickOnElement(currentLogo);
        return new GhostHomePage(driver);
    }

    public GhostLoginPage goToLoginPage(){
        clickOnElement(signInButton);
        return new GhostLoginPage(driver);
    }



}
