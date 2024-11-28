package model;

import java.util.ArrayList;
import java.util.List;

public class Stock {
    private String name;
    private double currentPrice;
    private List<Double> priceHistory;

    public Stock(String name, double initialPrice) {
        this.name = name;
        this.currentPrice = initialPrice;
        this.priceHistory = new ArrayList<>();
        priceHistory.add(initialPrice);
    }

    public synchronized double getCurrentPrice() {
        return currentPrice;
    }

    public synchronized void updatePrice(double newPrice) {
        currentPrice = newPrice;
        priceHistory.add(newPrice);
    }

    public String getName() {
        return name;
    }

    public synchronized List<Double> getPriceHistory() {
        return new ArrayList<>(priceHistory); 
    }

    public String toString() {
        return "Stock{name='" + name + "', currentPrice=" + currentPrice + "}";
    }
}
