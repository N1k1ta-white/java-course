package bg.sofia.uni.fmi.mjt.trading.stock;

import java.time.LocalDateTime;

public class MicrosoftStockPurchase extends Purchase {
    public MicrosoftStockPurchase(int quantity, LocalDateTime purchaseTimestamp, double purchasePricePerUnit) {
        super(quantity, purchaseTimestamp, purchasePricePerUnit);
    }

    @Override
    public String getStockTicker() {
        return Ticker.MSFT.toString();
    }
}