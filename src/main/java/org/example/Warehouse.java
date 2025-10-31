package org.example;

import java.util.concurrent.ConcurrentHashMap;

public class Warehouse {
    private ConcurrentHashMap<Product,Integer> stock= new ConcurrentHashMap<>();

    public Warehouse(){}
}
