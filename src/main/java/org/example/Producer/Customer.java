package org.example.Producer;


import org.example.Exceptions.InsufficientBalanceException;
import org.example.Model.Entity.Product;
import org.example.Model.Payload.Order;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Customer implements Runnable {
    private final String name;
    private double balance;
    private final BlockingQueue<Order> orders;
    private final ConcurrentHashMap<Product, Integer> catalog;
    private final Random random = new Random();
    private static final int MAX_QUANTITY_PER_ITEM = 10;
    private static final int MIN_ORDERS_PER_RUN = 1;
    private static final int MAX_ORDERS_PER_RUN = 7;


    public Customer(String name, double balance, BlockingQueue<Order> orders, ConcurrentHashMap<Product, Integer> catalog) {
        this.name = name;
        this.balance = balance;
        this.orders = orders;
        this.catalog = catalog;
    }


    @Override
    public void run() {
        try {
            int orderNumber = random.nextInt(MIN_ORDERS_PER_RUN, MAX_ORDERS_PER_RUN);
            for (int i = 0; i < orderNumber; i++) {
                //Create a list using catalog keys(products)
                List<Product> products = catalog.keySet().stream().toList();

                if (products.isEmpty()) {
                    System.out.println("Catalog is empty");
                    break;
                }
                //Randomly selecting product by index
                int randomProductIndex = random.nextInt(products.size());
                Product selectedProduct = products.get(randomProductIndex);
                //getting quantity in stock for selected product
                Integer inStock = catalog.get(selectedProduct);
                if (inStock > 0) {
                    //product quantity is random between 1-10
                    int productQuantity = random.nextInt(1, Math.min(inStock, MAX_QUANTITY_PER_ITEM) + 1);

                    Order newOrder = new Order(selectedProduct, productQuantity, this);

                    orders.put(newOrder);
                } else {
                    System.out.println("Product " + selectedProduct.getName() + " is out of");
                }


            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    //Method for subtracting money from balance
    public synchronized void debitBalance(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
        } else {
            throw new InsufficientBalanceException("Customer has insufficient balance to cover price:" + amount);
        }
    }

    public double getBalance() {
        return balance;
    }


}
