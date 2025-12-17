package com.productcomparison;

public class Product {
    private String name;
    private String price;
    private String url;
    private String imageUrl;
    private String site;
    private String rating;
    
    public Product(String name, String price, String url, String imageUrl, String site, String rating) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.imageUrl = imageUrl;
        this.site = site;
        this.rating = rating;
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public String getSite() { return site; }
    public void setSite(String site) { this.site = site; }
    
    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }
    
    @Override
    public String toString() {
        return String.format("Product{name='%s', price='%s', site='%s', rating='%s'}", 
                           name, price, site, rating);
    }
}