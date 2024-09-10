package bg.sofia.uni.fmi.mjt.cookingcompass.recipe;

import bg.sofia.uni.fmi.mjt.cookingcompass.api.JsonConverter;
import bg.sofia.uni.fmi.mjt.cookingcompass.api.Response;
import bg.sofia.uni.fmi.mjt.cookingcompass.api.Service;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.CuisineType;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.DietLabel;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.DishType;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.HealthLabel;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.MealType;
import bg.sofia.uni.fmi.mjt.cookingcompass.exceptions.NoRecipesException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageOfRecipesTest {

    static Response response1;
    static Response response2;

    @BeforeAll
    static void init() {
        response1 = new Response(JsonConverter.convertJson(Service.getJson()).receipts(), "None");
        response2 = new Response(JsonConverter.convertJson(Service.getJson2()).receipts(), "test");
    }

    @Test
    void testGetPageFromIndex() throws URISyntaxException, IOException, InterruptedException {
        Storage<Recipe> storage = new StorageOfRecipes(response1, 6);
        assertTrue(storage.getPageFromIndex(0).size() == 6);
        assertTrue(storage.getPageFromIndex(18).size() == 2);
    }

    @Test
    void testGetPageFromIndexBound() throws URISyntaxException, IOException, InterruptedException {
        Storage<Recipe> storage = new StorageOfRecipes(response1, 6);
        assertTrue(storage.getPageFromIndex(18).size() == 2);
    }

    @Test
    void tesGetPageFromIndexNoRecipe() {
        Storage<Recipe> storage = new StorageOfRecipes(response1, 6);
        assertThrows(NoRecipesException.class,  () -> storage.getPageFromIndex(21));
    }

    @Test
    void tesGetPageFromIndexNeg() {
        Storage<Recipe> storage = new StorageOfRecipes(response1, 6);
        assertThrows(IllegalArgumentException.class,  () -> storage.getPageFromIndex(-1));
    }

    @Test
    void testStorageReset() throws URISyntaxException, IOException, InterruptedException {
        Storage<Recipe> storage = new StorageOfRecipes(response2, 6);
        storage.resetStorage(response1);
        assertEquals(storage.getPageFromIndex(0).getFirst(), new Recipe("Rhubarb Shrub Recipe", List.of(DietLabel.LOW_FAT, DietLabel.LOW_SODIUM)
                , List.of(HealthLabel.VEGAN, HealthLabel.VEGETARIAN), List.of("1 cup granulated sugar"), "1345.1847400000001"
                , "1007.3487954", List.of(MealType.LUNCH_DINNER), List.of(DishType.CONDIMENTS_AND_SAUCES),
                List.of(CuisineType.EASTERN_EUROPE)));
    }

    @Test
    void testStorageResetNull() throws URISyntaxException, IOException, InterruptedException {
        Storage<Recipe> storage = new StorageOfRecipes(response2, 6);
        assertThrows(IllegalArgumentException.class, () -> storage.resetStorage(null));
    }
}
