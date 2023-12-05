package bg.sofia.uni.fmi.mjt.gym.member;

import java.time.DayOfWeek;

public class DayOffException extends RuntimeException {
    private static final String MESSAGE = "is a day off";

    public DayOffException(DayOfWeek day) {
        super(day.toString() + MESSAGE);
    }

    public DayOffException(DayOfWeek day, Throwable ex) {
        super(day.toString() + MESSAGE, ex);
    }
}
