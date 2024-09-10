package bg.sofia.uni.fmi.mjt.space.rocket;

import java.util.Optional;

public record Rocket(String id, String name, Optional<String> wiki, Optional<Double> height) {

    private static  final String LINE_SEPARATOR = ",";
    private static final int SIZE = 4;
    private static final int ID = 0;
    private static final int NAME = 1;
    private static final int WIKI = 2;
    private static final int HEIGHT = 3;

    public Double getHeight() {
        return height.orElse(-1.);
    }

    public String getWiki() {
        return wiki.orElse("");
    }

    public static Rocket of(String line) {
        String[] data = line.split(LINE_SEPARATOR, SIZE);
        return new Rocket(data[ID], data[NAME],
                (data[WIKI].isBlank() || data[WIKI].isEmpty() ? Optional.empty() : Optional.of(data[WIKI])),
                (data[HEIGHT].isBlank() || data[HEIGHT].isEmpty() ? Optional.empty() :
                        Optional.of(Double.parseDouble(data[HEIGHT].substring(0, data[HEIGHT].length() - 1)))));
    }
}
