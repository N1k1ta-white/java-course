package bg.sofia.uni.fmi.mjt.football;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public record Player(String name, String fullName, LocalDate birthDate, int age, double heightCm, double weightKg,
                     List<Position> positions, String nationality, int overallRating, int potential, long valueEuro,
                     long wageEuro, Foot preferredFoot) {

    private static final String SEPARATOR = ";";
    private static final String TIME_SEPARATOR = "/";
    private static final String POS_SEPARATOR = ",";

    private static final int YEAR = 2;
    private static final int MONTH = 0;
    private static final int DAY = 1;
    private static final int NAME = 0;
    private static final int FULL_NAME = 1;
    private static final int BIRTH = 2;
    private static final int AGE = 3;
    private static final int HEIGHT = 4;
    private static final int WEIGHT = 5;
    private static final int POSITIONS = 6;
    private static final int NATION = 7;
    private static final int RATE = 8;
    private static final int POTENTIAL = 9;
    private static final int VALUE = 10;
    private static final int WAGE = 11;
    private static final int FOOT = 12;

    private static LocalDate parseDate(String date) {
        String[] data = date.split(TIME_SEPARATOR);
        return LocalDate.of(Integer.parseInt(data[YEAR]), Integer.parseInt(data[MONTH]), Integer.parseInt(data[DAY]));
    }

    private static List<Position> parsePos(String string) {
        return Arrays.stream(string.split(POS_SEPARATOR))
                .map(Position::valueOf)
                .toList();
    }

    public boolean isPlayerOfPos(List<Position> pos) {
        for (Position p : pos) {
            if (positions.contains(p)) {
                return true;
            }
        }
        return false;
    }

    public static Player of(String line) {
        String[] data = line.split(SEPARATOR);
        return new Player(data[NAME], data[FULL_NAME], parseDate(data[BIRTH]), Integer.parseInt(data[AGE]),
                Double.parseDouble(data[HEIGHT]), Double.parseDouble(data[WEIGHT]), parsePos(data[POSITIONS]),
                data[NATION], Integer.parseInt(data[RATE]), Integer.parseInt(data[POTENTIAL]),
                Integer.parseInt(data[VALUE]), Integer.parseInt(data[WAGE]),
                (data[FOOT].equals("Right") ? Foot.RIGHT : Foot.LEFT));
    }
}
