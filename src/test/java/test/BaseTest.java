package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.BasePage;
import pageobjects.TourSearchPage;

public class BaseTest extends AbstractTest {

    @Test
    public void testOpenPage() throws InterruptedException {
        String departureCity = "Минск";
        String country = "Египет";
        int numberOfAdults = 2;
        int numberOfChildren = 2;
        String resort = "Хургада";

        TourSearchPage tourSearchPage = new BasePage(driver).openPage()
                .clickButtonTourSearch()
                .selectDepartureCity(departureCity)
                .selectCountry(country)
                .selectDepartureDatePeriodOfTwoWeeksFromToday()
                .selectCountOfNightsMoreThan10()
                .selectTourists(numberOfAdults, numberOfChildren)
                .selectResort(resort)
                .clickButtonSearchTour();

        Assert.assertTrue(tourSearchPage.isAnyResultContainsResortName(resort));
        Assert.assertTrue(tourSearchPage.isAllResultsContainResortName(resort));
    }
}
