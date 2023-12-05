import bg.sofia.uni.fmi.mjt.trading.Portfolio;
import bg.sofia.uni.fmi.mjt.trading.Rounding;
import bg.sofia.uni.fmi.mjt.trading.price.PriceChart;

public class Main {
    public static void main(String[] args) {
        PriceChart priceChart = new PriceChart(100.0, 100.0, 100.0);
        Portfolio portfolio = new Portfolio("Nik", priceChart, 1000, 10);
        if (portfolio.buyStock("GOOG", 10) == null)
            System.out.println("ERROR");
        System.out.println(priceChart.getCurrentPrice("GOOG"));
    }
}