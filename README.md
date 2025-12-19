# 🛒 E-commerce Product Comparison Tool

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium-4.0+-green.svg)](https://selenium.dev/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> A powerful Java-based tool that compares prices across multiple e-commerce platforms to help you find the best deals instantly! 💰

**🎯 Find the cheapest prices across Amazon, Flipkart, Myntra, Ajio, Nykaa & more with just one click!**

## 🚀 Features

- **Multi-site Search**: Supports 7+ major e-commerce sites
- **Real-time Scraping**: Live price extraction using Selenium WebDriver
- **Parallel Processing**: Searches all sites simultaneously for faster results
- **Robust Error Handling**: Automatic retries and popup handling
- **Professional Reports**: Clean HTML comparison pages
- **Multiple Interfaces**: Console app and web browser demo

## 🛒 Supported Platforms

**Indian Sites:**
- Amazon.in, Flipkart, Myntra, Ajio, Nykaa, Snapdeal

**Global Sites:**
- Amazon.com, eBay, Walmart

## 🛠️ Tech Stack

- **Java 17** - Core programming language
- **Selenium WebDriver** - Web automation and scraping
- **Maven** - Dependency management and build tool
- **WebDriverManager** - Automatic driver management
- **Multi-threading** - Parallel processing for performance

## ⚡ Quick Start

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- Chrome browser installed

### Installation & Usage

1. **Clone the repository:**
   ```bash
   git clone https://github.com/nehaht/EcommerceProductComparison.git
   cd EcommerceProductComparison
   ```

2. **Compile the project:**
   ```bash
   mvn clean compile
   ```

3. **Run the main application:**
   ```bash
   mvn exec:java -Dexec.mainClass="com.productcomparison.ProductComparisonApp"
   ```

4. **Run the web browser demo:**
   ```bash
   mvn exec:java -Dexec.mainClass="com.productcomparison.WebBrowserDemo"
   ```

## 🎬 Demo

The **WebBrowserDemo** provides a perfect demonstration:
- Analyzes real Myntra product link (Philips Hair Straightener)
- Compares prices across 5 Indian e-commerce sites
- Opens professional HTML comparison page in browser
- Shows price ranking (Flipkart ₹3,620 vs Myntra ₹4,109)
- All "Shop Now" buttons link to actual product searches

## 📁 Project Structure

```
src/main/java/com/productcomparison/
├── Product.java                      # Product data model
├── EcommerceScraper.java            # Interface for scrapers
├── ProductComparisonService.java    # Main service coordinator
├── ProductComparisonApp.java        # Console application
├── WebBrowserDemo.java              # Web browser demo
├── RobustScrapingUtils.java         # Utility for robust scraping
├── AmazonScraper.java              # Amazon implementation
├── FlipkartScraper.java            # Flipkart implementation
├── MyntraScraper.java              # Myntra implementation
├── AjioScraper.java                # Ajio implementation
├── EbayScraper.java                # eBay implementation
├── WalmartScraper.java             # Walmart implementation
└── SnapdealScraper.java            # Snapdeal implementation
```

## 🎯 Key Features

- **Robust Scraping**: Multiple fallback selectors handle website changes
- **Popup Handling**: Automatically handles website popups and modals
- **Data Cleaning**: Cleans and formats price and rating data
- **Retry Mechanism**: Automatically retries failed requests
- **Parallel Processing**: Multi-threaded execution for performance
- **Professional UI**: Clean HTML reports with responsive design

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Built with Java and Selenium WebDriver
- Inspired by the need for smart shopping solutions
- Perfect for learning web scraping and automation

---

⭐ **Star this repo if you found it helpful!** ⭐