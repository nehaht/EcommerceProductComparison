package com.productcomparison;

import org.openqa.selenium.WebDriver;
import java.util.List;

public interface EcommerceScraper {
    List<Product> searchProducts(WebDriver driver, String searchQuery);
    String getSiteName();
}