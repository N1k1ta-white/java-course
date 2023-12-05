package bg.sofia.uni.fmi.mjt.itinerary.exception;

public class CityNotKnownException extends Exception {
    public CityNotKnownException() {
        super("Invalid input of city!");
    }

    public CityNotKnownException(Throwable ex) {
        super("Invalid input of city!", ex);
    }
}
