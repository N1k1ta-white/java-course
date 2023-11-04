package bg.sofia.uni.fmi.mjt.udemy.exception;

public class CourseNotCompletedException extends Exception {
    private static final String message = "Course hasn't completed!";

    public CourseNotCompletedException() {
        super(message);
    }

    public CourseNotCompletedException(Throwable ex) {
        super(message, ex);
    }
}
