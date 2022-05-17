package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage extends AbstractPage {

    protected WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));

    @FindBy(xpath = "//nav[@class='menu']//a[@href='/poisk-turov-vo-vse-strany/']")
    private WebElement buttonTourSearch;

    public BasePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public AlatanTourHomePage openPage() {
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        return new AlatanTourHomePage(driver);
    }

    public TourSearchPage clickButtonTourSearch(){
        buttonTourSearch.click();
        return new TourSearchPage(driver);
    }
}
