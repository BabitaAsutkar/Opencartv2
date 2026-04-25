package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class productMeetingTheSearchCriteriaPage extends BasePage  {
    
     public productMeetingTheSearchCriteriaPage(WebDriver driver) {
         super(driver);
     }
     
    // Broadened selector list to match various OpenCart theme markups
    private By productItems = By.cssSelector("div.product-layout, div.product-thumb, div.product-grid, .product-layout, .product-thumb, .product-item, [class*='product']");
    // Broadened no-match message XPath to capture common variations of the 'no results' text
    private By noMatchMessage = By.xpath("//*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'there is no product') or contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'no products were found') or contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'no product that matches') or contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'product not found') or contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '0 products')]");
    // Locator for search heading (optional)
    private By searchHeading = By.xpath("//h1");

    /**
     * Returns true if one or more product result elements are present/visible on the page.
     */
    public boolean isProductFound() {
        try {
            // Increase wait time to handle slower renders
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // Wait until either product items are present OR a no-match message is present
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(productItems),
                    ExpectedConditions.presenceOfElementLocated(noMatchMessage)
            ));

            // After the wait, check for product items first
            try {
                int count = driver.findElements(productItems).size();
                if (count > 0) {
                    // ensure at least one is displayed
                    for (org.openqa.selenium.WebElement el : driver.findElements(productItems)) {
                        try {
                            if (el.isDisplayed()) {
                                return true;
                            }
                        } catch (Exception ignore) {
                            // continue checking other elements
                        }
                    }
                }
                return false;
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns true if the "no match" message is displayed on the page.
     */
    public boolean isNoMatchFoundMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
            wait.until(ExpectedConditions.presenceOfElementLocated(noMatchMessage));
            try {
                return driver.findElement(noMatchMessage).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Optional helper: get the page heading text (useful for debugging/assertions)
     */
    public String getSearchHeadingText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(searchHeading)).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

}