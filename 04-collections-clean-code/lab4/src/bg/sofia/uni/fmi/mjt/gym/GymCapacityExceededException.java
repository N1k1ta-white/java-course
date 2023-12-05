package bg.sofia.uni.fmi.mjt.gym;

public class GymCapacityExceededException extends Exception {
    public GymCapacityExceededException(int current, int capacity) {
        super("You want add " + current + "'s member when capacity is " + capacity);
    }

    public GymCapacityExceededException(int current, int capacity, Throwable ex) {
        super("You want add " + current + "'s member when capacity is " + capacity, ex);
    }
}
