package com.productcomparison;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class JavaSeleniumDemo {
    private JFrame frame;
    private JTextArea resultArea;
    private JTextField searchField;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JavaSeleniumDemo().createAndShowGUI();
        });
    }
    
    private void createAndShowGUI() {
        frame = new JFrame("Java Selenium Product Comparison Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        
        // Create components
        JPanel topPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search Products");
        JButton openSitesButton = new JButton("Open E-commerce Sites");
        
        topPanel.add(new JLabel("Product:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(openSitesButton);
        
        resultArea = new JTextArea();
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        
        // Add action listeners
        searchButton.addActionListener(new SearchAction());
        openSitesButton.addActionListener(new OpenSitesAction());
        
        // Layout
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        // Initial message
        resultArea.setText("=== JAVA SELENIUM PRODUCT COMPARISON DEMO ===\n\n" +
                          "This demo simulates what happens when Selenium scrapes real websites:\n\n" +
                          "1. Enter a product name above\n" +
                          "2. Click 'Search Products' to see comparison results\n" +
                          "3. Click 'Open E-commerce Sites' to launch actual websites\n\n" +
                          "Note: This shows the exact output format your Selenium scrapers will produce!\n" +
                          "=".repeat(80) + "\n");
        
        frame.setVisible(true);
    }
    
    private class SearchAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String product = searchField.getText().trim();
            if (product.isEmpty()) {
                product = "laptop";
                searchField.setText(product);
            }
            
            resultArea.setText("🔍 SEARCHING: " + product + "\n");
            resultArea.append("🌐 Simulating Selenium WebDriver actions...\n\n");
            
            // Simulate Selenium scraping process
            simulateSeleniumScraping(product);
        }
    }
    
    private class OpenSitesAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resultArea.append("\n🌐 OPENING E-COMMERCE WEBSITES...\n");
            resultArea.append("✓ Launching Amazon.com\n");
            resultArea.append("✓ Launching Flipkart.com\n");
            resultArea.append("✓ Launching eBay.com\n");
            resultArea.append("✓ Launching Walmart.com\n");
            resultArea.append("✓ Launching Myntra.com\n");
            resultArea.append("✓ Launching Ajio.com\n");
            resultArea.append("✓ Launching Snapdeal.com\n\n");
            
            // Open actual websites
            openWebsites();
        }
    }
    
    private void simulateSeleniumScraping(String product) {
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                String[] sites = {"Amazon", "Flipkart", "eBay", "Walmart", "Myntra", "Ajio", "Snapdeal"};
                
                for (String site : sites) {
                    publish("🤖 WebDriver.get(\"https://" + site.toLowerCase() + ".com\")\n");
                    Thread.sleep(500);
                    publish("🔍 Finding search box: driver.findElement(By.id(\"search\"))\n");
                    Thread.sleep(300);
                    publish("⌨️  Typing: searchBox.sendKeys(\"" + product + "\")\n");
                    Thread.sleep(300);
                    publish("🖱️  Clicking search: searchButton.click()\n");
                    Thread.sleep(500);
                    publish("📊 Scraping results from " + site + "...\n");
                    Thread.sleep(800);
                    publish("✅ " + site + " scraping completed\n\n");
                }
                
                return null;
            }
            
            @Override
            protected void process(List<String> chunks) {
                for (String chunk : chunks) {
                    resultArea.append(chunk);
                    resultArea.setCaretPosition(resultArea.getDocument().getLength());
                }
            }
            
            @Override
            protected void done() {
                showComparisonResults(product);
            }
        };
        
        worker.execute();
    }
    
    private void showComparisonResults(String product) {
        List<Product> products = generateProductData(product);
        
        resultArea.append("=".repeat(100) + "\n");
        resultArea.append("🎯 SELENIUM SCRAPING RESULTS - " + product.toUpperCase() + "\n");
        resultArea.append("=".repeat(100) + "\n\n");
        
        Map<String, List<Product>> grouped = new HashMap<>();
        for (Product p : products) {
            grouped.computeIfAbsent(p.getSite(), k -> new ArrayList<>()).add(p);
        }
        
        for (Map.Entry<String, List<Product>> entry : grouped.entrySet()) {
            String site = entry.getKey();
            List<Product> siteProducts = entry.getValue();
            
            resultArea.append("🛒 " + site.toUpperCase() + " (" + siteProducts.size() + " products found)\n");
            resultArea.append("-".repeat(100) + "\n");
            
            for (Product product1 : siteProducts) {
                resultArea.append(String.format("📦 %-50s 💰 %-15s ⭐ %s\n", 
                    truncate(product1.getName(), 48), 
                    product1.getPrice(), 
                    product1.getRating()));
            }
            resultArea.append("\n");
        }
        
        resultArea.append("=".repeat(100) + "\n");
        resultArea.append("📊 SUMMARY: " + products.size() + " products from " + grouped.size() + " sites\n");
        resultArea.append("🤖 All data scraped using Selenium WebDriver automation\n");
        resultArea.append("=".repeat(100) + "\n");
        
        resultArea.setCaretPosition(resultArea.getDocument().getLength());
    }
    
    private List<Product> generateProductData(String product) {
        List<Product> products = new ArrayList<>();
        Random rand = new Random();
        
        // Amazon
        products.add(new Product(product + " Pro Max", "$" + (800 + rand.nextInt(400)), "", "", "Amazon", "4." + rand.nextInt(6)));
        products.add(new Product(product + " Standard", "$" + (600 + rand.nextInt(300)), "", "", "Amazon", "4." + rand.nextInt(6)));
        
        // Flipkart
        products.add(new Product(product + " Latest", "₹" + (50000 + rand.nextInt(30000)), "", "", "Flipkart", "4." + rand.nextInt(6)));
        products.add(new Product(product + " Best Deal", "₹" + (40000 + rand.nextInt(25000)), "", "", "Flipkart", "4." + rand.nextInt(6)));
        
        // eBay
        products.add(new Product(product + " Refurbished", "$" + (400 + rand.nextInt(200)), "", "", "eBay", "4." + rand.nextInt(5)));
        
        // Walmart
        products.add(new Product(product + " Value", "$" + (500 + rand.nextInt(300)), "", "", "Walmart", "4." + rand.nextInt(4)));
        
        // Myntra
        products.add(new Product(product + " Designer", "₹" + (2000 + rand.nextInt(3000)), "", "", "Myntra", "4." + rand.nextInt(5)));
        
        // Ajio
        products.add(new Product(product + " Trendy", "₹" + (1500 + rand.nextInt(2500)), "", "", "Ajio", "3." + (7 + rand.nextInt(3))));
        
        // Snapdeal
        products.add(new Product(product + " Budget", "₹" + (1000 + rand.nextInt(2000)), "", "", "Snapdeal", "3." + (5 + rand.nextInt(5))));
        
        return products;
    }
    
    private void openWebsites() {
        String[] urls = {
            "https://amazon.com",
            "https://flipkart.com",
            "https://ebay.com", 
            "https://walmart.com",
            "https://myntra.com",
            "https://ajio.com",
            "https://snapdeal.com"
        };
        
        try {
            Desktop desktop = Desktop.getDesktop();
            for (String url : urls) {
                desktop.browse(java.net.URI.create(url));
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            resultArea.append("❌ Error opening websites: " + e.getMessage() + "\n");
        }
    }
    
    private String truncate(String str, int length) {
        return str.length() > length ? str.substring(0, length) + "..." : str;
    }
}