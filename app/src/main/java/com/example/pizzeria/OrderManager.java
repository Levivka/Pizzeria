package com.example.pizzeria;

import java.util.HashMap;
import java.util.Map;

public class OrderManager {
    private static final OrderManager instance = new OrderManager();
    private final Map<String, Integer> orderItems = new HashMap<>();
    private final Map<String, Double> itemPrices = new HashMap<>();
    private double totalAmount = 0.0;

    private OrderManager() {}

    public static OrderManager getInstance() {
        return instance;
    }

    public double getItemPrice(String itemName, double defaultValue) {
        return itemPrices.getOrDefault(itemName, defaultValue);
    }

    public void addItem(String itemName, int quantity, double price) {
        orderItems.put(itemName, orderItems.getOrDefault(itemName, 0) + quantity);
        itemPrices.put(itemName, price);
        totalAmount += price * quantity;
    }

    public Map<String, Integer> getOrderItems() {
        return orderItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void clearOrder() {
        orderItems.clear();
        itemPrices.clear();
        totalAmount = 0.0;
    }
}

