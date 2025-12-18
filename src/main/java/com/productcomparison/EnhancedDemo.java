package com.productcomparison;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.*;
import java.util.List;

public class EnhancedDemo {
    private JFrame frame;
    private JTextField searchField;
    private JTextField minPriceField;
    private JTextField maxPriceField;
    private JComboBox<String> currencyCombo;
    private JPanel resultsPanel;
    private JScrollPane scrollPane;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EnhancedDemo().createAndShowGUI();
        });
    }
    
    private void createAndShowGUI() {
        frame = new JFrame("E-commerce Product Comparison with Price Filter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        
        // Create input panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(new Color(45, 52, 54));
        
        // Search components
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
        currencyCombo.setSelectedIndex(0);
        
        JButton searchButton = new JButton("🔍 Search & Compare");
        searchButton.setBackground(new Color(0, 123, 255));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        
        JButton openSitesButton = new JButton("🌐 Open E-commerce Sites");
        openSitesButton.setBackground(new Color(40, 167, 69));
        openSitesButton.setForeground(Color.WHITE);
        openSitesButton.setFocusPainted(false);
        
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
        
        // Results panel
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(Color.WHITE);
        
        scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Initial message
        showWelcomeMessage();
        
        // Action listeners
        searchButton.addActionListener(new SearchAction());
        openSitesButton.addActionListener(new OpenSitesAction());
        
        // Layout
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        frame.setVisible(true);
    }
    
    private void showWelcomeMessage() {
        resultsPanel.removeAll();
        
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(Color.WHITE);
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JLabel title = new JLabel("🛒 E-commerce Product Comparison Tool");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitle = new JLabel("Compare products across 7 major e-commerce sites with price filtering");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel instructions = new JLabel("<html><center>Enter a product name and optional price range, then click Search & Compare<br/>Click on any product link to visit the site directly</center></html>");
        instructions.setFont(new Font("Arial", Font.PLAIN, 14));
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        welcomePanel.add(title);
        welcomePanel.add(Box.createVerticalStrut(20));
        welcomePanel.add(subtitle);
        welcomePanel.add(Box.createVerticalStrut(20));
        welcomePanel.add(instructions);
        
        resultsPanel.add(welcomePanel);
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
    
    private class SearchAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String product = searchField.getText().trim();
            if (product.isEmpty()) {
                product = "laptop";
                searchField.setText(product);
            }
            
            String minPriceStr = minPriceField.getText().trim();
            String maxPriceStr = maxPriceField.getText().trim();
            String currency = (String) currencyCombo.getSelectedItem();
            
            performSearch(product, minPriceStr, maxPriceStr, currency);
        }
    }
    
    private class OpenSitesAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            openEcommerceSites();
        }
    }
    
    private void performSearch(String product, String minPriceStr, String maxPriceStr, String currency) {
        resultsPanel.removeAll();
        
        // Show searching message
        JLabel searchingLabel = new JLabel("🔍 Searching for: " + product + " across 7 e-commerce sites...");
        searchingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        searchingLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        resultsPanel.add(searchingLabel);
        resultsPanel.revalidate();
        resultsPanel.repaint();
        
        SwingWorker<List<Product>, Void> worker = new SwingWorker<List<Product>, Void>() {
            @Override
            protected List<Product> doInBackground() throws Exception {
                Thread.sleep(2000); // Simulate search time
                return generateFilteredProducts(product, minPriceStr, maxPriceStr, currency);
            }
            
            @Override
            protected void done() {
                try {
                    List<Product> products = get();
                    displayResults(products, product, minPriceStr, maxPriceStr, currency);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        
        worker.execute();
    }
    
    private List<Product> generateFilteredProducts(String product, String minPriceStr, String maxPriceStr, String currency) {
        List<Product> allProducts = new ArrayList<>();
        Random rand = new Random();
        String currencySymbol = getCurrencySymbol(currency);
        
        // Generate products with selected currency
        allProducts.add(new Product(product + " Pro Max 256GB", currencySymbol + convertPrice(900 + rand.nextInt(300), currency), 
            "https://amazon.com/dp/B0BDKXKL8S", "https://m.media-amazon.com/images/I/71xb2xkN5qL._AC_SL1500_.jpg", "Amazon", "4." + (3 + rand.nextInt(3))));
        allProducts.add(new Product(product + " Standard 128GB", currencySymbol + convertPrice(700 + rand.nextInt(200), currency), 
            "https://amazon.com/dp/B0BDKXKL8T", "https://m.media-amazon.com/images/I/71xb2xkN5qL._AC_SL1500_.jpg", "Amazon", "4." + (2 + rand.nextInt(3))));
        
        allProducts.add(new Product(product + " Latest Model", currencySymbol + convertPrice(800 + rand.nextInt(250), currency), 
            "https://flipkart.com/apple-iphone-15-pro/p/itm6c6470c0c5c8e", "https://rukminim2.flixcart.com/image/416/416/xif0q/mobile/k/l/l/-original-imagtc5fz9spysyk.jpeg", "Flipkart", "4." + (3 + rand.nextInt(3))));
        allProducts.add(new Product(product + " Best Deal", currencySymbol + convertPrice(650 + rand.nextInt(200), currency), 
            "https://flipkart.com/apple-iphone-15/p/itm6c6470c0c5c8f", "https://rukminim2.flixcart.com/image/416/416/xif0q/mobile/k/l/l/-original-imagtc5fz9spysyk.jpeg", "Flipkart", "4." + (2 + rand.nextInt(3))));
        
        allProducts.add(new Product(product + " Refurbished", currencySymbol + convertPrice(500 + rand.nextInt(200), currency), 
            "https://ebay.com/itm/Apple-iPhone-15-Pro-Max/174234567890", "https://i.ebayimg.com/images/g/abc123/s-l1600.jpg", "eBay", "4." + (1 + rand.nextInt(3))));
        
        allProducts.add(new Product(product + " Value Pack", currencySymbol + convertPrice(650 + rand.nextInt(150), currency), 
            "https://walmart.com/ip/Apple-iPhone-15-Pro/123456789", "https://i5.walmartimages.com/asr/abc123.jpeg", "Walmart", "4." + (2 + rand.nextInt(2))));
        
        allProducts.add(new Product(product + " Budget Option", currencySymbol + convertPrice(550 + rand.nextInt(100), currency), 
            "https://snapdeal.com/product/apple-iphone-15/123456789", "https://n2.sdlcdn.com/imgs/a/b/c/abc123.jpg", "Snapdeal", "3." + (8 + rand.nextInt(2))));
        
        // Filter by price range
        return filterByPriceRange(allProducts, minPriceStr, maxPriceStr);
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
            case "INR (₹)": return usdPrice * 83; // 1 USD = 83 INR
            case "EUR (€)": return (int)(usdPrice * 0.92); // 1 USD = 0.92 EUR
            case "GBP (£)": return (int)(usdPrice * 0.79); // 1 USD = 0.79 GBP
            default: return usdPrice;
        }
    }
    
    private List<Product> filterByPriceRange(List<Product> products, String minPriceStr, String maxPriceStr) {
        if (minPriceStr.isEmpty() && maxPriceStr.isEmpty()) {
            return products;
        }
        
        List<Product> filtered = new ArrayList<>();
        
        for (Product product : products) {
            double price = extractPrice(product.getPrice());
            
            boolean inRange = true;
            
            if (!minPriceStr.isEmpty()) {
                try {
                    double minPrice = Double.parseDouble(minPriceStr);
                    if (price < minPrice) inRange = false;
                } catch (NumberFormatException e) {
                    // Ignore invalid input
                }
            }
            
            if (!maxPriceStr.isEmpty()) {
                try {
                    double maxPrice = Double.parseDouble(maxPriceStr);
                    if (price > maxPrice) inRange = false;
                } catch (NumberFormatException e) {
                    // Ignore invalid input
                }
            }
            
            if (inRange) {
                filtered.add(product);
            }
        }
        
        return filtered;
    }
    
    private double extractPrice(String priceStr) {
        // Extract numeric value from price string
        String numericPrice = priceStr.replaceAll("[^0-9.]", "");
        try {
            return Double.parseDouble(numericPrice);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private void displayResults(List<Product> products, String searchTerm, String minPrice, String maxPrice, String currency) {
        resultsPanel.removeAll();
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(248, 249, 250));
        
        String currencySymbol = getCurrencySymbol(currency);
        String filterInfo = "";
        if (!minPrice.isEmpty() || !maxPrice.isEmpty()) {
            filterInfo = " (Price filter: " + 
                        (!minPrice.isEmpty() ? currencySymbol + minPrice + "+" : "") +
                        (!maxPrice.isEmpty() ? " - " + currencySymbol + maxPrice : "") + 
                        " in " + currency + ")";
        }
        
        JLabel headerLabel = new JLabel("🎯 Results for: " + searchTerm + filterInfo + " - " + products.size() + " products found");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(headerLabel);
        
        resultsPanel.add(headerPanel);
        
        if (products.isEmpty()) {
            JLabel noResultsLabel = new JLabel("No products found in the specified price range. Try adjusting your filters.");
            noResultsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            noResultsLabel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
            resultsPanel.add(noResultsLabel);
        } else {
            // Group by site
            Map<String, List<Product>> grouped = new HashMap<>();
            for (Product p : products) {
                grouped.computeIfAbsent(p.getSite(), k -> new ArrayList<>()).add(p);
            }
            
            // Display each site's products
            for (Map.Entry<String, List<Product>> entry : grouped.entrySet()) {
                String site = entry.getKey();
                List<Product> siteProducts = entry.getValue();
                
                // Site header
                JPanel sitePanel = new JPanel();
                sitePanel.setLayout(new BoxLayout(sitePanel, BoxLayout.Y_AXIS));
                sitePanel.setBorder(BorderFactory.createTitledBorder("🛒 " + site + " (" + siteProducts.size() + " products)"));
                sitePanel.setBackground(Color.WHITE);
                
                for (Product product : siteProducts) {
                    JPanel productPanel = createProductPanel(product);
                    sitePanel.add(productPanel);
                }
                
                resultsPanel.add(sitePanel);
            }
        }
        
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
    
    private JPanel createProductPanel(Product product) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        
        // Product info
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBackground(Color.WHITE);
        
        JLabel nameLabel = new JLabel("📦 " + product.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel priceLabel = new JLabel("💰 " + product.getPrice());
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priceLabel.setForeground(new Color(220, 53, 69));
        
        JLabel ratingLabel = new JLabel("⭐ " + product.getRating() + "/5");
        ratingLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(ratingLabel);
        
        // Visit button
        JButton visitButton = new JButton("Visit " + product.getSite());
        visitButton.setBackground(new Color(0, 123, 255));
        visitButton.setForeground(Color.WHITE);
        visitButton.setFocusPainted(false);
        visitButton.addActionListener(e -> openURL(product.getUrl()));
        
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(visitButton, BorderLayout.EAST);
        
        // Add hover effect
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(248, 249, 250));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.WHITE);
            }
        });
        
        return panel;
    }
    
    private void openURL(String url) {
        try {
            Desktop.getDesktop().browse(URI.create(url));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Could not open URL: " + url, "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        
        for (String site : sites) {
            openURL(site);
            try {
                Thread.sleep(1000); // Delay between opening sites
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}