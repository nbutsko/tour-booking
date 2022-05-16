package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class TourSearchPage extends BasePage {

    private final String TOUR_SEARCH_URL = BASE_URL + "poisk-turov-vo-vse-strany/";

    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));

    @FindBy(xpath = "//div[contains(@class,'TVProgressBar')]")
    private WebElement progressBar;

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

    public TourSearchPage selectDepartureCity(String selectedCity) throws InterruptedException {
        Thread.sleep(5000);
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
        String availableDaysLocator = "//td[contains(@class,'TVAvailableDays')]";
        List<WebElement> availableDays = driver.findElements(By.xpath(availableDaysLocator));
        /*for (int i = 13; i < availableDays.size(); i++) {
            String classText = availableDays.get(i).getDomProperty("className");
            System.out.println(classText);
        }*/
        availableDays.get(13).click();
        logger.info("Departure dates " + buttonDepartureDate.getText());
        return this;
    }

    public TourSearchPage selectCountOfNightsMoreThan10() {
        buttonNights.click();
        String availableNightsLocator = "//div[contains(@class,'TVAvailableNight')]";
        List<WebElement> availableNights = driver.findElements(By.xpath(availableNightsLocator));
        int count = 0;
        for (WebElement night : availableNights) {
            if (Integer.parseInt(night.getText()) >= 11) {
                logger.info("Number of nights: " + night.getText());
                night.click();
                count++;
                if (count == 2) {
                    break;
                }
            }
        }
        return this;
    }

    public TourSearchPage selectTourists(int numberOfAdults, int numberOfChildren) {
        buttonTourists.click();
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

    public TourSearchPage clickButtonSearchTour() {
        buttonSearchTour.click();
        return this;
    }

    private List<String> getListOfResortNamesInSearchResult(){
        wait.until(ExpectedConditions.attributeContains(progressBar, "class", "TVHide"));
        String toursRegionLocator = "//div[@class='TVTourBlock']//div[@class='TVRegion']";
        List<String> regionsInResult = driver.findElements(By.xpath(toursRegionLocator)).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        return regionsInResult;
    }

    public boolean isAnyResultContainsResortName(String resortName) {
        List<String> regionsInResult = getListOfResortNamesInSearchResult();
        return regionsInResult.stream().anyMatch(region -> region.contains(resortName));
    }

    public boolean isAllResultsContainResortName(String resortName){
        List<String> regionsInResult = getListOfResortNamesInSearchResult();
        logger.info(regionsInResult.toString());
        for (String regionInOneTour : regionsInResult) {
            if (!regionInOneTour.contains(resortName)){
                return false;
            }
        }
        return true;
    }
}
