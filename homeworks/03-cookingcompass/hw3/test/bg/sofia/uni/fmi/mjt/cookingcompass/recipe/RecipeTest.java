package bg.sofia.uni.fmi.mjt.cookingcompass.recipe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecipeTest {
    @Test
    void recipeWeightTest() {
        Recipe recipe = new Recipe(null, null, null, null, "100.323143",
                "34.3242342", null, null, null);
        assertEquals(recipe.weight(), "100.32");
    }

    @Test
    void recipeCaloriesTest() {
        Recipe recipe = new Recipe(null, null, null, null, "100.323143",
                "34.3242342", null, null, null);
        assertEquals(recipe.calories(), "34");
    }
}
