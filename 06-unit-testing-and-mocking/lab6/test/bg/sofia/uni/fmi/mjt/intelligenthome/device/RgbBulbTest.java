package bg.sofia.uni.fmi.mjt.intelligenthome.device;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RgbBulbTest {

    @Test
    void testGetId() {
        RgbBulb test = new RgbBulb("Bulb", 1.0, LocalDateTime.now());
        assertTrue(test.getId().matches("BLB-Bulb-(\\d+)"));
    }

    @Test
    void testGetType() {
        RgbBulb test = new RgbBulb("Bulb", 1.0, LocalDateTime.now());
        assertEquals("BLB", test.getType().getShortName());
    }

    @Test
    void testGetIdIdentity() {
        RgbBulb test = new RgbBulb("Bulb", 1.0, LocalDateTime.now());
        RgbBulb test2 = new RgbBulb("Bulb", 1.0, LocalDateTime.now());
        assertNotEquals(test2.getId(), test.getId());
    }
}
