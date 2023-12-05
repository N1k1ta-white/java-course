package bg.sofia.uni.fmi.mjt.intelligenthome.device;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IoTDeviceBaseTest {

    @Test
    void testGetPowerConsumptionKWhNow() {
        IoTDeviceBase device = new AmazonAlexa("Alexa", 5.0, LocalDateTime.now());
        assertEquals(0.0, device.getPowerConsumptionKWh(), "You hadn't time for consumption!");
    }

    @Test
    void testGetPowerConsumptionKWhHourAgo() {
        IoTDeviceBase device = new AmazonAlexa("Alexa", 5.0, LocalDateTime.now().minusHours(1));
        assertEquals(5.0, device.getPowerConsumptionKWh(), "You hadn't time for consumption!");
    }

    @Test
    void testGetPowerConsumptionKWhHourDay() {
        IoTDeviceBase device = new AmazonAlexa("Alexa", 5.0, LocalDateTime.now().minusDays(1));
        assertEquals(120.0, device.getPowerConsumptionKWh(), "You hadn't time for consumption!");
    }
}
