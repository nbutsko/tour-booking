package pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AbstractPage {

    protected WebDriver driver;
    public final static String BASE_URL = "https://alatantour.by/";
    public final static int WAIT_TIMEOUT_SECONDS = 10;

    protected static final Logger logger = LogManager.getLogger();

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

}
