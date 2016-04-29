package com.ajax;

public class Product {

    private String productId;
    private String productName;
    private String productPrice;
    private String productType;
    
    
    public Product (String productId, String productName, String productPrice, String productType) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public String getProductPrice() {
        return productPrice;
    }
}