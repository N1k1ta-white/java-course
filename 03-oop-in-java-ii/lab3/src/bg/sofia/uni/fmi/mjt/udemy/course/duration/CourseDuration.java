package bg.sofia.uni.fmi.mjt.udemy.course.duration;

public record CourseDuration(int hours, int minutes) {
    public CourseDuration(int hours, int minutes) {
        if (hours > 24 || hours < 0 || minutes > 60 || minutes < 0)
            throw new IllegalArgumentException("Invalid input!");
        this.hours = hours;
        this.minutes = minutes;
    }
}
