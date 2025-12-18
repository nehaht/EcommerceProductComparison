package com.productcomparison;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FileOutputDemo {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== FILE OUTPUT PRODUCT COMPARISON DEMO ===");
        System.out.print("Enter product to search: ");
        String product = scanner.nextLine().trim();
        
        if (product.isEmpty()) product = "laptop";
        
        System.out.println("🔍 Searching for: " + product);
        System.out.println("📄 Generating comparison files...");
        
        List<Product> products = generateProducts(product);
        
        // Generate different file formats
        generateTextFile(product, products);
        generateCSVFile(product, products);
        generateJSONFile(product, products);
        
        System.out.println("✅ Files generated:");
        System.out.println("📄 comparison_results.txt");
        System.out.println("📊 comparison_results.csv");
        System.out.println("🔧 comparison_results.json");
        
        scanner.close();
    }
    
    private static List<Product> generateProducts(String product) {
        List<Product> products = new ArrayList<>();
        Random rand = new Random();
        
        products.add(new Product(product + " Pro Max", "$" + (900 + rand.nextInt(300)), 
            "https://amazon.com/dp/B0BDKXKL8S", "", "Amazon", "4.5"));
        products.add(new Product(product + " Latest", "₹" + (60000 + rand.nextInt(20000)), 
            "https://flipkart.com/product1", "", "Flipkart", "4.4"));
        products.add(new Product(product + " Refurbished", "$" + (500 + rand.nextInt(200)), 
            "https://ebay.com/itm/123456", "", "eBay", "4.1"));
        
        return products;
    }
    
    private static void generateTextFile(String product, List<Product> products) {
        try (FileWriter writer = new FileWriter("comparison_results.txt")) {
            writer.write("=== PRODUCT COMPARISON RESULTS ===\n");
            writer.write("Search: " + product + "\n");
            writer.write("Date: " + new Date() + "\n\n");
            
            for (Product p : products) {
                writer.write("Site: " + p.getSite() + "\n");
                writer.write("Product: " + p.getName() + "\n");
                writer.write("Price: " + p.getPrice() + "\n");
                writer.write("Rating: " + p.getRating() + "/5\n");
                writer.write("URL: " + p.getUrl() + "\n");
                writer.write("-".repeat(50) + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error creating text file");
        }
    }
    
    private static void generateCSVFile(String product, List<Product> products) {
        try (FileWriter writer = new FileWriter("comparison_results.csv")) {
            writer.write("Site,Product,Price,Rating,URL\n");
            
            for (Product p : products) {
                writer.write(String.format("%s,\"%s\",%s,%s,%s\n",
                    p.getSite(), p.getName(), p.getPrice(), p.getRating(), p.getUrl()));
            }
        } catch (IOException e) {
            System.err.println("Error creating CSV file");
        }
    }
    
    private static void generateJSONFile(String product, List<Product> products) {
        try (FileWriter writer = new FileWriter("comparison_results.json")) {
            writer.write("{\n");
            writer.write("  \"search\": \"" + product + "\",\n");
            writer.write("  \"timestamp\": \"" + new Date() + "\",\n");
            writer.write("  \"products\": [\n");
            
            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                writer.write("    {\n");
                writer.write("      \"site\": \"" + p.getSite() + "\",\n");
                writer.write("      \"name\": \"" + p.getName() + "\",\n");
                writer.write("      \"price\": \"" + p.getPrice() + "\",\n");
                writer.write("      \"rating\": \"" + p.getRating() + "\",\n");
                writer.write("      \"url\": \"" + p.getUrl() + "\"\n");
                writer.write("    }" + (i < products.size() - 1 ? "," : "") + "\n");
            }
            
            writer.write("  ]\n");
            writer.write("}\n");
        } catch (IOException e) {
            System.err.println("Error creating JSON file");
        }
    }
}