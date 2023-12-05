package bg.sofia.uni.fmi.mjt.intelligenthome.device;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WiFiThermostatTest {
    @Test
    void testGetId() {
        WiFiThermostat test = new WiFiThermostat("WiFi", 1.0, LocalDateTime.now());
        assertTrue(test.getId().matches("TMST-WiFi-(\\d+)"));
    }

    @Test
    void testGetType() {
        WiFiThermostat test = new WiFiThermostat("WiFi", 1.0, LocalDateTime.now());
        assertEquals("TMST", test.getType().getShortName());
    }

    @Test
    void testGetIdIdentity() {
        WiFiThermostat test = new WiFiThermostat("WiFi", 1.0, LocalDateTime.now());
        WiFiThermostat test2 = new WiFiThermostat("WiFi", 1.0, LocalDateTime.now());
        assertNotEquals(test2.getId(), test.getId());
    }
}
