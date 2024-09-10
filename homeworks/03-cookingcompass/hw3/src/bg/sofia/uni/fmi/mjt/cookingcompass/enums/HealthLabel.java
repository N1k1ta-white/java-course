package bg.sofia.uni.fmi.mjt.cookingcompass.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum HealthLabel {
    ALCOHOL_COCKTAIL("Alcohol-Cocktail", "alcohol-cocktail"),
    ALCOHOL_FREE("Alcohol-Free", "alcohol-free"),
    CELERY_FREE("Celery-Free", "celery-free"),
    CRUSTACEAN_FREE("Crustacean-Free", "crustacean-free"),
    DAIRY_FREE("Dairy-Free", "dairy-free"),
    DASH("DASH", "DASH"),
    EGG_FREE("Egg-Free", "egg-free"),
    FISH_FREE("Fish-Free", "fish-free"),
    FODMAP_FREE("FODMAP-Free", "fodmap-free"),
    GLUTEN_FREE("Gluten-Free", "gluten-free"),
    IMMUNO_SUPPORTIVE("Immuno-Supportive", "immuno-supportive"),
    KETO_FRIENDLY("Keto-Friendly", "keto-friendly"),
    KIDNEY_FRIENDLY("Kidney-Friendly", "kidney-friendly"),
    KOSHER("Kosher", "kosher"),
    LOW_POTASSIUM("Low Potassium", "low-potassium"),
    LOW_SUGAR("Low Sugar", "low-sugar"),
    LUPINE_FREE("Lupine-Free", "lupine-free"),
    MEDITERRANEAN("Mediterranean", "Mediterranean"),
    MOLLUSK_FREE("Mollusk-Free", "mollusk-free"),
    MUSTARD_FREE("Mustard-Free", "mustard-free"),
    NO_OIL_ADDED("No oil added", "no-oil-added"),
    PALEO("Paleo", "paleo"),
    PEANUT_FREE("Peanut-Free", "peanut-free"),
    PESCATARIAN("Pescatarian", "pescatarian"),
    PORK_FREE("Pork-Free", "pork-free"),
    RED_MEAT_FREE("Red-Meat-Free", "red-meat-free"),
    SESAME_FREE("Sesame-Free", "sesame-free"),
    SHELLFISH_FREE("Shellfish-Free", "shellfish-free"),
    SOY_FREE("Soy-Free", "soy-free"),
    SUGAR_CONSCIOUS("Sugar-Conscious", "sugar-conscious"),
    SULFITE_FREE("Sulfite-Free", "sulfite-free"),
    TREE_NUT_FREE("Tree-Nut-Free", "tree-nut-free"),
    VEGAN("Vegan", "vegan"),
    VEGETARIAN("Vegetarian", "vegetarian"),
    WHEAT_FREE("Wheat-Free", "wheat-free");

    private final String webLabel;
    private final String apiParameter;

    private static final Map<String, HealthLabel> BY_WEB_TO_ENUM;

    static {
        BY_WEB_TO_ENUM = new HashMap<>();
        Arrays.stream(HealthLabel.values()).forEach(el -> BY_WEB_TO_ENUM.put(el.webLabel, el));
    }

    HealthLabel(String web, String apiParam) {
        webLabel = web;
        apiParameter = apiParam;
    }

    public String getWebLabel() {
        return webLabel;
    }

    public String getApiParameter() {
        return apiParameter;
    }

    public static HealthLabel healthLabelOf(String val) {
        if (val == null) {
            throw new IllegalArgumentException("Doesn't exist any enum in HealthLabel with value null!");
        } else if (!BY_WEB_TO_ENUM.containsKey(val)) {
            throw new IllegalArgumentException("Doesn't exist any enum in HealthLabel with value " + val);
        }
        return BY_WEB_TO_ENUM.get(val);
    }
}