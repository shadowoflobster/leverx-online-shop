package org.example.Consumer.Service;

import org.example.Exceptions.InsufficientBalanceException;
import org.example.Model.Entity.Product;
import org.example.Model.Payload.Order;
import org.example.Reporting.Analytics;

import java.util.concurrent.ConcurrentHashMap;

public class Warehouse {
    private final ConcurrentHashMap<Product, Integer> stock = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Product,Integer> reservedItems = new ConcurrentHashMap<>();
    private final Analytics analytics;

    public Warehouse(Analytics analytics) {
        this.analytics = analytics;
    }

    public void addProduct(Product product, int quantity) {
        stock.put(product, quantity);
    }

    public ConcurrentHashMap<Product, Integer> getStock() {
        return stock;
    }

    public boolean reserve(Order order){
        Product product = order.getProduct();
        int orderQuantity = order.getQuantity();
        int stockQuantity = stock.getOrDefault(product, 0);

        //Checks if there is enough quantity in stock
        if (!(stockQuantity >= orderQuantity)) {
            return false;
        }

        stock.computeIfPresent(product, (key, quantity) -> quantity - orderQuantity);

        reservedItems.merge(product,orderQuantity, Integer::sum);

        return true;
    }

    public boolean processOrder(Order order) {
        Product product = order.getProduct();
        int orderQuantity = order.getQuantity();
        int stockQuantity = stock.getOrDefault(product, 0);

        //Checks if there is enough quantity in stock
        if (!(stockQuantity >= orderQuantity)) {
            return false;
        }

        //Debit customer balance
        try {
            order.getCustomer().debitBalance(order.getPrice());
        }catch (InsufficientBalanceException e){
            System.out.println(e);
            return false;
        }

        //If TRUE, then subtracts order quantity from stock quantity
        stock.computeIfPresent(product, (key, quantity) -> quantity - orderQuantity);
        analytics.addProcessedOrders(order);
        return true;

    }

}
