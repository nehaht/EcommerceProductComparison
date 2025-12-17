package com.productcomparison;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class EbayScraper implements EcommerceScraper {
    
    @Override
    public List<Product> searchProducts(WebDriver driver, String searchQuery) {
        List<Product> products = new ArrayList<>();
        
        try {
            driver.get("https://www.ebay.com");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            RobustScrapingUtils.handlePopups(driver);
            
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("gh-ac")));
            searchBox.clear();
            searchBox.sendKeys(searchQuery);
            searchBox.submit();
            
            List<WebElement> productElements = RobustScrapingUtils.findElementsWithFallback(driver,
                ".s-item", "[data-view='mi:1686']", ".srp-results .s-item");
            
            for (int i = 1; i < Math.min(productElements.size(), 11); i++) {
                WebElement element = productElements.get(i);
                
                try {
                    String name = RobustScrapingUtils.getTextWithFallback(element,
                        ".s-item__title", "h3", ".it-ttl");
                    String price = RobustScrapingUtils.cleanPrice(RobustScrapingUtils.getTextWithFallback(element,
                        ".s-item__price", ".notranslate", ".u-flL"));
                    String rating = RobustScrapingUtils.cleanRating(RobustScrapingUtils.getTextWithFallback(element,
                        ".reviews", ".ebay-review-stars"));
                    String imageUrl = RobustScrapingUtils.getAttributeWithFallback(element, "src", ".s-item__image img", "img");
                    String productUrl = RobustScrapingUtils.getAttributeWithFallback(element, "href", ".s-item__link", "a");
                    
                    if (!name.equals("N/A")) {
                        products.add(new Product(name, price, productUrl, imageUrl, "eBay", rating));
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (Exception e) {
            System.err.println("Error scraping eBay: " + e.getMessage());
        }
        
        return products;
    }
    
    @Override
    public String getSiteName() {
        return "eBay";
    }
}