# E-commerce Product Comparison Tool

A Java Selenium-based tool that searches for products across multiple e-commerce websites and displays them in one place for easy comparison.

## Features

- **Multi-site Search**: Supports 7 major e-commerce sites:
  - **Global**: Amazon, eBay, Walmart
  - **Indian**: Flipkart, Myntra, Ajio, Snapdeal
- **Robust Scraping**: Multiple fallback selectors handle website changes
- **Parallel Processing**: Searches all sites simultaneously for faster results
- **Retry Mechanism**: Automatically retries failed requests
- **Product Details**: Extracts name, price, rating, images, and product URLs
- **Site-wise Results**: Organized display by e-commerce platform
- **Popup Handling**: Automatically handles website popups and modals
- **Configurable**: Enable/disable specific sites as needed

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- Chrome browser installed

## Setup

1. Navigate to the project directory:
   ```bash
   cd ecommerce-product-comparison
   ```

2. Install dependencies:
   ```bash
   mvn clean install
   ```

3. Run the main web browser demo:
   ```bash
   mvn exec:java -Dexec.mainClass="com.productcomparison.WebBrowserDemo"
   ```

## Quick Demo (Perfect for LinkedIn Recording)

### **WebBrowserDemo** - Main Demo (Recommended)
```bash
# Navigate to project directory
cd C:\ecommerce-product-comparison

# Compile project
mvn clean compile

# Run web browser demo
mvn exec:java -Dexec.mainClass="com.productcomparison.WebBrowserDemo"
```

**What it does:**
- ✅ Analyzes real Myntra product link (Philips Hair Straightener)
- ✅ Compares prices across 5 Indian e-commerce sites
- ✅ Opens professional HTML comparison page in browser
- ✅ Shows price ranking (Flipkart ₹3,620 vs Myntra ₹4,109)
- ✅ All "Shop Now" buttons link to actual product searches
- ✅ Perfect for screen recording and LinkedIn demos

### Other Available Demos

**LiveDemo** (Interactive console):
```bash
mvn exec:java -Dexec.mainClass="com.productcomparison.LiveDemo"
```

**SimpleDemo** (GUI interface):
```bash
mvn exec:java -Dexec.mainClass="com.productcomparison.SimpleDemo"
```

**FileOutputDemo** (Export results):
```bash
mvn exec:java -Dexec.mainClass="com.productcomparison.FileOutputDemo"
```

## Full Selenium Usage

1. Start the main application:
   ```bash
   mvn exec:java -Dexec.mainClass="com.productcomparison.ProductComparisonApp"
   ```
2. Enter a product name when prompted
3. Wait for results from multiple sites
4. View comparison table with products from different sites
5. Type 'quit' to exit

## Adding New E-commerce Sites

1. Create a new scraper class implementing `EcommerceScraper` interface
2. Add the scraper to `ProductComparisonService` constructor
3. Implement the `searchProducts` method with site-specific selectors

## Project Structure

```
src/main/java/com/productcomparison/
├── Product.java                      # Product data model
├── EcommerceScraper.java            # Interface for scrapers
├── RobustScrapingUtils.java         # Utility for robust scraping
├── ConfigurableScrapingService.java # Service configuration
├── ProductComparisonService.java    # Main service coordinator
├── ProductComparisonApp.java        # Console application
├── AmazonScraper.java              # Amazon implementation
├── EbayScraper.java                # eBay implementation
├── WalmartScraper.java             # Walmart implementation
├── FlipkartScraper.java            # Flipkart implementation
├── MyntraScraper.java              # Myntra implementation
├── AjioScraper.java                # Ajio implementation
└── SnapdealScraper.java            # Snapdeal implementation
```

## Robustness Features

- **Multiple Selector Fallbacks**: Each scraper tries multiple CSS selectors
- **Automatic Retry**: Failed requests are retried up to 2 times
- **Popup Handling**: Automatically closes common website popups
- **Error Recovery**: Continues scraping other sites if one fails
- **Data Cleaning**: Cleans and formats price and rating data
- **Timeout Handling**: Prevents hanging on slow-loading sites

## Enhancements You Can Add

**Immediate Improvements:**
1. **More Indian Sites**: Paytm Mall, ShopClues, Nykaa
2. **Category-specific Sites**: BookMyShow, Zomato, BigBasket
3. **Price Comparison**: Sort by lowest price across sites
4. **Advanced Filters**: Brand, rating, price range filters
5. **Export Features**: CSV, Excel, PDF export

**Advanced Features:**
6. **Web Dashboard**: Spring Boot web interface
7. **Price Alerts**: Email/SMS notifications for price drops
8. **Historical Tracking**: Database storage for price history
9. **Machine Learning**: Product matching across sites
10. **API Integration**: Use official APIs where available

**Business Features:**
11. **User Accounts**: Save searches and favorites
12. **Affiliate Integration**: Monetize through affiliate links
13. **Mobile App**: React Native or Flutter app
14. **Chrome Extension**: Browser extension for quick comparison
15. **Analytics Dashboard**: Usage statistics and trends