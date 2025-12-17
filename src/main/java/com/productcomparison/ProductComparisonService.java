package com.productcomparison;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductComparisonService {
    private List<EcommerceScraper> scrapers;
    private ExecutorService executorService;
    
    public ProductComparisonService() {
        this.scrapers = new ArrayList<>();
        this.scrapers.add(new AmazonScraper());
        this.scrapers.add(new EbayScraper());
        this.scrapers.add(new FlipkartScraper());
        this.scrapers.add(new MyntraScraper());
        this.scrapers.add(new AjioScraper());
        this.scrapers.add(new WalmartScraper());
        this.scrapers.add(new SnapdealScraper());
        this.executorService = Executors.newFixedThreadPool(7);
    }
    
    public List<Product> compareProducts(String searchQuery) {
        List<Product> allProducts = new ArrayList<>();
        List<CompletableFuture<List<Product>>> futures = new ArrayList<>();
        
        for (EcommerceScraper scraper : scrapers) {
            CompletableFuture<List<Product>> future = CompletableFuture.supplyAsync(() -> {
                return searchWithRetry(scraper, searchQuery, 2);
            }, executorService);
            
            futures.add(future);
        }
        
        for (CompletableFuture<List<Product>> future : futures) {
            try {
                List<Product> products = future.get();
                allProducts.addAll(products);
                System.out.println("Found " + products.size() + " products from a site");
            } catch (Exception e) {
                System.err.println("Error getting products: " + e.getMessage());
            }
        }
        
        return allProducts;
    }
    
    private List<Product> searchWithRetry(EcommerceScraper scraper, String searchQuery, int maxRetries) {
        for (int attempt = 0; attempt <= maxRetries; attempt++) {
            WebDriver driver = createWebDriver();
            try {
                List<Product> products = scraper.searchProducts(driver, searchQuery);
                if (!products.isEmpty() || attempt == maxRetries) {
                    return products;
                }
            } catch (Exception e) {
                System.err.println("Attempt " + (attempt + 1) + " failed for " + scraper.getSiteName() + ": " + e.getMessage());
                if (attempt == maxRetries) {
                    return new ArrayList<>();
                }
            } finally {
                try {
                    driver.quit();
                } catch (Exception e) {
                    // Ignore cleanup errors
                }
            }
            
            try {
                Thread.sleep(2000); // Wait before retry
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        return new ArrayList<>();
    }
    
    private WebDriver createWebDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
        return new ChromeDriver(options);
    }
    
    public void shutdown() {
        executorService.shutdown();
    }
}