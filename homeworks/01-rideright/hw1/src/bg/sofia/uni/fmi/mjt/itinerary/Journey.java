package bg.sofia.uni.fmi.mjt.itinerary;

import bg.sofia.uni.fmi.mjt.itinerary.vehicle.VehicleType;

import java.math.BigDecimal;

public record Journey(VehicleType vehicleType, City from, City to, BigDecimal price) {
    public Journey(VehicleType vehicleType, City from, City to, BigDecimal price) {
        this.vehicleType = vehicleType;
        this.from = from;
        this.to = to;
        this.price = price.add(price.multiply(vehicleType.getGreenTax()));
    }
}
