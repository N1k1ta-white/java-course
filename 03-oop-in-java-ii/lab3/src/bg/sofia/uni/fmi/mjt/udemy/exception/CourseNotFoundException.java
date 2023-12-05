package bg.sofia.uni.fmi.mjt.udemy.exception;

public class CourseNotFoundException extends Exception {
    private static final String message = "Course hasn't found!";
    public CourseNotFoundException() {
        super(message);
    }

    public CourseNotFoundException(Throwable ex) {
        super(message, ex);
    }
}
