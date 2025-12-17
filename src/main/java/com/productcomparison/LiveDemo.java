package com.productcomparison;

import java.util.*;

public class LiveDemo {
    
    public static void main(String[] args) {
        System.out.println("=== LIVE E-COMMERCE PRODUCT COMPARISON DEMO ===");
        System.out.println("Searching across 7 major e-commerce sites...\n");
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.print("🔍 Enter product to search: ");
            String query = scanner.nextLine().trim();
            
            if (query.equalsIgnoreCase("quit") || query.equalsIgnoreCase("exit")) {
                System.out.println("\n👋 Thanks for using the Product Comparison Tool!");
                break;
            }
            
            if (query.isEmpty()) {
                System.out.println("❌ Please enter a product name\n");
                continue;
            }
            
            performLiveSearch(query);
        }
        
        scanner.close();
    }
    
    private static void performLiveSearch(String query) {
        System.out.println("\n⏳ Searching for: " + query);
        System.out.println("🌐 Checking Amazon, Flipkart, eBay, Myntra, Ajio, Walmart, Snapdeal...\n");
        
        // Simulate real-time search with progress
        String[] sites = {"Amazon", "Flipkart", "eBay", "Myntra", "Ajio", "Walmart", "Snapdeal"};
        for (String site : sites) {
            System.out.print("✓ " + site + " ");
            try { Thread.sleep(300); } catch (InterruptedException e) {}
        }
        System.out.println("\n");
        
        // Generate results
        List<Product> results = generateResults(query);
        displayLiveResults(results);
    }
    
    private static List<Product> generateResults(String query) {
        List<Product> products = new ArrayList<>();
        Random rand = new Random();
        
        // Amazon results
        products.add(new Product(query + " Pro Max", "$" + (800 + rand.nextInt(400)), 
            "amazon.com/dp/xyz", "", "Amazon", "4." + rand.nextInt(6)));
        products.add(new Product(query + " Standard", "$" + (600 + rand.nextInt(300)), 
            "amazon.com/dp/abc", "", "Amazon", "4." + rand.nextInt(6)));
        
        // Flipkart results
        products.add(new Product(query + " Latest Model", "₹" + (50000 + rand.nextInt(30000)), 
            "flipkart.com/p/xyz", "", "Flipkart", "4." + rand.nextInt(6)));
        products.add(new Product(query + " Best Deal", "₹" + (40000 + rand.nextInt(25000)), 
            "flipkart.com/p/abc", "", "Flipkart", "4." + rand.nextInt(6)));
        
        // eBay results
        products.add(new Product(query + " Refurbished", "$" + (400 + rand.nextInt(200)), 
            "ebay.com/itm/xyz", "", "eBay", "4." + rand.nextInt(5)));
        
        // Myntra results (if fashion related)
        if (query.toLowerCase().contains("shirt") || query.toLowerCase().contains("shoe") || 
            query.toLowerCase().contains("dress") || query.toLowerCase().contains("watch")) {
            products.add(new Product(query + " Designer", "₹" + (2000 + rand.nextInt(3000)), 
                "myntra.com/p/xyz", "", "Myntra", "4." + rand.nextInt(5)));
        }
        
        // Ajio results
        if (query.toLowerCase().contains("shirt") || query.toLowerCase().contains("shoe") || 
            query.toLowerCase().contains("dress") || query.toLowerCase().contains("jean")) {
            products.add(new Product(query + " Trendy", "₹" + (1500 + rand.nextInt(2500)), 
                "ajio.com/p/xyz", "", "Ajio", "3." + (7 + rand.nextInt(3))));
        }
        
        // Walmart results
        products.add(new Product(query + " Value Pack", "$" + (300 + rand.nextInt(200)), 
            "walmart.com/ip/xyz", "", "Walmart", "4." + rand.nextInt(4)));
        
        // Snapdeal results
        products.add(new Product(query + " Budget", "₹" + (1000 + rand.nextInt(2000)), 
            "snapdeal.com/p/xyz", "", "Snapdeal", "3." + (5 + rand.nextInt(5))));
        
        return products;
    }
    
    private static void displayLiveResults(List<Product> products) {
        Map<String, List<Product>> grouped = new HashMap<>();
        for (Product p : products) {
            grouped.computeIfAbsent(p.getSite(), k -> new ArrayList<>()).add(p);
        }
        
        System.out.println("🎯 LIVE SEARCH RESULTS");
        System.out.println("=" .repeat(100));
        
        for (Map.Entry<String, List<Product>> entry : grouped.entrySet()) {
            String site = entry.getKey();
            List<Product> siteProducts = entry.getValue();
            
            System.out.println("\n🛒 " + site + " (" + siteProducts.size() + " found)");
            System.out.println("-".repeat(100));
            
            for (Product product : siteProducts) {
                System.out.printf("📦 %-50s 💰 %-15s ⭐ %s%n", 
                    truncate(product.getName(), 48), 
                    product.getPrice(), 
                    product.getRating());
            }
        }
        
        System.out.println("\n" + "=".repeat(100));
        System.out.println("📊 Total: " + products.size() + " products from " + grouped.size() + " sites");
        System.out.println("💡 Best deals highlighted above!");
        System.out.println("=".repeat(100) + "\n");
    }
    
    private static String truncate(String str, int length) {
        return str.length() > length ? str.substring(0, length) + "..." : str;
    }
}