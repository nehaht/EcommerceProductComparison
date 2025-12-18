package com.productcomparison;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.*;
import java.util.List;

public class SimpleDemo {
    private JFrame frame;
    private JTextField searchField;
    private JTextArea resultArea;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SimpleDemo().createGUI();
        });
    }
    
    private void createGUI() {
        frame = new JFrame("E-commerce Product Comparison Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        
        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(new Color(52, 58, 64));
        
        JLabel searchLabel = new JLabel("Product:");
        searchLabel.setForeground(Color.WHITE);
        searchField = new JTextField(20);
        
        JButton searchButton = new JButton("🔍 Search Products");
        searchButton.setBackground(new Color(0, 123, 255));
        searchButton.setForeground(Color.WHITE);
        
        JButton openSitesButton = new JButton("🌐 Open Sites");
        openSitesButton.setBackground(new Color(40, 167, 69));
        openSitesButton.setForeground(Color.WHITE);
        
        inputPanel.add(searchLabel);
        inputPanel.add(searchField);
        inputPanel.add(searchButton);
        inputPanel.add(openSitesButton);
        
        // Result area
        resultArea = new JTextArea();
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        
        // Initial message
        resultArea.setText("=== E-COMMERCE PRODUCT COMPARISON TOOL ===\n\n" +
                          "Features:\n" +
                          "• Compare products across 7 major e-commerce sites\n" +
                          "• Real-time search simulation\n" +
                          "• Direct links to products\n" +
                          "• Site-wise organized results\n\n" +
                          "Enter a product name and click Search Products!\n" +
                          "=".repeat(60));
        
        // Action listeners
        searchButton.addActionListener(e -> performSearch());
        openSitesButton.addActionListener(e -> openEcommerceSites());
        
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    private void performSearch() {
        String product = searchField.getText().trim();
        if (product.isEmpty()) {
            product = "laptop";
            searchField.setText(product);
        }
        
        final String finalProduct = product;
        
        resultArea.setText("🔍 Searching for: " + finalProduct + "\n");
        resultArea.append("🌐 Checking 7 e-commerce sites...\n\n");
        
        // Simulate search with timer
        javax.swing.Timer timer = new javax.swing.Timer(500, new ActionListener() {
            int count = 0;
            String[] sites = {"Amazon", "Flipkart", "eBay", "Myntra", "Ajio", "Walmart", "Snapdeal"};
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < sites.length) {
                    resultArea.append("✓ " + sites[count] + " completed\n");
                    count++;
                } else {
                    ((javax.swing.Timer) e.getSource()).stop();
                    showResults(finalProduct);
                }
            }
        });
        timer.start();
    }
    
    private void showResults(String product) {
        List<Product> products = generateProducts(product);
        
        resultArea.append("\n" + "=".repeat(80) + "\n");
        resultArea.append("🎯 SEARCH RESULTS FOR: " + product.toUpperCase() + "\n");
        resultArea.append("=".repeat(80) + "\n\n");
        
        Map<String, List<Product>> grouped = new HashMap<>();
        for (Product p : products) {
            grouped.computeIfAbsent(p.getSite(), k -> new ArrayList<>()).add(p);
        }
        
        for (Map.Entry<String, List<Product>> entry : grouped.entrySet()) {
            String site = entry.getKey();
            List<Product> siteProducts = entry.getValue();
            
            resultArea.append("🛒 " + site.toUpperCase() + " (" + siteProducts.size() + " products)\n");
            resultArea.append("-".repeat(80) + "\n");
            
            for (Product prod : siteProducts) {
                resultArea.append(String.format("📦 %-40s 💰 %-12s ⭐ %s\n", 
                    truncate(prod.getName(), 38), prod.getPrice(), prod.getRating()));
                resultArea.append("🔗 " + prod.getUrl() + "\n\n");
            }
        }
        
        resultArea.append("=".repeat(80) + "\n");
        resultArea.append("📊 TOTAL: " + products.size() + " products from " + grouped.size() + " sites\n");
        resultArea.append("💡 Click 'Open Sites' to visit e-commerce websites\n");
        resultArea.append("=".repeat(80) + "\n");
        
        resultArea.setCaretPosition(resultArea.getDocument().getLength());
    }
    
    private List<Product> generateProducts(String product) {
        List<Product> products = new ArrayList<>();
        Random rand = new Random();
        
        // Amazon products
        products.add(new Product(product + " Pro Max", "$" + (900 + rand.nextInt(300)), 
            "https://amazon.com/dp/B0BDKXKL8S", "", "Amazon", "4.5"));
        products.add(new Product(product + " Standard", "$" + (700 + rand.nextInt(200)), 
            "https://amazon.com/dp/B0BDKXKL8T", "", "Amazon", "4.3"));
        
        // Flipkart products
        products.add(new Product(product + " Latest", "₹" + (60000 + rand.nextInt(20000)), 
            "https://flipkart.com/product1", "", "Flipkart", "4.4"));
        products.add(new Product(product + " Best Deal", "₹" + (55000 + rand.nextInt(15000)), 
            "https://flipkart.com/product2", "", "Flipkart", "4.2"));
        
        // eBay products
        products.add(new Product(product + " Refurbished", "$" + (500 + rand.nextInt(200)), 
            "https://ebay.com/itm/123456", "", "eBay", "4.1"));
        
        // Walmart products
        products.add(new Product(product + " Value Pack", "$" + (600 + rand.nextInt(150)), 
            "https://walmart.com/ip/123456", "", "Walmart", "4.0"));
        
        // Myntra products (fashion items)
        if (product.toLowerCase().contains("shirt") || product.toLowerCase().contains("shoe") || 
            product.toLowerCase().contains("dress") || product.toLowerCase().contains("watch")) {
            products.add(new Product(product + " Designer", "₹" + (2000 + rand.nextInt(3000)), 
                "https://myntra.com/product1", "", "Myntra", "4.1"));
        }
        
        // Ajio products (fashion items)
        if (product.toLowerCase().contains("shirt") || product.toLowerCase().contains("shoe") || 
            product.toLowerCase().contains("dress") || product.toLowerCase().contains("jean")) {
            products.add(new Product(product + " Trendy", "₹" + (1500 + rand.nextInt(2500)), 
                "https://ajio.com/product1", "", "Ajio", "3.9"));
        }
        
        // Snapdeal products
        products.add(new Product(product + " Budget", "₹" + (3000 + rand.nextInt(2000)), 
            "https://snapdeal.com/product/123456", "", "Snapdeal", "3.8"));
        
        return products;
    }
    
    private String truncate(String str, int length) {
        return str.length() > length ? str.substring(0, length) + "..." : str;
    }
    
    private void openEcommerceSites() {
        String[] sites = {
            "https://amazon.com",
            "https://flipkart.com",
            "https://ebay.com",
            "https://walmart.com",
            "https://myntra.com",
            "https://ajio.com",
            "https://snapdeal.com"
        };
        
        resultArea.append("\n🌐 Opening e-commerce websites...\n");
        
        for (String site : sites) {
            try {
                Desktop.getDesktop().browse(URI.create(site));
                resultArea.append("✓ Opened " + site + "\n");
                Thread.sleep(1000);
            } catch (Exception e) {
                resultArea.append("❌ Could not open " + site + "\n");
            }
        }
        resultArea.append("\n");
    }
}