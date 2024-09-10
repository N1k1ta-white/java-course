package bg.sofia.uni.fmi.mjt.order.server.destination;

public enum Destination {
    EUROPE("EUROPE"),
    NORTH_AMERICA("NORTH_AMERICA"),
    AUSTRALIA("AUSTRALIA"),
    UNKNOWN("UNKNOWN");

    private final String name;

    Destination(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Find value of string in enum class
     *
     * @param str string
     * @return Relevant value
     */
    public static Destination getValue(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Color can't be null!");
        }
        try {
            return Destination.valueOf(str);
        } catch (IllegalArgumentException e) {
            return Destination.UNKNOWN;
        }
    }
}