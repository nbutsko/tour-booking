package test;

import org.testng.annotations.Test;
import pageobjects.BasePage;

public class BaseTest extends AbstractTest {

    @Test
    public void testOpenPage() throws InterruptedException {
        new BasePage(driver).openPage()
                .clickButtonTourSearch()
                .selectDepartureCity("Минск")
                .selectCountry("Египет")
                .selectDepartureDatePeriodOfTwoWeeksFromToday()
                .selectCountOfNightsMoreThan10()
                .selectTourists(2, 2)
                .selectResort("Хургада")
                .clickButtonSearchTour();

        Thread.sleep(10000);
    }
}
