package bg.sofia.uni.fmi.mjt.cookingcompass.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MealTypeTest {
    @Test
    void testMealTypeOf() {
        assertEquals(MealType.mealTypeOf("snack"), MealType.SNACK);
    }

    @Test
    void testMealTypeOfNull() {
        assertThrows(IllegalArgumentException.class, () -> MealType.mealTypeOf(null));
    }

    @Test
    void testMealTypeOfUnknownElement() {
        assertThrows(IllegalArgumentException.class, () -> MealType.mealTypeOf("null"));
    }
}
