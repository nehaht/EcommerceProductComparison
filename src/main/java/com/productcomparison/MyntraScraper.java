package com.productcomparison;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MyntraScraper implements EcommerceScraper {
    
    @Override
    public List<Product> searchProducts(WebDriver driver, String searchQuery) {
        List<Product> products = new ArrayList<>();
        
        try {
            driver.get("https://www.myntra.com");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.className("desktop-searchBar")));
            searchBox.clear();
            searchBox.sendKeys(searchQuery);
            searchBox.submit();
            
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("product-base")));
            
            List<WebElement> productElements = driver.findElements(By.className("product-base"));
            
            for (int i = 0; i < Math.min(productElements.size(), 10); i++) {
                WebElement element = productElements.get(i);
                
                try {
                    String name = getTextSafely(element, ".product-product, .product-brand, h3, h4");
                    String price = getTextSafely(element, ".product-discountedPrice, .product-price");
                    String rating = getTextSafely(element, ".product-ratingsContainer");
                    String imageUrl = getAttributeSafely(element, "img", "src");
                    String productUrl = getAttributeSafely(element, "a", "href");
                    
                    if (!name.isEmpty()) {
                        products.add(new Product(name, price, productUrl, imageUrl, "Myntra", rating));
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (Exception e) {
            System.err.println("Error scraping Myntra: " + e.getMessage());
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
        return "Myntra";
    }
}