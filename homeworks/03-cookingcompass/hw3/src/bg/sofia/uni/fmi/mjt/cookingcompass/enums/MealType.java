package bg.sofia.uni.fmi.mjt.cookingcompass.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum MealType {
    BREAKFAST("breakfast"),
    BRUNCH("brunch"),
    LUNCH_DINNER("lunch/dinner"),
    LUNCH("lunch"),
    DINNER("dinner"),
    SNACK("snack"),
    TEATIME("teatime");

    private final String value;

    private static final Map<String, MealType> BY_STRING;

    static {
        BY_STRING = new HashMap<>();
        Arrays.stream(MealType.values()).forEach(el -> BY_STRING.put(el.getValue(), el));
    }

    MealType(String val) {
        value = val;
    }

    public String getValue() {
        return value;
    }

    public static MealType mealTypeOf(String val) {
        if (val == null) {
            throw new IllegalArgumentException("Doesn't exist any enum in MealType with value null!");
        } else if (!BY_STRING.containsKey(val)) {
            throw new IllegalArgumentException("Doesn't exist any enum in MealType with value " + val);
        }
        return BY_STRING.get(val);
    }

}
