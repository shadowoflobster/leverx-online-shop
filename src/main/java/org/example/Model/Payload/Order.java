package org.example.Model.Payload;

import org.example.Model.Entity.Product;
import org.example.Producer.Customer;

public class Order {
    private final Product product;
    private final int quantity;
    private final Customer customer;

    public Order(Product product, int quantity, Customer customer) {
        this.product = product;
        this.quantity = quantity;
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return product.getPrice() * quantity;
    }

    public Customer getCustomer(){
        return customer;
    }
}
