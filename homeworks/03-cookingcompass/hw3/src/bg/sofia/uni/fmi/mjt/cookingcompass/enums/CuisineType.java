package bg.sofia.uni.fmi.mjt.cookingcompass.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum CuisineType {
    AMERICAN("american"),
    ASIAN("asian"),
    BRITISH("british"),
    CARIBBEAN("caribbean"),
    CENTRAL_EUROPE("central europe"),
    CHINESE("chinese"),
    EASTERN_EUROPE("eastern europe"),
    FRENCH("french"),
    GREEK("greek"),
    INDIAN("indian"),
    ITALIAN("italian"),
    JAPANESE("japanese"),
    KOREAN("korean"),
    KOSHER("kosher"),
    MEDITERRANEAN("mediterranean"),
    MEXICAN("mexican"),
    MIDDLE_EASTERN("middle eastern"),
    NORDIC("nordic"),
    SOUTH_AMERICAN("south american"),
    SOUTH_EAST_ASIAN("south east asian"),
    WORLD("world");

    private final String value;

    private static final Map<String, CuisineType> BY_STRING;

    static {
        BY_STRING = new HashMap<>();
        Arrays.stream(CuisineType.values()).forEach(el -> BY_STRING.put(el.getValue(), el));
    }

    CuisineType(String val) {
        value = val;
    }

    public String getValue() {
        return value;
    }

    public static CuisineType cuisineTypeOf(String val) {
        if (val == null) {
            throw new IllegalArgumentException("Doesn't exist any enum in CuisineType with value null!");
        } else if (!BY_STRING.containsKey(val)) {
            throw new IllegalArgumentException("Doesn't exist any enum in CuisineType with value " + val);
        }
        return BY_STRING.get(val);
    }
}
