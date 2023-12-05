package bg.sofia.uni.fmi.mjt.trading.stock;

import bg.sofia.uni.fmi.mjt.trading.Rounding;
import java.time.LocalDateTime;

public abstract class Purchase implements StockPurchase {
    int quantity;
    LocalDateTime time;
    double pricePerUnit;
     public Purchase(int quantity, LocalDateTime purchaseTimestamp, double purchasePricePerUnit) {
         this.quantity = quantity;
         time = purchaseTimestamp;
         pricePerUnit = purchasePricePerUnit;
     }
    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public LocalDateTime getPurchaseTimestamp() {
        return time;
    }

    @Override
    public double getPurchasePricePerUnit() {
        return Rounding.rounding(pricePerUnit);
    }

    @Override
    public double getTotalPurchasePrice() {
        return Rounding.rounding(pricePerUnit * quantity);
    }
}
