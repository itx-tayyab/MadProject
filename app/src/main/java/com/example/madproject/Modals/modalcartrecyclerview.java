package com.example.madproject.Modals;
public class modalcartrecyclerview {
    private String image_url; // To store image URL
    private String itemName;  // To store item name
    private String itemDescription; // To store item description
    private double itemPrice; // To store item price
    private int quantity; // To store quantity

    public modalcartrecyclerview() {

    }
    public modalcartrecyclerview(String image_url, String itemName, String itemDescription, double itemPrice, int quantity) {
        this.image_url = image_url;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    // Getters and setters
    public String getImageUrl() {
        return image_url;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
