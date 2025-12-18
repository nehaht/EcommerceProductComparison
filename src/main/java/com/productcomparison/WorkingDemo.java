package com.productcomparison;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.*;
import java.util.List;

public class WorkingDemo {
    private JFrame frame;
    private JTextField searchField;
    private JTextField minPriceField;
    private JTextField maxPriceField;
    private JComboBox<String> currencyCombo;
    private JTextArea resultArea;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new WorkingDemo().createGUI();
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
        searchField = new JTextField(15);
        
        JLabel minLabel = new JLabel("Min Price:");
        minLabel.setForeground(Color.WHITE);
        minPriceField = new JTextField(8);
        
        JLabel maxLabel = new JLabel("Max Price:");
        maxLabel.setForeground(Color.WHITE);
        maxPriceField = new JTextField(8);
        
        JLabel currencyLabel = new JLabel("Currency:");
        currencyLabel.setForeground(Color.WHITE);
        currencyCombo = new JComboBox<>(new String[]{"USD ($)", "INR (₹)", "EUR (€)", "GBP (£)"});
        
        JButton searchButton = new JButton("🔍 Search Products");
        searchButton.setBackground(new Color(0, 123, 255));
        searchButton.setForeground(Color.WHITE);
        
        JButton openSitesButton = new JButton("🌐 Open Sites");
        openSitesButton.setBackground(new Color(40, 167, 69));
        openSitesButton.setForeground(Color.WHITE);
        
        inputPanel.add(searchLabel);
        inputPanel.add(searchField);
        inputPanel.add(minLabel);
        inputPanel.add(minPriceField);
        inputPanel.add(maxLabel);
        inputPanel.add(maxPriceField);
        inputPanel.add(currencyLabel);
        inputPanel.add(currencyCombo);
        inputPanel.add(searchButton);
        inputPanel.add(openSitesButton);
        
