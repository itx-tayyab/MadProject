package com.example.madproject.Modals;

public class modalmenurecyclerview {
    private String imageUrl;
    private String itemName;
    private String itemDescription;
    private Long itemPrice;
    private boolean isHeader;

    public modalmenurecyclerview(String imageUrl, String itemName, String itemDescription, Long itemPrice) {
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.isHeader = false;
    }

    public modalmenurecyclerview(String headerTitle) {
        this.itemName = headerTitle;
        this.isHeader = true;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public Long getItemPrice() {
        return itemPrice;
    }

    public boolean isHeader() {
        return isHeader;
    }
}
