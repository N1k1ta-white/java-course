package bg.sofia.uni.fmi.mjt.simcity.exception;

public class InsufficientPlotAreaException extends RuntimeException {
    private static final String MESSAGE = "Are you want live in prison?! Area: ";

    public InsufficientPlotAreaException(int area) {
        super(MESSAGE + area);
    }

    public InsufficientPlotAreaException(int area, Throwable ex) {
        super(MESSAGE + area, ex);
    }
}
