package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TourSearchPage extends BasePage {

    private final String TOUR_SEARCH_URL = BASE_URL + "poisk-turov-vo-vse-strany/";

    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));

    @FindBy(xpath = "//div[contains(@class,'TVLocationButton')]")
    private WebElement buttonDepartureCity;

    @FindBy(xpath = "//div[contains(@class,'TVCountry')]")
    private WebElement buttonCountry;

    @FindBy(xpath = "//div[contains(@class,'TVDates')]")
    private WebElement buttonDepartureDate;

    @FindBy(xpath = "//td[contains(@class,'TVToday')]")
    private WebElement buttonTodayDate;

    //TODO change locator or change method logic
    @FindBy(xpath = "//td[contains(@class,'TVAvailableDays')][1]/../following-sibling::tr/td[contains(@class,'TVAvailableDays')][6]")
    private WebElement buttonTwoWeeksLaterDate;

    @FindBy(xpath = "//div[contains(@class,'TVNights')]")
    private WebElement buttonNights;

    @FindBy(xpath = "//div[contains(@class,'TVTourists')]")
    private WebElement buttonTourists;

    @FindBy(xpath = "//div[contains(@class,'TVTouristCount')]")
    private WebElement defaultNumberOfAdultTourists;

    @FindBy(xpath = "//div[contains(@class,'TVTouristActionMinus')]")
    private WebElement buttonMinusTourists;

    @FindBy(xpath = "//div[contains(@class,'TVTouristActionPlus')]")
    private WebElement buttonPlusTourists;

    @FindBy(xpath = "//div[contains(@class,'TVTouristButton')]")
    private WebElement buttonAddChild;

    @FindBy(xpath = "//div[contains(@class,'TVSelectAgeItem')]")
    private WebElement buttonChildAge;

    @FindBy(xpath = "//div[@class='TVSubmitButton TVButtonActive']")
    private WebElement buttonAccept;

    @FindBy(xpath = "//div[contains(@class,'TVResortHotel')]")
    private WebElement buttonResorts;

    @FindBy(xpath = "//div[@class='TVMainFilter']/div[@class='TVSearchButton']")
    private WebElement buttonSearchTour;

    public TourSearchPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public TourSearchPage selectDepartureCity(String selectedCity) throws InterruptedException {
        //wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("TVMainFilter")));
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'TVLocationButton')]"))).click();
        Thread.sleep(10000);
        buttonDepartureCity.click();
        String citiesLocator = "//div[contains(@class,'TVTableCitiesItem')]";
        List<WebElement> cities = driver.findElements(By.xpath(citiesLocator));
        for (WebElement city : cities) {
            if (selectedCity.equals(city.getText().trim())) {
                logger.info(city.getText());
                city.click();
                break;
            }
        }
        return this;
    }

    public TourSearchPage selectCountry(String selectedCountry) {
        buttonCountry.click();
        String countriesLocator = "//div[contains(@class,'TVCountryCheckboxContent')]";
        List<WebElement> countries = driver.findElements(By.xpath(countriesLocator));
        for (WebElement country : countries) {
            if (selectedCountry.equals(country.getText().trim())) {
                logger.info(country.getText());
                country.click();
                break;
            }
        }
        return this;
    }

    public TourSearchPage selectDepartureDatePeriodOfTwoWeeksFromToday() {
        buttonDepartureDate.click();
        buttonTodayDate.click();
        buttonTwoWeeksLaterDate.click();
        logger.info("Departure dates " + buttonDepartureDate.getText());
        return this;
    }

    public TourSearchPage selectCountOfNightsMoreThan10() {
        buttonNights.click();
        String availableNightsLocator = "//div[contains(@class,'TVAvailableNight')]";
        List<WebElement> availableNights = driver.findElements(By.xpath(availableNightsLocator));
        for (WebElement night : availableNights) {
            if (Integer.parseInt(night.getText()) >= 11) {
                logger.info("Number of nights: " + night.getText());
                night.click();
                break;
            }
        }
        return this;
    }

    public TourSearchPage selectTourists(int numberOfAdults, int numberOfChildren) {
        buttonTourists.click();
        if (Integer.parseInt(defaultNumberOfAdultTourists.getText().trim()) > numberOfAdults) {
            do {
                buttonMinusTourists.click();
            }
            while (Integer.parseInt(defaultNumberOfAdultTourists.getText().trim()) > numberOfAdults);
        } else if (Integer.parseInt(defaultNumberOfAdultTourists.getText().trim()) < numberOfAdults) {
            do {
                buttonPlusTourists.click();
            }
            while (Integer.parseInt(defaultNumberOfAdultTourists.getText().trim()) < numberOfAdults);
        }
        logger.info("Number of adults: " + defaultNumberOfAdultTourists.getText());
        for (int i = 0; i < numberOfChildren; i++) {
            buttonAddChild.click();
            buttonChildAge.click();
        }
        buttonAccept.click();
        return this;
    }

    private void selectNumberOfAdults(int numberOfAdults){
        if (Integer.parseInt(defaultNumberOfAdultTourists.getText().trim()) > numberOfAdults) {
            do {
                buttonMinusTourists.click();
            }
            while (Integer.parseInt(defaultNumberOfAdultTourists.getText().trim()) > numberOfAdults);
        } else if (Integer.parseInt(defaultNumberOfAdultTourists.getText().trim()) < numberOfAdults) {
            do {
                buttonPlusTourists.click();
            }
            while (Integer.parseInt(defaultNumberOfAdultTourists.getText().trim()) < numberOfAdults);
        }
        logger.info("Number of adults: " + defaultNumberOfAdultTourists.getText());
    }

    private void selectNumberOfChildren(int numberOfChildren){

    }

    public TourSearchPage selectResort(String selectedResort){
        buttonResorts.click();
        String resortsLocator = "//div[contains(@class,'TreeListItem')]";
        List<WebElement> resorts = driver.findElements(By.xpath(resortsLocator));
        for (WebElement resort : resorts) {
            if (selectedResort.equals(resort.getText().trim())) {
                logger.info("Resort: " + resort.getText());
                resort.click();
                break;
            }
        }
        buttonAccept.click();
        return this;
    }

    public TourSearchPage clickButtonSearchTour(){
        buttonSearchTour.click();
        return this;
    }
}
