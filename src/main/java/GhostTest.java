import abstracts.TestBase;
import common.SeleniumUtils;
import org.junit.Test;
import pageobjects.ghost.GhostHomePage;
import pageobjects.ghost.GhostSupportPage;
import pageobjects.ghost.GhostUserPage;

/**
 * Created by alinnitu on 7/6/2016.
 */
public class GhostTest extends TestBase {

    @Test
    public void searchForNewBlogInformation(){
        SeleniumUtils utils = new SeleniumUtils(driver);
        GhostHomePage ghostHomePage = new GhostHomePage(driver);
        GhostUserPage userPage = ghostHomePage.createAccount(utils.generateUsername(), utils.generatePassword(), "1234567812312");
        GhostSupportPage supportPage = userPage.goToSupportPage();
        supportPage.searchForText("create new blog");
        supportPage.openRandomSearchResult();

    }

    @Override
    protected String getStartPage() {
        return "";
    }

    @Override
    protected String getContextPath() {
        return "";
    }

}
