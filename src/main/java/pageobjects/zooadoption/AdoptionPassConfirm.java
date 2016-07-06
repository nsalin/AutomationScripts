package pageobjects.zooadoption;

import com.googlecode.junittoolbox.PollingWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by alinnitu on 7/6/2016.
 */
public class AdoptionPassConfirm extends AdoptionBasePage {
    @FindBy (className = "content")
    private WebElement contentPage;
    private PollingWait wait = new PollingWait().timeoutAfter(10, SECONDS)
            .pollEvery(100, MILLISECONDS);
    private final String message = "YOUR ADOPTION HAS BEEN SET UP";

    public AdoptionPassConfirm(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean succesfullAdoption(){
        wait.until(contentPage::isDisplayed);
        return contentPage.getText().contains(message);
    }
}
