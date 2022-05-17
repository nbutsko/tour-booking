package pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class AbstractPage {

    public final static String BASE_URL = "https://alatantour.by/";

    public static final Logger logger = LogManager.getLogger();

    public WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }
}
