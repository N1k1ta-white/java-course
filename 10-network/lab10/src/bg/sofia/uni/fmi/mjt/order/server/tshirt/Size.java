package bg.sofia.uni.fmi.mjt.order.server.tshirt;

public enum Size {
    S("S"),
    M("M"),
    L("L"),
    XL("XL"),
    UNKNOWN("UNKNOWN");

    private final String name;

    Size(String name) {
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
    public static Size getValue(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Color can't be null!");
        }
        try {
            return Size.valueOf(str);
        } catch (IllegalArgumentException e) {
            return Size.UNKNOWN;
        }
    }
}