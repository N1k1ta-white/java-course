package bg.sofia.uni.fmi.mjt.trading;

import bg.sofia.uni.fmi.mjt.trading.price.PriceChartAPI;
import bg.sofia.uni.fmi.mjt.trading.stock.*;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Portfolio implements PortfolioAPI {
    static final int percentage = 5;
    String owner;
    PriceChartAPI priceChart;
    int quantity;
    StockPurchase[] purchases;
    double budget;
    int maxSize;

    public Portfolio(String owner, PriceChartAPI priceChart, double budget, int maxSize) {
        this.owner = owner;
        this.priceChart = priceChart;
        this.budget = budget;
        this.maxSize = maxSize;
        purchases = null;
        quantity = 0;
    }
    public Portfolio(String owner, PriceChartAPI priceChart, StockPurchase[] stockPurchases, double budget, int maxSize) {
        this.owner = owner;
        this.priceChart = priceChart;
        this.budget = budget;
        this.maxSize = maxSize;
        purchases = stockPurchases;
        for (StockPurchase purchase: purchases)
            quantity += purchase.getQuantity();
    }

    @Override
    public StockPurchase buyStock(String stockTicker, int quantity) {
        if (quantity < 0 || stockTicker == null || quantity + this.quantity > maxSize)
            return null;
        StockPurchase res = switch (stockTicker) {
            case "MSFT" -> new MicrosoftStockPurchase(quantity, LocalDateTime.now(), priceChart.getCurrentPrice(stockTicker));
            case "GOOG" -> new GoogleStockPurchase(quantity, LocalDateTime.now(), priceChart.getCurrentPrice(stockTicker));
            case "AMZ" -> new AmazonStockPurchase(quantity, LocalDateTime.now(), priceChart.getCurrentPrice(stockTicker));
            default -> null;
        };
        if (res == null)
            return null;
        double bill = res.getTotalPurchasePrice();
        if (bill > budget)
            return null;
        budget -= bill;
        priceChart.changeStockPrice(stockTicker, percentage);
        int size = (purchases != null ? purchases.length : 0) + 1;
        StockPurchase[] nPurchases = new StockPurchase[size];
        for (int i = 0; i < size - 1; i++)
            nPurchases[i] = purchases[i];
        nPurchases[size - 1] = res;
        purchases = nPurchases;
        this.quantity += res.getQuantity();
        return res;
    }

    @Override
    public StockPurchase[] getAllPurchases() {
        return purchases;
    }

    @Override
    public StockPurchase[] getAllPurchases(LocalDateTime startTimestamp, LocalDateTime endTimestamp) {
        int size = 0, iter = 0;
        for (StockPurchase purchase : purchases)
            if (purchase.getPurchaseTimestamp().isAfter(startTimestamp) && purchase.getPurchaseTimestamp().isBefore(endTimestamp))
                size++;
        StockPurchase[] res = new StockPurchase[size];
        for (StockPurchase purchase : purchases)
            if (purchase.getPurchaseTimestamp().isAfter(startTimestamp) && purchase.getPurchaseTimestamp().isBefore(endTimestamp))
                res[iter++] = purchase;
        return res;
    }

    @Override
    public double getNetWorth() {
        double netWorth = 0;
        for (StockPurchase purchase : purchases)
            netWorth += purchase.getTotalPurchasePrice();
        return Rounding.rounding(netWorth);
    }

    @Override
    public double getRemainingBudget() {
        return Rounding.rounding(budget);
    }

    @Override
    public String getOwner() {
        return owner;
    }
}
