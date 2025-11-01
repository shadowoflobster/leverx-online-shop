package org.example;

import java.util.concurrent.BlockingQueue;

public class Worker implements Runnable{
    private final String name;
    private BlockingQueue<Order> orders;


    public Worker(String name) {
        this.name = name;
    }

    @Override
    public void run(){}
}
