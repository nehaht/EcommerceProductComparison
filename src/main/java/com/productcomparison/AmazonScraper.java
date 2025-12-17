package com.productcomparison;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AmazonScraper implements EcommerceScraper {
    
    @Override
    public List<Product> searchProducts(WebDriver driver, String searchQuery) {
        List<Product> products = new ArrayList<>();
        
        try {
            driver.get("https://www.amazon.com");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            RobustScrapingUtils.handlePopups(driver);
            
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("twotabsearchtextbox")));
            searchBox.clear();
            searchBox.sendKeys(searchQuery);
            searchBox.submit();
            
            List<WebElement> productElements = RobustScrapingUtils.findElementsWithFallback(driver,
                "[data-component-type='s-search-result']",
                ".s-result-item",
                "[data-asin]");
            
            for (int i = 0; i < Math.min(productElements.size(), 10); i++) {
                WebElement element = productElements.get(i);
                
                try {
                    String name = RobustScrapingUtils.getTextWithFallback(element,
                        "h2 a span", ".a-size-medium", "h2 span", ".s-size-mini");
                    String price = RobustScrapingUtils.cleanPrice(RobustScrapingUtils.getTextWithFallback(element,
                        ".a-price-whole", ".a-price .a-offscreen", ".a-price-range"));
                    String rating = RobustScrapingUtils.cleanRating(RobustScrapingUtils.getTextWithFallback(element,
                        ".a-icon-alt", ".a-star-mini", "[aria-label*='stars']"));
                    String imageUrl = RobustScrapingUtils.getAttributeWithFallback(element, "src", "img");
                    String productUrl = RobustScrapingUtils.getAttributeWithFallback(element, "href", "h2 a", "a");
                    
                    if (!name.equals("N/A")) {
                        products.add(new Product(name, price, productUrl, imageUrl, "Amazon", rating));
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (Exception e) {
            System.err.println("Error scraping Amazon: " + e.getMessage());
        }
        
        return products;
    }
    
    @Override
    public String getSiteName() {
        return "Amazon";
    }
}