package bg.sofia.uni.fmi.mjt.simcity.exception;

public class BuildableAlreadyExistsException extends RuntimeException {
    private static final String MESSAGE = "On current street already exist building! Street: ";

    public BuildableAlreadyExistsException(String street) {
        super(MESSAGE + street);
    }

    public BuildableAlreadyExistsException(String street, Throwable ex) {
        super(MESSAGE + street, ex);
    }
}
