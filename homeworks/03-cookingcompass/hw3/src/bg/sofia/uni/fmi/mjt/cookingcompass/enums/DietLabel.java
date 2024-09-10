package bg.sofia.uni.fmi.mjt.cookingcompass.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum DietLabel {
    BALANCED("Balanced", "balanced"),
    HIGH_FIBER("High-Fiber", "high-fiber"),
    HIGH_PROTEIN("High-Protein", "high-protein"),
    LOW_CARB("Low-Carb", "low-carb"),
    LOW_FAT("Low-Fat", "low-fat"),
    LOW_SODIUM("Low-Sodium", "low-sodium");

    private final String webLabel;
    private final String apiParameter;

    private static final Map<String, DietLabel> BY_WEB_TO_ENUM;

    static {
        BY_WEB_TO_ENUM = new HashMap<>();
        Arrays.stream(DietLabel.values()).forEach(el -> BY_WEB_TO_ENUM.put(el.webLabel, el));
    }

    DietLabel(String web, String apiParam) {
        webLabel = web;
        apiParameter = apiParam;
    }

    public String getWebLabel() {
        return webLabel;
    }

    public String getApiParameter() {
        return apiParameter;
    }

    public static DietLabel dietLabelOf(String val) {
        if (val == null) {
            throw new IllegalArgumentException("Doesn't exist any enum in DietLabel with value null!");
        } else if (!BY_WEB_TO_ENUM.containsKey(val)) {
            throw new IllegalArgumentException("Doesn't exist any enum in DietLabel with value " + val);
        }
        return BY_WEB_TO_ENUM.get(val);
    }
}
