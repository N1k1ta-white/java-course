package bg.sofia.uni.fmi.mjt.cookingcompass.api;

import bg.sofia.uni.fmi.mjt.cookingcompass.enums.CuisineType;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.DietLabel;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.DishType;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.HealthLabel;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.MealType;
import bg.sofia.uni.fmi.mjt.cookingcompass.recipe.Recipe;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class JsonConverter {
    private static <E> List<E> getListOfEnum(JsonArray jsonArray, Function<String, E> function) {
        return jsonArray.asList().stream()
                .map(JsonElement::toString)
                .map(string -> string.substring(1, string.length() - 1))
                .map(function)
                .toList();
    }

    private static Recipe jsonElementToRecipe(JsonElement jsonElement) {
        JsonObject obj = jsonElement.getAsJsonObject().getAsJsonObject("recipe");
        String label = obj.get("label").getAsString();
        List<DietLabel> dietLabelList = getListOfEnum(obj.getAsJsonArray("dietLabels"),
                DietLabel::dietLabelOf);
        List<HealthLabel> healthLabelList = getListOfEnum(obj.getAsJsonArray("healthLabels"),
                HealthLabel::healthLabelOf);
        List<String> ingredients = obj.getAsJsonArray("ingredientLines").asList().stream()
                .map(JsonElement::toString)
                .map(string -> string.substring(1, string.length() - 1))
                .toList();
        String weight = obj.get("totalWeight").getAsString();
        String calories = obj.get("calories").getAsString();
        List<MealType> mealTypeList = getListOfEnum(obj.getAsJsonArray("mealType"), MealType::mealTypeOf);
        List<DishType> dishTypeList = getListOfEnum(obj.getAsJsonArray("dishType"), DishType::dishTypeOf);
        List<CuisineType> cuisineTypeList = getListOfEnum(obj.getAsJsonArray("cuisineType"),
                CuisineType::cuisineTypeOf);
        return new Recipe(label, dietLabelList, healthLabelList, ingredients, weight, calories, mealTypeList,
                dishTypeList, cuisineTypeList);
    }

    public static Response convertJson(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        List<Recipe> recipeArrayList = new ArrayList<>();
        String next = jsonObject.getAsJsonObject("_links").has("next") ?
                jsonObject.getAsJsonObject("_links").get("next")
                        .getAsJsonObject().get("href").getAsString() : "None";
        if (!jsonObject.getAsJsonArray("hits").isEmpty()) {
            jsonObject.getAsJsonArray("hits")
                    .forEach(jsonElement ->
                            recipeArrayList.add(jsonElementToRecipe(jsonElement))
                );
        }
        return new Response(recipeArrayList, next);
    }
//
//    public static void exceptionCreator(int statusCode, String json) {
//        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonArray().asList().get(0).getAsJsonObject();
//        throw NetworkExceptionFactory.exceptionByCodeStatus(statusCode, jsonObject.get("message").getAsString());
//    }
}
