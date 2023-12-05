package bg.sofia.uni.fmi.mjt.itinerary.exception;

public class NoPathToDestinationException extends Exception {
    public NoPathToDestinationException(String dest) {
        super("For city " + dest + " hasn't found any path!");
    }

    public NoPathToDestinationException(String dest, Throwable ex) {
        super("For city " + dest + " hasn't found any path!", ex);
    }
}
