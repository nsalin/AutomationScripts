package pageobjects.zooadoption;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by alinnitu on 7/6/2016.
 */
public class AdoptionPassPage extends AdoptionBasePage {
    @FindBy(name = "name_field")
    private WebElement nameField;
    @FindBy(name = "address_field")
    private WebElement addressField;
    @FindBy(name = "postcode_field")
    private WebElement postcodeField;
    @FindBy(name = "email_field")
    private WebElement emailField;
    @FindBy (id = "submit_adoption")
    private WebElement submitAdoptionButton;

    public AdoptionPassPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public AdoptionPassConfirm checkoutAdoption(){
        fillInput(nameField, generateUsername());
        fillInput(addressField, "TM, 101");
        fillInput(postcodeField,"300033");
        fillInput(emailField, generateUsername() + "@gmail.com");
        clickOnElement(submitAdoptionButton);

        return new AdoptionPassConfirm(driver);
    }
}
