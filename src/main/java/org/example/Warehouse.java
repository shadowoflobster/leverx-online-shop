package org.example;

import java.util.concurrent.ConcurrentHashMap;

public class Warehouse {
    private final ConcurrentHashMap<Product, Integer> stock = new ConcurrentHashMap<>();
    private final Analytics analytics;

    public Warehouse(Analytics analytics) {
        this.analytics = analytics;
    }

    public void addProduct(Product product, int quantity) {
        stock.put(product, quantity);
    }

    public ConcurrentHashMap<Product,Integer> getStock(){
        return stock;
    }

    public boolean processOrder(Order order) {
        Product product = order.getProduct();
        int orderQuantity = order.getQuantity();
        int stockQuantity = stock.getOrDefault(product, 0);

        //Checks if there is enough quantity in stock to process order
        if (stockQuantity >= orderQuantity) {
            //If TRUE, then subtracts order quantity from stock quantity
            stock.computeIfPresent(product, (key, quantity) -> quantity - orderQuantity);
            analytics.addProcessedOrders(order);
            return true;
        }

        return false;

    }


}
