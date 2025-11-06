package org.example;

import org.example.Consumer.Service.Warehouse;
import org.example.Consumer.Worker.Worker;
import org.example.Model.Entity.Customer;
import org.example.Model.Entity.Product;
import org.example.Model.Payload.Order;
import org.example.Producer.CustomerOrderTask;
import org.example.Reporting.Analytics;

import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Analytics analytics = new Analytics();
        Warehouse warehouse = new Warehouse(analytics);
        BlockingQueue<Order> orders = new LinkedBlockingQueue<>();
        Random random = new Random();

        int customerBalance = 50;
        int customerCount = 10;
        int workerCount = 5;
        int fixedProductQuantity = 20;
        double MAX_PRODUCT_PRICE = 20.0;

        String[] productNames =
                {"Carrot", "Potato", "Apple", "Bread", "Milk", "Egg", "Onion", "Lemon", "Banana", "Avocado"};

        for (int i = 1; i < 10; i++) {
            warehouse.addProduct(new Product(productNames[i],
                            random.nextDouble(1, MAX_PRODUCT_PRICE)), /*Giving items random price*/
                    fixedProductQuantity);
        }

        //Starting threadpool for customers and workers
        ExecutorService customerExecutor = Executors.newFixedThreadPool(customerCount);
        ExecutorService workerExecutor = Executors.newFixedThreadPool(workerCount);

        //Create customer threads
        for (int i = 1; i <= customerCount; i++) {
            Customer customer = new Customer("Customer n" + i, customerBalance);
            customerExecutor.execute(new CustomerOrderTask(warehouse.getStock(), orders, customer));
        }

        //Create worker threads
        for (int i = 1; i <= workerCount; i++) {
            workerExecutor.execute(new Worker("Worker n" + i, orders, warehouse));
        }

        //Shutting down customer executor to prevent customer/order generation
        customerExecutor.shutdown();
        //Wait all customers to submit their tasks (or terminate automatically in 10 seconds)
        customerExecutor.awaitTermination(10, TimeUnit.SECONDS);

        //Add poison pills to orders queue for each worker
        for (int i = 0; i < workerCount; i++) {
            orders.put(Order.POISON_PILL);
        }

        //Stop accepting new tasks from the order processor
        workerExecutor.shutdown();
        //Wait all worker to finish threads (or terminate automatically in 10 seconds)
        workerExecutor.awaitTermination(10, TimeUnit.SECONDS);

        //Run analytics
        analytics.run();


    }
}