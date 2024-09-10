package bg.sofia.uni.fmi.mjt.cookingcompass.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DietLabelTest {
    @Test
    void testDietLabelOf() {
        assertEquals(DietLabel.dietLabelOf("Low-Fat"), DietLabel.LOW_FAT);
    }

    @Test
    void testDietLabelOfNull() {
        assertThrows(IllegalArgumentException.class, () -> DietLabel.dietLabelOf(null));
    }

    @Test
    void testDietLabelOfUnknownElement() {
        assertThrows(IllegalArgumentException.class, () -> DietLabel.dietLabelOf("null"));
    }
}
