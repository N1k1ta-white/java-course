package bg.sofia.uni.fmi.mjt.udemy.exception;

public class CourseNotPurchasedException extends Exception {
    private static final String message = "Course hasn't purchased!";

    public CourseNotPurchasedException() {
        super(message);
    }

    public CourseNotPurchasedException(Throwable ex) {
        super(message, ex);
    }
}
