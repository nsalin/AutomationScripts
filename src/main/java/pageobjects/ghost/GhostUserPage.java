package pageobjects.ghost;

import common.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Alin on 7/6/2016.
 */
public class GhostUserPage extends SeleniumUtils {
    @FindBy(xpath = "//*[@id='main-menu']//a[contains(text(), 'Home')]")
    private WebElement homeButton;
    @FindBy (xpath = "//*[@id='main-menu']//a[contains(text(), 'Marketplace')]")
    private WebDriver marketButton;
    @FindBy (xpath = "//*[@id='main-menu']//a[contains(text(), 'Support')]")
    private WebElement supportButton;
    @FindBy (xpath = "//*[@id='main-menu']//a[contains(text(), 'Blog')]")
    private WebElement blogButton;
    @FindBy (className = "ghost-logo")
    private WebElement ghostLogo;
    @FindBy (className = "usermenu")
    private WebElement userMenuButton;

    //// TODO: 7/6/2016 add list of footer links
    private WebDriver driver;

    public GhostUserPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public GhostSupportPage goToSupportPage(){
        waitUntilPageIsLoaded(supportButton);
       clickOnElement(supportButton);
        return new GhostSupportPage(driver);
    }


}
