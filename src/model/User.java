package model;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private double balance; 
    private Map<String, Integer> portfolio; 
    public User(String name, double initialBalance) {
        this.name = name;
        this.balance = initialBalance;
        this.portfolio = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public Map<String, Integer> getPortfolio() {
        return portfolio;
    }

    public void addStock(String stockSymbol, int quantity) {
        portfolio.put(stockSymbol, portfolio.getOrDefault(stockSymbol, 0) + quantity);
    }

    public void removeStock(String stockSymbol, int quantity) {
        if (portfolio.containsKey(stockSymbol)) {
            int currentQuantity = portfolio.get(stockSymbol);
            if (currentQuantity >= quantity) {
                portfolio.put(stockSymbol, currentQuantity - quantity);
            } else {
                System.out.println("Not enough stock to sell.");
            }
        } else {
            System.out.println("You don't own this stock.");
        }
    }

    public void deductBalance(double amount) {
        balance -= amount;
    }

    public void addBalance(double amount) {
        balance += amount;
    }
}
