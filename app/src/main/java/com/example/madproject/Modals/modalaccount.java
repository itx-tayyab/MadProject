package com.example.madproject.Modals;

public class modalaccount {
    private String name;
    private String phone;
    private String address;
    private String username; // This is the email

    public modalaccount() {
        // Required for Firebase
    }

    public modalaccount(String name, String phone, String address, String username) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.username = username;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}

