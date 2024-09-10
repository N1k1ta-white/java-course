package bg.sofia.uni.fmi.mjt.space.exception;

public class TimeFrameMismatchException extends RuntimeException {
    private static final String MESSAGE = "You have some problems with your entered time!";

    public TimeFrameMismatchException() {
        super(MESSAGE);
    }

}
