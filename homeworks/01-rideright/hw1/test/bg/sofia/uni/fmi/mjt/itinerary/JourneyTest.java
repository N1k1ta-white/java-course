package bg.sofia.uni.fmi.mjt.itinerary;

import bg.sofia.uni.fmi.mjt.itinerary.vehicle.VehicleType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JourneyTest {
    @Test
    void testConstructor() {
        Journey test = new Journey(VehicleType.PLANE, new City("Test1", new Location(0, 0)),
                new City("Test2", new Location(10, 10)), BigDecimal.valueOf(1000));
        assertEquals(1250, test.price().intValue());
    }
}
