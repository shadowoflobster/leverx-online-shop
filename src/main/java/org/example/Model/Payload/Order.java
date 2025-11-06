package org.example.Model.Payload;

import org.example.Model.Entity.Customer;
import org.example.Model.Entity.Product;

public class Order {
    private final Product product;
    private final int quantity;
    private final Customer customer;
    private boolean isReserved;
    private final boolean poisonPill;

    public Order(Product product, int quantity, Customer customer) {
        this.product = product;
        this.quantity = quantity;
        this.customer = customer;
        this.poisonPill = false;
    }

    public Order() {
        this.product = null;
        this.quantity = 0;
        this.customer = null;
        this.poisonPill = true;
    }

    //Static method to create poison pill
    public static final Order POISON_PILL = new Order();

    //Boolean method to check if this object is poison pill
    public boolean isPoisonPill() {
        return poisonPill;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        assert product != null;
        return product.getPrice() * quantity;
    }

    public Customer getCustomer() {
        return customer;
    }
}
