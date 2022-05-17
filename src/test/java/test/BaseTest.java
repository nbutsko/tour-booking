package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.BasePage;
import pageobjects.SearchResultPage;
import pageobjects.TourSearchPage;

import java.time.LocalDate;

public class BaseTest extends AbstractTest {

    @Test
    public void testOpenPage(){
        String departureCity = "Минск";
        String country = "Египет";
        LocalDate datePeriodStart = LocalDate.of(2022, 5, 31);
        LocalDate datePeriodEnd = LocalDate.of(2022, 6, 15);
        int numberOfNights = 12;
        int numberOfAdults = 2;
        int numberOfChildren = 2;
        String resort = "Хургада";

        SearchResultPage searchResultPage = new BasePage(driver).openPage()
                .clickButtonTourSearch()
                .selectDepartureCity(departureCity)
                .selectCountry(country)
                .selectDepartureDatePeriod(datePeriodStart, datePeriodEnd)
                .selectCountOfNights(numberOfNights)
                .selectTourists(numberOfAdults, numberOfChildren)
                .selectResort(resort)
                .clickButtonSearchTour();

        Assert.assertTrue(searchResultPage.isAnyResultContainsResortName(resort));
        Assert.assertTrue(searchResultPage.isAllResultsContainResortName(resort));
    }
}
