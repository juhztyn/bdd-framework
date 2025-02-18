package helper;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CommonUtils {
    private static final int DEFAULT_TIMEOUT = 5;
    private static final Logger logger = LoggerUtil.getLogger(CommonUtils.class);

    // Private constructor to prevent instantiation
    private CommonUtils() {}

    // Function to pause for specified seconds
    public static void pause(long milliseconds) {
        try {
            logger.info("Pausing for {} milliseconds.", milliseconds);
            Thread.sleep(milliseconds);
            logger.info("Resumed after pausing for {} milliseconds.", milliseconds);
        } catch (InterruptedException e) {
            logger.error("Thread was interrupted during a {} milliseconds pause.", milliseconds, e);
            Thread.currentThread().interrupt(); // Restore interrupted status
            throw new RuntimeException("Pause operation interrupted", e);
        }
    }

    // Function to explicit wait for element to be visible
    public static WebElement waitForVisibility(WebDriver driver, By locator, String pageName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element not visible after {} seconds on {} page: {}", DEFAULT_TIMEOUT, pageName, locator, e);
            throw new RuntimeException("Element not visible on " + pageName + " page: " + locator, e);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while waiting for visibility of element on {} page: {}", pageName, locator, e);
            throw new RuntimeException("Unexpected error on " + pageName + " page: " + locator, e);
        }
    }

    // Function to explicit wait for element to be clickable
    public static WebElement waitForElementToBeClickable(WebDriver driver, By locator, String pageName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            logger.error("Element not clickable after {} seconds on {} page: {}", DEFAULT_TIMEOUT, pageName, locator, e);
            throw new RuntimeException("Element not clickable on " + pageName + " page: " + locator, e);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while waiting for element to be clickable on {} page: {}", pageName, locator, e);
            throw new RuntimeException("Unexpected error on " + pageName + " page: " + locator, e);
        }
    }

}
