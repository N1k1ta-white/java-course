package bg.sofia.uni.fmi.mjt.cookingcompass.api;

import bg.sofia.uni.fmi.mjt.cookingcompass.enums.CuisineType;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.DietLabel;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.DishType;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.HealthLabel;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.MealType;
import bg.sofia.uni.fmi.mjt.cookingcompass.recipe.Recipe;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonConverterTest {

    @Test
    void testFromJsonToResponse() throws IOException, InterruptedException {
        Response response = JsonConverter.convertJson(Service.getJson());
        assertEquals(response.receipts().size(), 20);
        Recipe test1 = new Recipe("Rhubarb Shrub Recipe", List.of(DietLabel.LOW_FAT, DietLabel.LOW_SODIUM)
        , List.of(HealthLabel.VEGAN, HealthLabel.VEGETARIAN), List.of("1 cup granulated sugar"), "1345.1847400000001"
        , "1007.3487954", List.of(MealType.LUNCH_DINNER), List.of(DishType.CONDIMENTS_AND_SAUCES),
                List.of(CuisineType.EASTERN_EUROPE));
        assertEquals(response.receipts().getFirst(), test1);
        Recipe test2 = new Recipe("Make-Ahead Marinated Cucumber Salad with Radishes, Dill, and Shrimp Recipe",
                List.of(DietLabel.LOW_CARB), List.of(HealthLabel.ALCOHOL_FREE, HealthLabel.SULFITE_FREE),
                List.of("3 tablespoons extra-virgin olive oil", "3/4 pound large shrimp, peeled and deveined"), "826.4626574052462",
                "934.8391370345751", List.of(MealType.LUNCH_DINNER), List.of(DishType.SALAD), List.of(CuisineType.EASTERN_EUROPE));
        assertEquals(response.receipts().getLast(), test2);
    }

//    @Test
//    void testExceptionCreator() {
//        assertThrows(ClientNetworkException.class, () -> DataConverter.exceptionCreator(400, """
//                [{"errorCode":"not_allowed","message":"Type not allowed","params":["type"]}]"""));
//    }
}
