package bg.sofia.uni.fmi.mjt.udemy.exception;

public class InsufficientBalanceException extends Exception {
    private final static String message = "You don't have enough funds in the balance!";

    public InsufficientBalanceException() {
        super(message);
    }

    public InsufficientBalanceException(Throwable ex) {
        super(message, ex);
    }
}
