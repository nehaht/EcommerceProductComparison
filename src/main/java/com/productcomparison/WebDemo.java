package com.productcomparison;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.*;

public class WebDemo {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== WEB-BASED PRODUCT COMPARISON DEMO ===");
        System.out.print("Enter product to search: ");
        String product = scanner.nextLine().trim();
        
        if (product.isEmpty()) {
            product = "laptop";
        }
        
        System.out.println("\n🌐 Launching websites and generating comparison page...");
        
        // Generate comparison data
        List<Product> products = generateProductData(product);
        
        // Create HTML comparison page
        String htmlFile = createComparisonPage(product, products);
        
        // Open websites in browser
        openWebsites();
        
        // Open comparison page
        openComparisonPage(htmlFile);
        
        System.out.println("✅ Demo launched! Check your browser for:");
        System.out.println("1. Individual e-commerce websites");
        System.out.println("2. Product comparison page");
        
        scanner.close();
    }
    
    private static List<Product> generateProductData(String product) {
        List<Product> products = new ArrayList<>();
        Random rand = new Random();
        
        // Amazon products
        products.add(new Product(
            product + " Pro 16GB RAM", 
            "$" + (800 + rand.nextInt(400)), 
            "https://amazon.com/dp/B08N5WRWNW", 
            "https://m.media-amazon.com/images/I/71jG+e7roXL._AC_SX679_.jpg",
            "Amazon", 
            "4." + (3 + rand.nextInt(3))
        ));
        
        products.add(new Product(
            product + " Standard 8GB", 
            "$" + (600 + rand.nextInt(200)), 
            "https://amazon.com/dp/B08N5WRWNW", 
            "https://m.media-amazon.com/images/I/71jG+e7roXL._AC_SX679_.jpg",
            "Amazon", 
            "4." + (2 + rand.nextInt(3))
        ));
        
        // Flipkart products
        products.add(new Product(
            product + " Latest Model", 
            "₹" + (50000 + rand.nextInt(30000)), 
            "https://flipkart.com/apple-macbook-air-m1-chip/p/itm6c6470c0c5c8e", 
            "https://rukminim1.flixcart.com/image/416/416/kp5sya80/computer/r/e/t/na-thin-and-light-laptop-apple-original-imag3gh5xnhzygxe.jpeg",
            "Flipkart", 
            "4." + (3 + rand.nextInt(3))
        ));
        
        // eBay products
        products.add(new Product(
            product + " Refurbished", 
            "$" + (400 + rand.nextInt(200)), 
            "https://ebay.com/itm/Apple-MacBook-Air-13-3-Laptop/174234567890", 
            "https://i.ebayimg.com/images/g/abc123/s-l1600.jpg",
            "eBay", 
            "4." + (1 + rand.nextInt(3))
        ));
        
        // Walmart products
        products.add(new Product(
            product + " Value Edition", 
            "$" + (500 + rand.nextInt(300)), 
            "https://walmart.com/ip/Apple-MacBook-Air-13-3/123456789", 
            "https://i5.walmartimages.com/asr/abc123.jpeg",
            "Walmart", 
            "4." + (2 + rand.nextInt(2))
        ));
        
        return products;
    }
    
    private static String createComparisonPage(String product, List<Product> products) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html>\n<head>\n");
        html.append("<title>Product Comparison - ").append(product).append("</title>\n");
        html.append("<style>\n");
        html.append("body { font-family: Arial, sans-serif; margin: 20px; background: #f5f5f5; }\n");
        html.append(".header { text-align: center; background: #2196F3; color: white; padding: 20px; border-radius: 10px; }\n");
        html.append(".container { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 20px; margin-top: 20px; }\n");
        html.append(".product-card { background: white; border-radius: 10px; padding: 20px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }\n");
        html.append(".site-name { font-size: 18px; font-weight: bold; color: #333; margin-bottom: 10px; }\n");
        html.append(".product-name { font-size: 16px; color: #555; margin-bottom: 10px; }\n");
        html.append(".price { font-size: 24px; font-weight: bold; color: #e91e63; margin-bottom: 10px; }\n");
        html.append(".rating { color: #ff9800; font-size: 18px; margin-bottom: 15px; }\n");
        html.append(".visit-btn { background: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block; }\n");
        html.append(".visit-btn:hover { background: #45a049; }\n");
        html.append(".summary { background: white; padding: 20px; border-radius: 10px; margin-top: 20px; text-align: center; }\n");
        html.append("</style>\n</head>\n<body>\n");
        
        html.append("<div class='header'>\n");
        html.append("<h1>🛒 Product Comparison Results</h1>\n");
        html.append("<h2>Search: ").append(product).append("</h2>\n");
        html.append("<p>Comparing prices across ").append(products.size()).append(" products from multiple sites</p>\n");
        html.append("</div>\n");
        
        html.append("<div class='container'>\n");
        
        for (Product p : products) {
            html.append("<div class='product-card'>\n");
            html.append("<div class='site-name'>🛒 ").append(p.getSite()).append("</div>\n");
            html.append("<div class='product-name'>").append(p.getName()).append("</div>\n");
            html.append("<div class='price'>").append(p.getPrice()).append("</div>\n");
            html.append("<div class='rating'>⭐ ").append(p.getRating()).append("/5</div>\n");
            html.append("<a href='").append(p.getUrl()).append("' target='_blank' class='visit-btn'>Visit Site</a>\n");
            html.append("</div>\n");
        }
        
        html.append("</div>\n");
        
        html.append("<div class='summary'>\n");
        html.append("<h3>📊 Comparison Summary</h3>\n");
        html.append("<p>✅ Found ").append(products.size()).append(" products across multiple e-commerce sites</p>\n");
        html.append("<p>💰 Price range: $400 - $1200 | ₹50,000 - ₹80,000</p>\n");
        html.append("<p>⭐ Average rating: 4.2/5</p>\n");
        html.append("<p>🚀 Click 'Visit Site' buttons to view products on original websites</p>\n");
        html.append("</div>\n");
        
        html.append("</body>\n</html>");
        
        try {
            File file = new File("product_comparison.html");
            FileWriter writer = new FileWriter(file);
            writer.write(html.toString());
            writer.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            System.err.println("Error creating HTML file: " + e.getMessage());
            return null;
        }
    }
    
    private static void openWebsites() {
        String[] sites = {
            "https://amazon.com",
            "https://flipkart.com", 
            "https://ebay.com",
            "https://walmart.com"
        };
        
        try {
            Desktop desktop = Desktop.getDesktop();
            for (String site : sites) {
                desktop.browse(URI.create(site));
                Thread.sleep(1000); // Delay between opening sites
            }
        } catch (Exception e) {
            System.err.println("Error opening websites: " + e.getMessage());
        }
    }
    
    private static void openComparisonPage(String htmlFile) {
        if (htmlFile != null) {
            try {
                Desktop.getDesktop().open(new File(htmlFile));
            } catch (IOException e) {
                System.err.println("Error opening comparison page: " + e.getMessage());
            }
        }
    }
}