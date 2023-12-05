package bg.sofia.uni.fmi.mjt.trading.price;

import bg.sofia.uni.fmi.mjt.trading.Rounding;
import bg.sofia.uni.fmi.mjt.trading.Size;
import bg.sofia.uni.fmi.mjt.trading.stock.Ticker;
public class PriceChart implements PriceChartAPI {
    static double[] StockPrice = new double[Size.STOCKCOUNT];
    public static int getPos(String stockTicker) {
        for (Ticker elem : Ticker.values()) {
            if (elem.toString().equals(stockTicker))
                return elem.ordinal();
        }
        return -1;
    }

    public PriceChart(double microsoftStockPrice, double googleStockPrice, double amazonStockPrice) {
        StockPrice[0] = microsoftStockPrice;
        StockPrice[1] = amazonStockPrice;
        StockPrice[2] = googleStockPrice;
    }

    @Override
    public double getCurrentPrice(String stockTicker) {
        int pos = getPos(stockTicker);
        if (pos == -1)
            return 0.0;
        return Rounding.rounding(StockPrice[pos]);
    }

    @Override
    public boolean changeStockPrice(String stockTicker, int percentChange) {
        int pos = getPos(stockTicker);
        if (pos == -1 || percentChange < 0)
            return false;
        StockPrice[pos] += (StockPrice[pos] / 100) * percentChange;
        return true;
    }
}
