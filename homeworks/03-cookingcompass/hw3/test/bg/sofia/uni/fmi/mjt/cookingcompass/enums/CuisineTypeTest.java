package bg.sofia.uni.fmi.mjt.cookingcompass.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CuisineTypeTest {
    @Test
    void testCuisineTypeOf() {
        assertEquals( CuisineType.cuisineTypeOf("french"), CuisineType.FRENCH);
    }

    @Test
    void testCuisineTypeOfNull() {
        assertThrows(IllegalArgumentException.class, () -> CuisineType.cuisineTypeOf(null));
    }

    @Test
    void testCuisineTypeOfUnknownElement() {
        assertThrows(IllegalArgumentException.class, () -> CuisineType.cuisineTypeOf("null"));
    }
}
