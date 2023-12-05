package bg.sofia.uni.fmi.mjt.udemy.exception;

public class CourseAlreadyPurchasedException extends Exception {
    private final static String message = "Course has already purchased!";

    public CourseAlreadyPurchasedException() {
        super(message);
    }

    public CourseAlreadyPurchasedException(Throwable ex) {
        super(message, ex);
    }
}
