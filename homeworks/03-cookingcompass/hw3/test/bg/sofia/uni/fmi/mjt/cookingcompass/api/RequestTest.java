package bg.sofia.uni.fmi.mjt.cookingcompass.api;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RequestTest {
    @Test
    void testRequestDefaultValue() throws URISyntaxException {
        assertEquals(Request.builder().build().toString(),
                "https://api.edamam.com/api/recipes/v2?type=public&app_id=c724210f&app_key=b3e59dc38218554dedb406a982623b79 GET");
    }

    @Test
    void testAddSettings() throws URISyntaxException {
        assertEquals(Request.builder().addSettings("test", "valTest").build().toString(),
                "https://api.edamam.com/api/recipes/v2?type=public&app_id=c724210f&app_key=b3e59dc38218554dedb406a982623b79&test=valTest GET");
        assertEquals(Request.builder().addSettings("test", "valTest1 valTest2").build().toString(),
                "https://api.edamam.com/api/recipes/v2?type=public&app_id=c724210f&app_key=b3e59dc38218554dedb406a982623b79&test=valTest1+valTest2 GET");
    }

    @Test
    void testAddSettingsNull() {
        assertThrows(IllegalArgumentException.class, () -> Request.builder().addSettings(null, "test").build());
        assertThrows(IllegalArgumentException.class, () -> Request.builder().addSettings("test", null).build());
    }

    @Test
    void testAddSettingsBlank() {
        assertThrows(IllegalArgumentException.class, () -> Request.builder().addSettings("", "test").build());
        assertThrows(IllegalArgumentException.class, () -> Request.builder().addSettings("test", "").build());
    }
}
