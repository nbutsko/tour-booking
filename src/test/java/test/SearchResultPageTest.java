package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.BasePage;
import pageobjects.SearchResultPage;

import java.time.LocalDate;

public class SearchResultPageTest extends AbstractTest {

    @Test
    public void testSearchResultPageContainsResortName(){
        String departureCity = "Минск";
        String country = "Египет";
        LocalDate datePeriodStart = LocalDate.now().plusWeeks(2);
        LocalDate datePeriodEnd = LocalDate.now().plusWeeks(4);
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
