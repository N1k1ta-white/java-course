package bg.sofia.uni.fmi.mjt.udemy.exception;

import bg.sofia.uni.fmi.mjt.udemy.course.Resource;

public class ResourceNotFoundException extends Exception {
    private static final String message = "Resource hasn't found!";
    public ResourceNotFoundException() {
        super(message);
    }
    public ResourceNotFoundException(Throwable throwable) {
        super(message, throwable);
    }
}
