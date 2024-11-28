package main;

import model.Stock;
import model.User;
import simulation.StockSimulator;
import java.io.*;
import java.util.*;

public class StockMarket {
    private Map<String, Stock> stocks;
    private User currentUser;

    public StockMarket(User user) {
        this.currentUser = user;
        stocks = new HashMap<>();
        initializeStocks();
    }

    private void initializeStocks() {
        stocks.put("AAPL", new Stock("Apple", 150.00));
        stocks.put("GOOGL", new Stock("Google", 2800.00));
        stocks.put("AMZN", new Stock("Amazon", 3300.00));
        stocks.put("TSLA", new Stock("Tesla", 700.00));

        for (Stock stock : stocks.values()) {
            new Thread(new StockSimulator(stock)).start();
        }
    }

    public void viewStockDetails() {
        System.out.println("Available Stocks:");
        for (Stock stock : stocks.values()) {
            System.out.println(stock);
        }
    }

    public void buyStock(String stockName, int quantity) {
        stockName = stockName.toUpperCase(); // Normalize input to uppercase
        Stock stock = stocks.get(stockName);
        
        if (stock != null) {
            double totalPrice = stock.getCurrentPrice() * quantity;

            if (currentUser.getBalance() >= totalPrice) {
                currentUser.deductBalance(totalPrice);
                currentUser.addStock(stockName, quantity);  

                System.out.println("Purchased " + quantity + " shares of " + stock.getName() + " at " + stock.getCurrentPrice() + " each.");
                logTransaction("BUY", stock.getName(), stock.getCurrentPrice(), quantity);
                System.out.println("New balance: $" + currentUser.getBalance());
            } else {
                System.out.println("Insufficient balance to complete the transaction.");
            }
        } else {
            System.out.println("Stock not found.");
        }
    }


    public void sellStock(String stockName, int quantity) {
        stockName = stockName.toUpperCase(); 
        Stock stock = stocks.get(stockName);
        
        if (stock != null) {

            if (currentUser.getPortfolio().containsKey(stockName)) {
                int ownedQuantity = currentUser.getPortfolio().get(stockName);
                
                if (ownedQuantity >= quantity) {

                    double totalPrice = stock.getCurrentPrice() * quantity;

                    currentUser.removeStock(stockName, quantity);
                    currentUser.addBalance(totalPrice);

                    System.out.println("Sold " + quantity + " shares of " + stock.getName() + " at " + stock.getCurrentPrice() + " each.");
                    logTransaction("SELL", stock.getName(), stock.getCurrentPrice(), quantity);
                    System.out.println("New balance: $" + currentUser.getBalance());
                } else {
                    System.out.println("Not enough stock to sell.");
                }
            } else {
                System.out.println("You don't own any " + stockName + " shares.");
            }
        } else {
            System.out.println("Stock not found.");
        }
    }


    private void logTransaction(String type, String stockName, double price, int quantity) {
        try (FileWriter writer = new FileWriter("transactions.txt", true)) {
            writer.write(type + ": " + quantity + " shares of " + stockName + " at $" + price + "\n");
        } catch (IOException e) {
            System.err.println("Error logging transaction: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        User user = new User("John Doe", 50000.00);

        StockMarket stockMarket = new StockMarket(user);

        while (true) {
            System.out.println("\n1. View Stock Details");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    stockMarket.viewStockDetails();
                    break;
                case 2:
                    System.out.print("Enter stock name: ");
                    String stockNameBuy = scanner.next();
                    System.out.print("Enter quantity: ");
                    int quantityBuy = scanner.nextInt();
                    stockMarket.buyStock(stockNameBuy, quantityBuy);
                    break;
                case 3:
                    System.out.print("Enter stock name: ");
                    String stockNameSell = scanner.next();
                    System.out.print("Enter quantity: ");
                    int quantitySell = scanner.nextInt();
                    stockMarket.sellStock(stockNameSell, quantitySell);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
