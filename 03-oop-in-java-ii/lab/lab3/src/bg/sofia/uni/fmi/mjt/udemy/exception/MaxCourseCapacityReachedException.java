package bg.sofia.uni.fmi.mjt.udemy.exception;

public class MaxCourseCapacityReachedException extends Exception {
    private static final String message = "Max course capacity has reached!";

    public MaxCourseCapacityReachedException() {
        super(message);
    }

    public MaxCourseCapacityReachedException(Throwable ex) {
        super(message, ex);
    }
}
