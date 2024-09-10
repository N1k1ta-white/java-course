package bg.sofia.uni.fmi.mjt.cookingcompass.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum DishType {
    ALCOHOL_COCKTAIL("alcohol cocktail"),
    BISCUITS_AND_COOKIES("biscuits and cookies"),
    BREAD("bread"),
    CEREALS("cereals"),
    CONDIMENTS_AND_SAUCES("condiments and sauces"),
    DESSERTS("desserts"),
    DRINKS("drinks"),
    EGG("egg"),
    ICE_CREAM_AND_CUSTARD("ice cream and custard"),
    MAIN_COURSE("main course"),
    PANCAKE("pancake"),
    PASTA("pasta"),
    PASTRY("pastry"),
    PIES_AND_TARTS("pies and tarts"),
    PIZZA("pizza"),
    PREPS("preps"),
    PRESERVE("preserve"),
    SALAD("salad"),
    SANDWICHES("sandwiches"),
    SEAFOOD("seafood"),
    SIDE_DISH("side dish"),
    SOUP("soup"),
    SPECIAL_OCCASIONS("special occasions"),
    STARTER("starter"),
    SWEETS("sweets");

    private final String value;

    private static final Map<String, DishType> BY_STRING;

    static {
        BY_STRING = new HashMap<>();
        Arrays.stream(DishType.values()).forEach(el -> BY_STRING.put(el.getValue(), el));
    }

    DishType(String val) {
        value = val;
    }

    public String getValue() {
        return value;
    }

    public static DishType dishTypeOf(String val) {
        if (val == null) {
            throw new IllegalArgumentException("Doesn't exist any enum in DishType with value null!");
        } else if (!BY_STRING.containsKey(val)) {
            throw new IllegalArgumentException("Doesn't exist any enum in DishType with value " + val);
        }
        return BY_STRING.get(val);
    }
}
