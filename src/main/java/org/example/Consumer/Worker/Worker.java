package org.example.Consumer.Worker;

import org.example.Consumer.Service.Warehouse;
import org.example.Model.Payload.Order;

import java.util.concurrent.BlockingQueue;

public class Worker implements Runnable {
    private final String name;
    private final BlockingQueue<Order> orders;
    private final Warehouse warehouse;


    public Worker(String name, BlockingQueue<Order> orders, Warehouse warehouse) {
        this.name = name;
        this.orders = orders;
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        boolean isRunning = true;
        try {

            while (isRunning) {
                Order order = orders.take();
                //Poison pill
                if (order.isPoisonPill()) {
                    isRunning = false;
                    continue;
                }

                boolean success;
                String operation;


                if (order.isReserved()) {
                    success = warehouse.reserve(order);
                    operation = "Reserved";
                } else {
                    success = warehouse.processOrder(order);
                    operation = "processed";

                }


                System.out.printf("Worker %s %s to %s order for %s x%d%n",
                        name,
                        success ? "successfully" : "failed",
                        operation,
                        order.getProduct().getName(),
                        order.getQuantity());

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
