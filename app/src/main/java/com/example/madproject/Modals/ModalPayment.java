package com.example.madproject.Modals;

public class ModalPayment {
    private String name;
    private String username;
    private String phone;
    private String address;
    private Long totalAmount;

    public ModalPayment() {
        // Default constructor required for Firebase
    }

    public ModalPayment(String name, String username, String phone, String address, Long totalAmount) {
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.address = address;
        this.totalAmount = totalAmount;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }
}
