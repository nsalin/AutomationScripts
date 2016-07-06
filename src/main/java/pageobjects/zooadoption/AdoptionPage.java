package pageobjects.zooadoption;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by alinnitu on 7/6/2016.
 */
public class AdoptionPage extends AdoptionBasePage {
    @FindBy (id = "start_select")
    private WebElement dateDropDown;
    @FindBy (id = "check_btn_01")
    private WebElement lionAvailabiltyButton;
    @FindBy (id = "check_btn_02")
    private WebElement turtleAvailabiltyButton;

    public AdoptionPage(WebDriver driver) {
        super(driver);
    }

    public AdoptionPassPage getTurtle(StartDate startDate){
        selectDate(startDate);
        clickOnElement(turtleAvailabiltyButton);
        return new AdoptionPassPage(driver);
    }

    private void selectDate(StartDate startDate){
        Select drop = new Select(dateDropDown);
        drop.selectByIndex(startDate.getIndex());
    }

    public enum StartDate {
        DEFAULT(0), TODAY(1), FIRST_DAY_OF_THE_NEXT_WEEK(2), FIRST_DAY_OF_THE_NEXT_MONTH(3);

        int index;
        StartDate(int index){
            this.index = index;
        }

        public Integer getIndex(){
            return index;
        }
    }
}
