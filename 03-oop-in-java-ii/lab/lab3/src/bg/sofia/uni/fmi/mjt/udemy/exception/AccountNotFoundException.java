package bg.sofia.uni.fmi.mjt.udemy.exception;

public class AccountNotFoundException extends Exception {
    private static final String message = "Account hasn't found!";

    public AccountNotFoundException() {
        super(message);
    }

    public AccountNotFoundException(Throwable ex) {
        super(message, ex);
    }
}
