package bg.sofia.uni.fmi.mjt.space.mission;

public record Detail(String rocketName, String payload) {
    private static final String SEPARATOR = " \\| ";
    private static final int NAME = 0;
    private static final int PAYLOAD = 1;
    public static Detail of(String str) {
        if (str.charAt(0) == '\"') {
            str = str.substring(1, str.length() - 1);
        }
        String[] data = str.split(SEPARATOR);
        return new Detail(data[NAME], data[PAYLOAD]);
    }
}