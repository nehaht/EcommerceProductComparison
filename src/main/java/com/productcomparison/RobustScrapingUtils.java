package com.productcomparison;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class RobustScrapingUtils {
    
    public static String getTextWithFallback(WebElement parent, String... selectors) {
        for (String selector : selectors) {
            try {
                WebElement element = parent.findElement(By.cssSelector(selector));
                String text = element.getText().trim();
                if (!text.isEmpty()) {
                    return text;
                }
            } catch (Exception e) {
                continue;
            }
        }
        return "N/A";
    }
    
    public static String getAttributeWithFallback(WebElement parent, String attribute, String... selectors) {
        for (String selector : selectors) {
            try {
                WebElement element = parent.findElement(By.cssSelector(selector));
                String value = element.getAttribute(attribute);
                if (value != null && !value.isEmpty()) {
                    return value;
                }
            } catch (Exception e) {
                continue;
            }
        }
        return "";
    }
    
    public static WebElement waitForAnyElement(WebDriver driver, int timeoutSeconds, String... selectors) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        
        for (String selector : selectors) {
            try {
                return wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
            } catch (Exception e) {
                continue;
            }
        }
        throw new RuntimeException("None of the selectors found elements");
    }
    
    public static List<WebElement> findElementsWithFallback(WebDriver driver, String... selectors) {
        for (String selector : selectors) {
            try {
                List<WebElement> elements = driver.findElements(By.cssSelector(selector));
                if (!elements.isEmpty()) {
                    return elements;
                }
            } catch (Exception e) {
                continue;
            }
        }
        return List.of();
    }
    
    public static void handlePopups(WebDriver driver) {
        String[] popupSelectors = {
            "button[aria-label='Close']",
            ".close-button",
            "[data-testid='close-button']",
            ".modal-close",
            "button:contains('Close')",
            "button:contains('×')",
            ".popup-close"
        };
        
        for (String selector : popupSelectors) {
            try {
                WebElement popup = driver.findElement(By.cssSelector(selector));
                if (popup.isDisplayed()) {
                    popup.click();
                    Thread.sleep(1000);
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
    }
    
    public static String cleanPrice(String price) {
        if (price == null || price.isEmpty()) return "N/A";
        return price.replaceAll("[^0-9.,₹$]", "").trim();
    }
    
    public static String cleanRating(String rating) {
        if (rating == null || rating.isEmpty()) return "N/A";
        return rating.replaceAll("[^0-9.]", "").trim();
    }
}