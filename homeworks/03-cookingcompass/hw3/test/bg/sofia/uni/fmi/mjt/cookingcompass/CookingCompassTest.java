package bg.sofia.uni.fmi.mjt.cookingcompass;

import bg.sofia.uni.fmi.mjt.cookingcompass.api.Request;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.HealthLabel;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.MealType;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CookingCompassTest {
    @Test
    void testConstructorNull() {
        assertThrows(IllegalArgumentException.class, () -> new CookingCompass(null, 1));
    }

    @Test
    void testConstructorNeg() {
        assertThrows(IllegalArgumentException.class, () -> new CookingCompass(Request.builder().build(), -1));
    }

    @Test
    void testCookingBuilder() throws URISyntaxException {
        assertEquals(CookingCompass.builder().getCurrentUri(), "https://api.edamam.com/api/recipes/v2?type=public&app_id=c724210f&app_key=b3e59dc38218554dedb406a982623b79");
    }

    @Test
    void testCookingBuilderSetKeywords() throws URISyntaxException {
        assertEquals(CookingCompass.builder().setKeywords("test").getCurrentUri(), "https://api.edamam.com/api/recipes/v2?type=public&app_id=c724210f&app_key=b3e59dc38218554dedb406a982623b79&q=test");
    }

    @Test
    void testCookingBuilderSetKeywordsNull() throws URISyntaxException {
        assertThrows(IllegalArgumentException.class, () -> CookingCompass.builder().setKeywords(null).getCurrentUri());
    }

    @Test
    void testCookingBuilderSetMealTypes() throws URISyntaxException {
        assertEquals(CookingCompass.builder().setKeywords("test").setMealTypes(List.of(MealType.BREAKFAST)).getCurrentUri(),
                "https://api.edamam.com/api/recipes/v2?type=public&app_id=c724210f&app_key=b3e59dc38218554dedb406a982623b79&q=test&mealType=breakfast");
    }

    @Test
    void testCookingBuilderSetMealTypesNull() throws URISyntaxException {
        assertThrows(IllegalArgumentException.class, () -> CookingCompass.builder().setMealTypes(null).getCurrentUri());
    }

    @Test
    void testCookingBuilderSetMealTypesLunchDinner() throws URISyntaxException {
        assertThrows(IllegalArgumentException.class, () -> CookingCompass.builder().setMealTypes(List.of(MealType.LUNCH_DINNER)).getCurrentUri());
    }

    @Test
    void testCookingBuilderSetMealTypesDuplicate() throws URISyntaxException {
        assertThrows(IllegalArgumentException.class, () -> CookingCompass.builder().setMealTypes(List.of(MealType.LUNCH_DINNER, MealType.LUNCH_DINNER)).getCurrentUri());
    }

    @Test
    void testCookingBuilderSetHealthLabels() throws URISyntaxException {
        assertEquals(CookingCompass.builder().setKeywords("test").setMealTypes(List.of(MealType.BREAKFAST)).setHealthLabels(List.of(HealthLabel.CELERY_FREE)).getCurrentUri(),
                "https://api.edamam.com/api/recipes/v2?type=public&app_id=c724210f&app_key=b3e59dc38218554dedb406a982623b79&q=test&mealType=breakfast&health=celery-free");
    }

    @Test
    void testCookingBuilderSetHealthLabelsDuplicate() throws URISyntaxException {
        assertThrows(IllegalArgumentException.class, () -> CookingCompass.builder().setHealthLabels(List.of(HealthLabel.VEGAN, HealthLabel.VEGAN)).getCurrentUri());
    }

    @Test
    void testCookingBuilderSetHealthLabelsNull() throws URISyntaxException {
        assertThrows(IllegalArgumentException.class, () -> CookingCompass.builder().setHealthLabels(null).getCurrentUri());
    }
}
