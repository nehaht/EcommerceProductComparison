package com.productcomparison;

import java.util.List;
import java.util.Scanner;

public class ProductComparisonApp {
    
    public static void main(String[] args) {
        ProductComparisonService service = new ProductComparisonService();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== E-commerce Product Comparison Tool ===");
        System.out.println("Enter product name to search (or 'quit' to exit):");
        
        while (true) {
            System.out.print("\nSearch: ");
            String searchQuery = scanner.nextLine().trim();
            
            if ("quit".equalsIgnoreCase(searchQuery)) {
                break;
            }
            
            if (searchQuery.isEmpty()) {
                System.out.println("Please enter a valid product name.");
                continue;
            }
            
            System.out.println("\nSearching for: " + searchQuery);
            System.out.println("Please wait...\n");
            
            List<Product> products = service.compareProducts(searchQuery);
            
            if (products.isEmpty()) {
                System.out.println("No products found.");
            } else {
                displayProducts(products);
            }
        }
        
        service.shutdown();
        scanner.close();
        System.out.println("Thank you for using Product Comparison Tool!");
    }
    
    private static void displayProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products found across all sites.");
            return;
        }
        
        // Group by site
        java.util.Map<String, java.util.List<Product>> productsBySite = products.stream()
            .collect(java.util.stream.Collectors.groupingBy(Product::getSite));
        
        System.out.println("\n" + "=".repeat(120));
        System.out.println("                           PRODUCT COMPARISON RESULTS");
        System.out.println("=".repeat(120));
        
        for (java.util.Map.Entry<String, java.util.List<Product>> entry : productsBySite.entrySet()) {
            String site = entry.getKey();
            java.util.List<Product> siteProducts = entry.getValue();
            
            System.out.println("\n🛒 " + site.toUpperCase() + " (" + siteProducts.size() + " products)");
            System.out.println("-".repeat(120));
            System.out.printf("%-60s %-20s %-15s%n", "PRODUCT NAME", "PRICE", "RATING");
            System.out.println("-".repeat(120));
            
            for (Product product : siteProducts) {
                String name = product.getName().length() > 57 ? 
                             product.getName().substring(0, 57) + "..." : product.getName();
                System.out.printf("%-60s %-20s %-15s%n", 
                                name, product.getPrice(), product.getRating());
            }
        }
        
        System.out.println("\n" + "=".repeat(120));
        System.out.println("📊 SUMMARY: Found " + products.size() + " products across " + 
                          productsBySite.size() + " sites");
        System.out.println("=".repeat(120));
    }
}