package pageobjects.ghost;

import com.googlecode.junittoolbox.PollingWait;
import common.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by alinnitu on 7/6/2016.
 */
public class GhostSupportPage extends SeleniumUtils {
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
    @FindBy (id = "s")
    private WebElement searchBar;
    @FindBy (id = "searchsubmit")
    private WebElement searchButton;
    @FindBy (className = "support-box green-box")
    private WebElement proFeature;
    @FindBy (className = "support-box red-box")
    private WebElement developerButton;
    @FindBy (id = "content")
    private WebElement searchResultContainer;
    @FindBy (id = "page-header")
    private WebElement articleHeader;

    private PollingWait wait = new PollingWait().timeoutAfter(30, SECONDS)
            .pollEvery(10, SECONDS);

    public GhostSupportPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public GhostSupportPage searchForText(String text){
        waitUntilPageIsLoaded(searchBar);
        fillInput(searchBar, text);
        clickOnElement(searchButton);

        return new GhostSupportPage(driver);
    }

    public GhostSupportPage openRandomSearchResult(){
        wait.until(searchResultContainer::isDisplayed);
        List<WebElement> articles = searchResultContainer.findElements(By.tagName("article"));
//        System.out.println(article.);
        clickOnElement(articles.get(getRandomNumberFromList(articles)));
        wait.until(articleHeader::isDisplayed);
        logger.info("The chosen article is, " + articleHeader.getText());

        return new GhostSupportPage(driver);
    }

}
