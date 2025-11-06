package org.example.Model.Entity;


import org.example.Exceptions.InsufficientBalanceException;
public class Customer {
    private final String name;
    private double balance;

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    //Method for subtracting money from balance
    public synchronized void debitBalance(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
        } else {
            throw new InsufficientBalanceException("Customer has insufficient balance to cover price:" + amount);
        }
    }

    public double getBalance() {
        return balance;
    }


}
