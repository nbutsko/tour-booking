package pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class AbstractPage {

    protected WebDriver driver;
    public final static String BASE_URL = "https://alatantour.by/";
    public final static int WAIT_TIMEOUT_SECONDS = 50;

    protected static final Logger logger = LogManager.getLogger();

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

}
