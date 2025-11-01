package org.example;

import java.util.concurrent.ConcurrentHashMap;

public class Warehouse {
    private ConcurrentHashMap<Product, Integer> stock = new ConcurrentHashMap<>();

    public Warehouse() {
    }

    public void addProduct(Product product, int quantity) {
        stock.put(product, quantity);
    }

    public boolean processOrder(Order order) {
        Product product = order.getProduct();
        int orderQuantity = order.getQuantity();
        int stockQuantity = stock.getOrDefault(product, 0);

        //Checks if there is enough quantity in stock to process order
        if (stockQuantity >= orderQuantity) {
            //If TRUE, then subtracts order quantity from stock quantity
            stock.computeIfPresent(product, (key, quantity) -> {
                return quantity - orderQuantity;
            });
            return true;
        }

        return false;

    }


}
