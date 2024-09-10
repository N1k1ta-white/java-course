package bg.sofia.uni.fmi.mjt.cookingcompass.exceptions;

public class ClientNetworkException extends RuntimeException {
    public ClientNetworkException(int code, String message) {
        super("Code error: " + code + System.lineSeparator() + message);
    }

    public ClientNetworkException(int code, String message, Exception e) {
        super("Code error: " + code + System.lineSeparator() + message, e);
    }
}
