package bg.sofia.uni.fmi.mjt.space.exception;

public class CipherException extends Exception {
    private static final String MESSAGE = " operation cannot be completed successfully!";

    public enum CipherOperation {
        Decrypt("Decrypt"),
        Encrypt("Encrypt");

        private final String value;
        CipherOperation(String str) {
            value = str;
        }
    }

    public CipherException(CipherOperation op, Exception e) {
        super(op.value + MESSAGE, e);
    }
}
