package com.productcomparison;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FlipkartScraper implements EcommerceScraper {
    
    @Override
    public List<Product> searchProducts(WebDriver driver, String searchQuery) {
        List<Product> products = new ArrayList<>();
        
        try {
            driver.get("https://www.flipkart.com");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Close login popup if present
            try {
                WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'_2KpZ6l _2doB4z')]")));
                closeButton.click();
            } catch (Exception e) {
                // Popup not present, continue
            }
            
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("q")));
            searchBox.clear();
            searchBox.sendKeys(searchQuery);
            searchBox.submit();
            
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-id]")));
            
            List<WebElement> productElements = driver.findElements(By.cssSelector("[data-id]"));
            
            for (int i = 0; i < Math.min(productElements.size(), 10); i++) {
                WebElement element = productElements.get(i);
                
                try {
                    String name = getTextSafely(element, "._4rR01T, .s1Q9rs, ._2WkVRV");
                    String price = getTextSafely(element, "._30jeq3, ._1_WHN1");
                    String rating = getTextSafely(element, "._3LWZlK, .gUuXy-");
                    String imageUrl = getAttributeSafely(element, "img", "src");
                    String productUrl = getAttributeSafely(element, "a", "href");
                    
                    if (!name.isEmpty()) {
                        products.add(new Product(name, price, productUrl, imageUrl, "Flipkart", rating));
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (Exception e) {
            System.err.println("Error scraping Flipkart: " + e.getMessage());
        }
        
        return products;
    }
    
    private String getTextSafely(WebElement parent, String selectors) {
        String[] selectorArray = selectors.split(", ");
        for (String selector : selectorArray) {
            try {
                return parent.findElement(By.cssSelector(selector.trim())).getText();
            } catch (Exception e) {
                continue;
            }
        }
        return "N/A";
    }
    
    private String getAttributeSafely(WebElement parent, String tag, String attribute) {
        try {
            return parent.findElement(By.tagName(tag)).getAttribute(attribute);
        } catch (Exception e) {
            return "";
        }
    }
    
    @Override
    public String getSiteName() {
        return "Flipkart";
    }
}