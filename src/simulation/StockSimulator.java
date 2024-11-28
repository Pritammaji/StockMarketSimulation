package simulation;

import model.Stock;
import java.util.Random;

public class StockSimulator implements Runnable {
    private Stock stock;
    private Random random;

    public StockSimulator(Stock stock) {
        this.stock = stock;
        this.random = new Random();
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000); 

                
                double percentageChange = (random.nextDouble() * 10) - 5;
                double newPrice = stock.getCurrentPrice() * (1 + percentageChange / 100);

                stock.updatePrice(Math.round(newPrice * 100.0) / 100.0);

                System.out.println(stock.getName() + " new price: " + stock.getCurrentPrice());
            } catch (InterruptedException e) {
                System.out.println("Error in stock simulation: " + e.getMessage());
            }
        }
    }
}
