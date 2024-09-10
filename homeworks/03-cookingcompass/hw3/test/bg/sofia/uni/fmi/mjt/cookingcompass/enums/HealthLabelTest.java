package bg.sofia.uni.fmi.mjt.cookingcompass.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HealthLabelTest {
    @Test
    void testHealthLabelOf() {
        assertEquals(HealthLabel.healthLabelOf("Paleo"), HealthLabel.PALEO);
    }

    @Test
    void testHealthLabelOfNull() {
        assertThrows(IllegalArgumentException.class, () -> HealthLabel.healthLabelOf(null));
    }

    @Test
    void testHealthLabelOfUnknownElement() {
        assertThrows(IllegalArgumentException.class, () -> HealthLabel.healthLabelOf("null"));
    }
}
