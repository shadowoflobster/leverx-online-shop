package org.example;


import java.util.Random;
import java.util.concurrent.*;

public class Customer implements Runnable {
    private final String name;
    private double balance;
    private BlockingQueue<Order> orders;
    private ConcurrentHashMap<Product, Integer> catalog;
    private final Random random = new Random();


    public Customer(String name, double balance, BlockingQueue<Order> orders, ConcurrentHashMap<Product, Integer> catalog) {
        this.name = name;
        this.balance = balance;
        this.orders = orders;
        this.catalog = catalog;
    }



    @Override
    public void run() {
        
    }


}
