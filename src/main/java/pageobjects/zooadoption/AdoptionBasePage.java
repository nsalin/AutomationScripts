package pageobjects.zooadoption;

import common.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by alinnitu on 7/6/2016.
 */
public class AdoptionBasePage extends SeleniumUtils {
    @FindBy (id = "home_link")
    private WebElement homeButton;
    @FindBy (id = "adoption_link")
    private WebElement adoptionPageButton;
    @FindBy (id = "about_link")
    private WebElement aboutPageButton;
    @FindBy (id = "contact_link")
    private WebElement contactPageButton;
    @FindBy (id = "footer_term")
    private WebElement termsWindow;
    @FindBy (xpath = "//a[contains(text(), 'THETESTROOM.COM')]")
    private WebElement theTestRoomPageButton;

    public AdoptionBasePage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public AdoptionPage goToAdoptionPage(){
        clickOnElement(adoptionPageButton);
        return new AdoptionPage(driver);
    }
}