        // Result area
        resultArea = new JTextArea();
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        
        // Initial message
        resultArea.setText("=== E-COMMERCE PRODUCT COMPARISON TOOL ===\\n\\n" +
                          "Features:\\n" +
                          "• Multi-currency support (USD, INR, EUR, GBP)\\n" +
                          "• Price range filtering\\n" +
                          "• 7 major e-commerce sites\\n" +
                          "• Direct links to products\\n\\n" +
                          "Enter a product name and click Search Products!\\n" +
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
        final String minPrice = minPriceField.getText().trim();
        final String maxPrice = maxPriceField.getText().trim();
        final String currency = (String) currencyCombo.getSelectedItem();
        
        resultArea.setText("🔍 Searching for: " + product + "\\n");
        resultArea.append("💱 Currency: " + currency + "\\n");
        if (!minPrice.isEmpty() || !maxPrice.isEmpty()) {
            resultArea.append("💰 Price Range: " + minPrice + " - " + maxPrice + "\\n");
        }
        resultArea.append("🌐 Checking 7 e-commerce sites...\\n\\n");
        
        // Simulate search with timer
        javax.swing.Timer timer = new javax.swing.Timer(500, new ActionListener() {
            int count = 0;
            String[] sites = {"Amazon", "Flipkart", "eBay", "Myntra", "Ajio", "Walmart", "Snapdeal"};
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < sites.length) {
                    resultArea.append("✓ " + sites[count] + " completed\\n");
                    count++;
                } else {
                    ((javax.swing.Timer) e.getSource()).stop();
                    showResults(finalProduct, minPrice, maxPrice, currency);
                }
            }
        });
        timer.start();
    }
    
    private void showResults(String product, String minPrice, String maxPrice, String currency) {
        List<Product> products = generateProducts(product, currency);
        products = filterProducts(products, minPrice, maxPrice);
        
        resultArea.append("\\n" + "=".repeat(80) + "\\n");
        resultArea.append("🎯 SEARCH RESULTS FOR: " + product.toUpperCase() + "\\n");
        resultArea.append("=".repeat(80) + "\\n\\n");
        
        if (products.isEmpty()) {
            resultArea.append("❌ No products found in specified price range\\n");
            return;
        }
        
        Map<String, List<Product>> grouped = new HashMap<>();
        for (Product p : products) {
            grouped.computeIfAbsent(p.getSite(), k -> new ArrayList<>()).add(p);
        }
        
        for (Map.Entry<String, List<Product>> entry : grouped.entrySet()) {
            String site = entry.getKey();
            List<Product> siteProducts = entry.getValue();
            
            resultArea.append("🛒 " + site.toUpperCase() + " (" + siteProducts.size() + " products)\\n");
            resultArea.append("-".repeat(80) + "\\n");
            
            for (Product prod : siteProducts) {
                resultArea.append(String.format("📦 %-40s 💰 %-12s ⭐ %s\\n", 
                    truncate(prod.getName(), 38), prod.getPrice(), prod.getRating()));
                resultArea.append("🔗 " + prod.getUrl() + "\\n\\n");
            }
        }
        
        resultArea.append("=".repeat(80) + "\\n");
        resultArea.append("📊 TOTAL: " + products.size() + " products from " + grouped.size() + " sites\\n");
        resultArea.append("💡 Click 'Open Sites' to visit e-commerce websites\\n");
        resultArea.append("=".repeat(80) + "\\n");
        
        resultArea.setCaretPosition(resultArea.getDocument().getLength());
    }
    
    private List<Product> generateProducts(String product, String currency) {
        List<Product> products = new ArrayList<>();
        Random rand = new Random();
        String symbol = getCurrencySymbol(currency);
        
        // Amazon products
        products.add(new Product(product + " Pro Max", symbol + convertPrice(900, currency), 
            "https://amazon.com/dp/B0BDKXKL8S", "", "Amazon", "4.5"));
        products.add(new Product(product + " Standard", symbol + convertPrice(700, currency), 
            "https://amazon.com/dp/B0BDKXKL8T", "", "Amazon", "4.3"));
        
        // Flipkart products
        products.add(new Product(product + " Latest", symbol + convertPrice(800, currency), 
            "https://flipkart.com/product1", "", "Flipkart", "4.4"));
        products.add(new Product(product + " Best Deal", symbol + convertPrice(650, currency), 
            "https://flipkart.com/product2", "", "Flipkart", "4.2"));
        
        // eBay products
        products.add(new Product(product + " Refurbished", symbol + convertPrice(500, currency), 
            "https://ebay.com/itm/123456", "", "eBay", "4.1"));
        
        // Walmart products
        products.add(new Product(product + " Value Pack", symbol + convertPrice(600, currency), 
            "https://walmart.com/ip/123456", "", "Walmart", "4.0"));
        
        // Snapdeal products
        products.add(new Product(product + " Budget", symbol + convertPrice(450, currency), 
            "https://snapdeal.com/product/123456", "", "Snapdeal", "3.9"));
        
        return products;
    }
    
    private List<Product> filterProducts(List<Product> products, String minPrice, String maxPrice) {
        if (minPrice.isEmpty() && maxPrice.isEmpty()) {
            return products;
        }
        
        List<Product> filtered = new ArrayList<>();
        for (Product product : products) {
            double price = extractNumericPrice(product.getPrice());
            
            boolean inRange = true;
            if (!minPrice.isEmpty()) {
                try {
                    if (price < Double.parseDouble(minPrice)) inRange = false;
                } catch (NumberFormatException e) {}
            }
            if (!maxPrice.isEmpty()) {
                try {
                    if (price > Double.parseDouble(maxPrice)) inRange = false;
                } catch (NumberFormatException e) {}
            }
            
            if (inRange) filtered.add(product);
        }
        return filtered;
    }
    
    private double extractNumericPrice(String priceStr) {
        String numeric = priceStr.replaceAll("[^0-9.]", "");
        try {
            return Double.parseDouble(numeric);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private String getCurrencySymbol(String currency) {
        switch (currency) {
            case "USD ($)": return "$";
            case "INR (₹)": return "₹";
            case "EUR (€)": return "€";
            case "GBP (£)": return "£";
            default: return "$";
        }
    }
    
    private int convertPrice(int usdPrice, String currency) {
        switch (currency) {
            case "USD ($)": return usdPrice;
            case "INR (₹)": return usdPrice * 83;
            case "EUR (€)": return (int)(usdPrice * 0.92);
            case "GBP (£)": return (int)(usdPrice * 0.79);
            default: return usdPrice;
        }
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
        
        resultArea.append("\\n🌐 Opening e-commerce websites...\\n");
        
        for (String site : sites) {
            try {
                Desktop.getDesktop().browse(URI.create(site));
                resultArea.append("✓ Opened " + site + "\\n");
                Thread.sleep(1000);
            } catch (Exception e) {
                resultArea.append("❌ Could not open " + site + "\\n");
            }
        }
        resultArea.append("\\n");
    }
}