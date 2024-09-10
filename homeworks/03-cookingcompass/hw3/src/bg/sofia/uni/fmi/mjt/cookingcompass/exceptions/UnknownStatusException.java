package bg.sofia.uni.fmi.mjt.cookingcompass.exceptions;

public class UnknownStatusException extends RuntimeException {
    public UnknownStatusException(int code, String message) {
        super("Code error: " + code + System.lineSeparator() + message);
    }

    public UnknownStatusException(int code, String message, Exception e) {
        super("Code error: " + code + System.lineSeparator() + message, e);
    }
}
