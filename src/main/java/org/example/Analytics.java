package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Analytics {
    private int orderCount;
    private double profit;
    private ConcurrentHashMap<Product, Integer> top3Products;
    private final List<Order> processedOrders = Collections.synchronizedList(new ArrayList<>());


    public List<Order> getProcessedOrders() {
        return processedOrders;
    }

    //when warehouse processes order successfully, it will be added to this list for later analytics
    public void addProcessedOrders(Order o){
        processedOrders.add(o);
    }




}
