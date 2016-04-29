package com.ajax;

public class Product {

    private String itemId;
    private String itemName;
    private String itemPrice;
    private String itemType;
    
    
    public Product (String itemId, String itemName, String itemPrice, String itemType) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }
    
    public String getItemId() {
        return itemId;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public String getItemPrice() {
        return itemPrice;
    }
}