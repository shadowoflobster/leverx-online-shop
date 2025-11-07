package org.example.Producer;

import org.example.Model.Entity.Customer;
import org.example.Model.Entity.Product;
import org.example.Model.Payload.Order;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerOrderTask implements Runnable {
    private final ConcurrentHashMap<Product, Integer> catalog;
    private final BlockingQueue<Order> orders;
    private final Customer customer;
    private final Random random = new Random();
    private static final int MAX_QUANTITY_PER_ITEM = 10;
    private static final int MIN_ORDERS_PER_RUN = 1;
    private static final int MAX_ORDERS_PER_RUN = 7;

    public CustomerOrderTask(ConcurrentHashMap<Product, Integer> catalog, BlockingQueue<Order> orders, Customer customer) {
        this.catalog = catalog;
        this.orders = orders;
        this.customer = customer;
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

                    //generated order has 30% chance of being reserve
                    boolean chanceOfBeingReserved = Math.random() < 0.3;

                    Order newOrder = new Order(selectedProduct, productQuantity, customer, chanceOfBeingReserved);

                    orders.put(newOrder);
                } else {
                    System.out.println("Product " + selectedProduct.getName() + " is out of");
                }


            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
