import abstracts.TestBase;
import org.junit.Test;
import pageobjects.zooadoption.AdoptionBasePage;
import pageobjects.zooadoption.AdoptionPage;
import pageobjects.zooadoption.AdoptionPassPage;

import static pageobjects.zooadoption.AdoptionPage.StartDate.TODAY;

/**
 * Created by alinnitu on 7/6/2016.
 */
public class AdoptionTest extends TestBase {

    @Test
    public void adoptTurtle(){
        AdoptionBasePage basePage = new AdoptionBasePage(driver);
        AdoptionPage adoptionPage = basePage.goToAdoptionPage();
        AdoptionPassPage passPage = adoptionPage.getTurtle(TODAY);
        passPage.checkoutAdoption().succesfullAdoption();

    }

    @Override
    protected String getStartPage() {
        return "";
    }

    @Override
    protected String getContextPath() {
        return "/webapp";
    }
}
