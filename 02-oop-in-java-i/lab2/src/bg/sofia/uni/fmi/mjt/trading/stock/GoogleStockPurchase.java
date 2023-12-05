package bg.sofia.uni.fmi.mjt.trading.stock;

import java.time.LocalDateTime;

public class GoogleStockPurchase extends Purchase {
    public GoogleStockPurchase(int quantity, LocalDateTime purchaseTimestamp, double purchasePricePerUnit) {
        super(quantity, purchaseTimestamp, purchasePricePerUnit);
    }

    @Override
    public String getStockTicker() {
        return Ticker.GOOG.toString();
    }
}
