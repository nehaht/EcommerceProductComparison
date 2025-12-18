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

3. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="com.productcomparison.ProductComparisonApp"
   ```

## Demo Versions

The project includes multiple demo versions for different use cases:

### 1. **AutoDemo** (Recommended for presentations)
```bash
cd src/main/java
javac com/productcomparison/Product.java com/productcomparison/AutoDemo.java
java com.productcomparison.AutoDemo
```
- Opens web browser with comparison results
- Clickable links to actual product search pages
- Professional UI perfect for LinkedIn demos

### 2. **LiveDemo** (Interactive console)
```bash
cd src/main/java
javac com/productcomparison/Product.java com/productcomparison/LiveDemo.java
java com.productcomparison.LiveDemo
```
- Interactive console interface
- Type product names to search
- Real-time search simulation

### 3. **SimpleDemo** (GUI interface)
```bash
cd src/main/java
javac com/productcomparison/Product.java com/productcomparison/SimpleDemo.java
java com.productcomparison.SimpleDemo
```
- Swing-based GUI window
- Search box and results table
- User-friendly interface

### 4. **FileOutputDemo** (Export results)
```bash
cd src/main/java
javac com/productcomparison/Product.java com/productcomparison/FileOutputDemo.java
java com.productcomparison.FileOutputDemo
```
- Saves results to CSV file
- Perfect for data analysis

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