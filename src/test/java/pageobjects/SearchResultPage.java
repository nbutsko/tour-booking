package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class SearchResultPage extends BasePage{

    @FindBy(xpath = "//div[contains(@class,'TVProgressBar')]")
    private WebElement progressBar;

    public SearchResultPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    private List<String> getListOfResortNamesInSearchResult() {
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

    public boolean isAllResultsContainResortName(String resortName) {
        List<String> regionsInResult = getListOfResortNamesInSearchResult();
        logger.info(regionsInResult.toString());
        for (String regionInOneTour : regionsInResult) {
            if (!regionInOneTour.contains(resortName)) {
                return false;
            }
        }
        return true;
    }
}
