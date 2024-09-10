package bg.sofia.uni.fmi.mjt.cookingcompass.exceptions;

public class NetworkExceptionFactory {
    private static final int SERVER = 500;
    private static final int BOUND = 600;
    private static final int CLIENT = 400;

    public static RuntimeException exceptionByCodeStatus(int codeStatus, String text) {
        if (codeStatus >= SERVER && codeStatus < BOUND) {
            return new ServerNetworkException(codeStatus, text);
        } else if (codeStatus >= CLIENT && codeStatus < SERVER) {
            return new ClientNetworkException(codeStatus, text);
        }
        return new UnknownStatusException(codeStatus, "Unexpected error's code status!");
    }
}
