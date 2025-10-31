package org.example;

public class Order {
    private final Product product;
    private final int quantity;

    public Order(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
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
}
