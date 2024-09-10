package bg.sofia.uni.fmi.mjt.order.server.tshirt;

public enum Color {
    BLACK("BLACK"),
    WHITE("WHITE"),
    RED("RED"),
    UNKNOWN("UNKNOWN");

    private final String name;

    Color(String name) {
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
    public static Color getValue(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Color can't be null!");
        }
        try {
            return Color.valueOf(str);
        } catch (IllegalArgumentException e) {
            return Color.UNKNOWN;
        }
    }
}