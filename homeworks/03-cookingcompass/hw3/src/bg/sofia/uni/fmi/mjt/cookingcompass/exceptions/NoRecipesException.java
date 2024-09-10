package bg.sofia.uni.fmi.mjt.cookingcompass.exceptions;

public class NoRecipesException extends RuntimeException {
    private static final String MESSAGE = "Haven't recipes to show anymore!";

    public NoRecipesException() {
        super(MESSAGE);
    }

    public NoRecipesException(Exception e) {
        super(MESSAGE, e);
    }
}
