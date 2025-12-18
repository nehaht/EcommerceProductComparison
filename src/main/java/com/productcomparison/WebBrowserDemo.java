package com.productcomparison;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.util.*;

public class WebBrowserDemo {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== WEB BROWSER PRODUCT COMPARISON DEMO ===");
        System.out.print("Enter product to search: ");
        String product = scanner.nextLine().trim();
        
        if (product.isEmpty()) product = "laptop";
        
        System.out.println("🌐 Generating web comparison page...");
        System.out.println("🔍 Searching for: " + product);
        
        // Generate products
        List<Product> products = generateProducts(product);
        
        // Create HTML page
        String htmlFile = createComparisonPage(product, products);
        
        // Open in browser
        openInBrowser(htmlFile);
        
        System.out.println("✅ Comparison page opened in your default browser!");
        scanner.close();
    }
    
    private static List<Product> generateProducts(String product) {
        List<Product> products = new ArrayList<>();
        Random rand = new Random();
        
        // Amazon products
        products.add(new Product(product + " Pro Max 256GB", "$" + (900 + rand.nextInt(300)), 
            "https://amazon.com/dp/B0BDKXKL8S", "", "Amazon", "4.5"));
        products.add(new Product(product + " Standard 128GB", "$" + (700 + rand.nextInt(200)), 
            "https://amazon.com/dp/B0BDKXKL8T", "", "Amazon", "4.3"));
        
        // Flipkart products
        products.add(new Product(product + " Latest Model", "₹" + (60000 + rand.nextInt(20000)), 
            "https://flipkart.com/apple-iphone-15-pro/p/itm6c6470c0c5c8e", "", "Flipkart", "4.4"));
        products.add(new Product(product + " Best Deal", "₹" + (55000 + rand.nextInt(15000)), 
            "https://flipkart.com/apple-iphone-15/p/itm6c6470c0c5c8f", "", "Flipkart", "4.2"));
        
        // eBay products
        products.add(new Product(product + " Refurbished", "$" + (500 + rand.nextInt(200)), 
            "https://ebay.com/itm/Apple-iPhone-15-Pro-Max/174234567890", "", "eBay", "4.1"));
        
        // Walmart products
        products.add(new Product(product + " Value Pack", "$" + (600 + rand.nextInt(150)), 
            "https://walmart.com/ip/Apple-iPhone-15-Pro/123456789", "", "Walmart", "4.0"));
        
        // Snapdeal products
        products.add(new Product(product + " Budget Option", "₹" + (45000 + rand.nextInt(10000)), 
            "https://snapdeal.com/product/apple-iphone-15/123456789", "", "Snapdeal", "3.9"));
        
        return products;
    }
    
    private static String createComparisonPage(String product, List<Product> products) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head>");
        html.append("<title>Product Comparison - ").append(product).append("</title>");
        html.append("<style>");
        html.append("body{font-family:'Segoe UI',Arial,sans-serif;margin:0;padding:20px;background:linear-gradient(135deg,#667eea 0%,#764ba2 100%);min-height:100vh}");
        html.append(".container{max-width:1200px;margin:0 auto}");
        html.append(".header{text-align:center;background:rgba(255,255,255,0.95);color:#333;padding:30px;border-radius:15px;box-shadow:0 8px 32px rgba(0,0,0,0.1);margin-bottom:30px}");
        html.append(".header h1{margin:0;font-size:2.5em;color:#2c3e50}");
        html.append(".header h2{margin:10px 0 0 0;color:#3498db;font-weight:300}");
        html.append(".products-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(350px,1fr));gap:20px}");
        html.append(".product{background:white;padding:25px;border-radius:12px;box-shadow:0 4px 20px rgba(0,0,0,0.1);transition:transform 0.3s ease,box-shadow 0.3s ease}");
        html.append(".product:hover{transform:translateY(-5px);box-shadow:0 8px 30px rgba(0,0,0,0.15)}");
        html.append(".site{font-size:1.2em;font-weight:bold;color:#e74c3c;margin-bottom:10px;display:flex;align-items:center}");
        html.append(".product-name{font-size:1.1em;color:#2c3e50;margin:10px 0;line-height:1.4}");
        html.append(".price{font-size:1.8em;color:#27ae60;font-weight:bold;margin:15px 0}");
        html.append(".rating{color:#f39c12;font-size:1.1em;margin:10px 0}");
        html.append(".btn{background:linear-gradient(45deg,#3498db,#2980b9);color:white;padding:12px 25px;text-decoration:none;border-radius:25px;display:inline-block;margin-top:15px;transition:all 0.3s ease;font-weight:500}");
        html.append(".btn:hover{background:linear-gradient(45deg,#2980b9,#3498db);transform:scale(1.05)}");
        html.append(".summary{background:rgba(255,255,255,0.95);padding:20px;border-radius:12px;text-align:center;margin-top:30px;box-shadow:0 4px 20px rgba(0,0,0,0.1)}");
        html.append("</style>");
        html.append("</head><body>");
        
        html.append("<div class='header'><h1>🛒 Product Comparison Results</h1>");
        html.append("<h2>").append(product).append("</h2></div>");
        
        for (Product p : products) {
            html.append("<div class='product'>");
            html.append("<div class='site'>🛒 ").append(p.getSite()).append("</div>");
            html.append("<div>📦 ").append(p.getName()).append("</div>");
            html.append("<div class='price'>💰 ").append(p.getPrice()).append("</div>");
            html.append("<div>⭐ ").append(p.getRating()).append("/5</div>");
            html.append("<a href='").append(p.getUrl()).append("' target='_blank' class='btn'>Visit Site</a>");
            html.append("</div>");
        }
        
        html.append("</body></html>");
        
        try {
            File file = new File("comparison.html");
            FileWriter writer = new FileWriter(file);
            writer.write(html.toString());
            writer.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            return null;
        }
    }
    
    private static void openInBrowser(String htmlFile) {
        try {
            Desktop.getDesktop().open(new File(htmlFile));
        } catch (Exception e) {
            System.err.println("Could not open browser");
        }
    }
}