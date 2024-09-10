package bg.sofia.uni.fmi.mjt.cookingcompass.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DishTypeTest {
    @Test
    void testDishTypeOf() {
        assertEquals(DishType.dishTypeOf("soup"), DishType.SOUP);
    }

    @Test
    void testDishTypeOfNull() {
        assertThrows(IllegalArgumentException.class, () -> DishType.dishTypeOf(null));
    }

    @Test
    void testDishTypeOfUnknownElement() {
        assertThrows(IllegalArgumentException.class, () -> DishType.dishTypeOf("null"));
    }
}
