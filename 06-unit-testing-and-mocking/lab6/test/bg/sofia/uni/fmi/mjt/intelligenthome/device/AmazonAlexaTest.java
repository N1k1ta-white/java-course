package bg.sofia.uni.fmi.mjt.intelligenthome.device;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AmazonAlexaTest {

    @Test
    void testGetId() {
        AmazonAlexa test = new AmazonAlexa("Alexa", 1.0, LocalDateTime.now());
        assertTrue(test.getId().matches("SPKR-Alexa-(\\d+)"));
    }


    @Test
    void testGetType() {
        AmazonAlexa test = new AmazonAlexa("Alexa", 1.0, LocalDateTime.now());
        assertEquals("SPKR", test.getType().getShortName());
    }

    @Test
    void testGetIdIdentity() {
        AmazonAlexa test = new AmazonAlexa("Alexa", 1.0, LocalDateTime.now());
        AmazonAlexa test2 = new AmazonAlexa("Alexa", 1.0, LocalDateTime.now());
        assertNotEquals(test.getId(), test2.getId());
    }
}
