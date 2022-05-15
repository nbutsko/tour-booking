package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class AlatanTourHomePage extends BasePage{

    public AlatanTourHomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
}
