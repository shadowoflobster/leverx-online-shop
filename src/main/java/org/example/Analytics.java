package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Analytics {
    private int orderCount;
    private double profit;
    private final List<Order> processedOrders = Collections.synchronizedList(new ArrayList<>());
    private List<Map.Entry<Product, Integer>> top3Sales = Collections.emptyList();


    public List<Order> getProcessedOrders() {
        return processedOrders;
    }

    //when warehouse processes order successfully, it will be added to this list for later analytics
    public void addProcessedOrders(Order o) {
        processedOrders.add(o);
    }

    public void run() {
        analyse();
        print();
    }

    public void analyse() {
        orderCount = Math.toIntExact(processedOrders.parallelStream().count());

        profit = processedOrders.parallelStream().mapToDouble(Order::getPrice).sum();

        //Round profit up to 2 decimals
        profit = Math.floor(profit * 100) / 100;

        Map<Product, Integer> productSales = processedOrders.parallelStream()
                .collect(Collectors.toConcurrentMap(
                        Order::getProduct,
                        Order::getQuantity,
                        Integer::sum
                ));

        top3Sales = productSales.entrySet().stream()
                .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
                .limit(3)
                .toList();
    }

    public void print() {
        System.out.println("Total number of orders: " + orderCount);
        System.out.println("Total profit: " + profit +"$");
        System.out.println("Top 3 product: ");

        top3Sales.forEach(m -> System.out.println("Product:" + m.getKey() + ", Sold: " + m.getValue()));
    }
}


