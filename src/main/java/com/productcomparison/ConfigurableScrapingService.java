package com.productcomparison;

import java.util.List;
import java.util.ArrayList;

public class ConfigurableScrapingService {
    private List<EcommerceScraper> enabledScrapers;
    
    public ConfigurableScrapingService() {
        this.enabledScrapers = new ArrayList<>();
        initializeDefaultScrapers();
    }
    
    private void initializeDefaultScrapers() {
        // Add all scrapers by default
        enabledScrapers.add(new AmazonScraper());
        enabledScrapers.add(new FlipkartScraper());
        enabledScrapers.add(new EbayScraper());
        enabledScrapers.add(new MyntraScraper());
        enabledScrapers.add(new AjioScraper());
        enabledScrapers.add(new WalmartScraper());
        enabledScrapers.add(new SnapdealScraper());
    }
    
    public void enableScraper(String siteName) {
        EcommerceScraper scraper = createScraperBySiteName(siteName);
        if (scraper != null && !enabledScrapers.contains(scraper)) {
            enabledScrapers.add(scraper);
        }
    }
    
    public void disableScraper(String siteName) {
        enabledScrapers.removeIf(scraper -> scraper.getSiteName().equalsIgnoreCase(siteName));
    }
    
    public List<String> getEnabledSites() {
        return enabledScrapers.stream()
            .map(EcommerceScraper::getSiteName)
            .collect(java.util.stream.Collectors.toList());
    }
    
    public List<EcommerceScraper> getEnabledScrapers() {
        return new ArrayList<>(enabledScrapers);
    }
    
    private EcommerceScraper createScraperBySiteName(String siteName) {
        switch (siteName.toLowerCase()) {
            case "amazon": return new AmazonScraper();
            case "flipkart": return new FlipkartScraper();
            case "ebay": return new EbayScraper();
            case "myntra": return new MyntraScraper();
            case "ajio": return new AjioScraper();
            case "walmart": return new WalmartScraper();
            case "snapdeal": return new SnapdealScraper();
            default: return null;
        }
    }
}