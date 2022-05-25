package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDate;
import java.util.List;

public class TourSearchPage extends BasePage {

    private final String TOUR_SEARCH_URL = BASE_URL + "poisk-turov-vo-vse-strany/";

    @FindBy(className = "se-pre-con")
    private WebElement loadingSearchForm;

    @FindBy(xpath = "//div[contains(@class,'TVLocationButton')]")
    private WebElement buttonDepartureCity;

    @FindBy(xpath = "//div[contains(@class,'TVCountry')]")
    private WebElement buttonCountry;

    @FindBy(xpath = "//div[contains(@class,'TVDates')]")
    private WebElement buttonDepartureDate;

    @FindBy(xpath = "//td[contains(@class,'TVToday')]")
    private WebElement buttonTodayDate;

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

    private void clickButtonWithJSExecutor(WebElement button) {
        try {
            button.click();
        } catch (Exception e) {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", button);
        }
    }

    public TourSearchPage selectDepartureCity(String selectedCity) {
        wait.until(ExpectedConditions.invisibilityOf(loadingSearchForm));
        clickButtonWithJSExecutor(buttonDepartureCity);
        String citiesLocator = "//div[contains(@class,'TVTableCitiesItem')]";
        List<WebElement> cities = driver.findElements(By.xpath(citiesLocator));
        for (WebElement city : cities) {
            if (selectedCity.equals(city.getText().trim())) {
                logger.info("Departure city: " + city.getText());
                city.click();
                break;
            }
        }
        return this;
    }

    public TourSearchPage selectCountry(String selectedCountry) {
        clickButtonWithJSExecutor(buttonCountry);
        String countriesLocator = "//div[contains(@class,'TVCountryCheckboxContent')]";
        List<WebElement> countries = driver.findElements(By.xpath(countriesLocator));
        for (WebElement country : countries) {
            if (selectedCountry.equals(country.getText().trim())) {
                logger.info("Country: " + country.getText());
                country.click();
                break;
            }
        }
        return this;
    }

    public TourSearchPage selectDepartureDatePeriod(LocalDate startDate, LocalDate endDate) {
        clickButtonWithJSExecutor(buttonDepartureDate);
        int numberOfDaysAfterTodayStart = (int) (startDate.toEpochDay() - LocalDate.now().toEpochDay()) - 1;
        int numberOfDaysAfterTodayEnd = (int) (endDate.toEpochDay() - LocalDate.now().toEpochDay()) - 1;
        selectDepartureDateDay(numberOfDaysAfterTodayStart);
        selectDepartureDateDay(numberOfDaysAfterTodayEnd);
        wait.until(ExpectedConditions.visibilityOf(buttonDepartureDate));
        logger.info("Departure dates: " + buttonDepartureDate.getText());
        return this;
    }

    private void selectDepartureDateDay(int numberOfDaysAfterToday) {
        for (int i = 0; i <= 2; i++) {
            String availableDaysLocator = "//td[contains(@class,'TVAvailableDays')]";
            try {
                List<WebElement> availableDays = driver.findElements(By.xpath(availableDaysLocator));
                availableDays.get(numberOfDaysAfterToday).click();
                break;
            } catch (StaleElementReferenceException e) {
                logger.warn(e.getMessage());
            }
        }
    }

    public TourSearchPage selectCountOfNights(int numberOfNights) {
        clickButtonWithJSExecutor(buttonNights);
        String availableNightsLocator = "//div[contains(@class,'TVAvailableNight')]";
        List<WebElement> availableNights = driver.findElements(By.xpath(availableNightsLocator));
        for (WebElement night : availableNights) {
            if (Integer.parseInt(night.getText()) == numberOfNights) {
                logger.info("Number of nights: " + night.getText());
                night.click();
                break;
            }
        }
        return this;
    }

    public TourSearchPage selectTourists(int numberOfAdults, int numberOfChildren) {
        clickButtonWithJSExecutor(buttonTourists);
        selectNumberOfAdults(numberOfAdults);
        selectNumberOfChildren(numberOfChildren);
        buttonAccept.click();
        return this;
    }

    private void selectNumberOfAdults(int numberOfAdults) {
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

    private void selectNumberOfChildren(int numberOfChildren) {
        int countOfChildren = 0;
        for (int i = 0; i < numberOfChildren; i++, countOfChildren++) {
            buttonAddChild.click();
            buttonChildAge.click();
        }
        logger.info("Number of children: " + countOfChildren);
    }

    public TourSearchPage selectResort(String selectedResort) {
        clickButtonWithJSExecutor(buttonResorts);
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

    public SearchResultPage clickButtonSearchTour() {
        clickButtonWithJSExecutor(buttonSearchTour);
        return new SearchResultPage(driver);
    }
}
