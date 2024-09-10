package bg.sofia.uni.fmi.mjt.cookingcompass.recipe;

import bg.sofia.uni.fmi.mjt.cookingcompass.enums.CuisineType;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.DietLabel;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.DishType;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.HealthLabel;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.MealType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public record Recipe(String label, List<DietLabel> dietLabels, List<HealthLabel> healthLabels, List<String> ingredients,
                     String weight, String calories, List<MealType> mealTypes, List<DishType> dishTypes,
                     List<CuisineType> cuisineTypes) {
    public Recipe {
        weight = BigDecimal.valueOf(Double.parseDouble(weight))
                .setScale(2, RoundingMode.HALF_UP)
                .toString();
        calories = BigDecimal.valueOf(Double.parseDouble(calories))
                .setScale(0, RoundingMode.HALF_UP)
                .toString();
    }
}
