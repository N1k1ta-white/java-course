package bg.sofia.uni.fmi.mjt.cookingcompass.exceptions;

public class ServerNetworkException extends RuntimeException {
    public ServerNetworkException(int code, String message) {
        super("Code error: " + code + System.lineSeparator() + message);
    }

    public ServerNetworkException(int code, String message, Exception e) {
        super("Code error: " + code + System.lineSeparator() + message, e);
    }
}
