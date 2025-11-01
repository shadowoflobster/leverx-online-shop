package org.example;

import java.util.concurrent.BlockingQueue;

public class Worker implements Runnable {
    private final String name;
    private BlockingQueue<Order> orders;
    private final Warehouse warehouse;


    public Worker(String name, BlockingQueue<Order> orders, Warehouse warehouse) {
        this.name = name;
        this.orders = orders;
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        try {

            while (true) {
                Order order = orders.take();
                if (order.getProduct() == null) {
                    break;
                }

                boolean process = warehouse.processOrder(order);

                if (process) {
                    System.out.println("Worker " + name + " successfully processed order for "
                            + order.getProduct().getName()
                            + " " + order.getQuantity()
                            + "x");
                }else {
                    System.out.println("Worker " + name + " failed to process order for "
                            + order.getProduct().getName()
                            + " " + order.getQuantity()
                            + "x, No items in stock");
                }

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
