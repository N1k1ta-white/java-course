package bg.sofia.uni.fmi.mjt.simcity.exception;

public class BuildableNotFoundException extends RuntimeException {
    private static final String MESSAGE = "On current street hasn't found any building! Street: ";

    public BuildableNotFoundException(String street) {
        super(MESSAGE + street);
    }

    public BuildableNotFoundException(String street, Throwable ex) {
        super(MESSAGE + street, ex);
    }
}