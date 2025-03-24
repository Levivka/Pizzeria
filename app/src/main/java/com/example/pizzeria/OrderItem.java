package com.example.pizzeria;

public class OrderItem {
    String itemName;
    int quantity;
    double price;
    OrderItem(String itemName, int quantity, double price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }
}

