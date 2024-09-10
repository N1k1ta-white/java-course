package bg.sofia.uni.fmi.mjt.space.mission;

import bg.sofia.uni.fmi.mjt.space.rocket.RocketStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public record Mission(String id, String company, String location, LocalDate date, Detail detail,
                      RocketStatus rocketStatus, Optional<Double> cost, MissionStatus missionStatus) {
    private static final String LINE_SEPARATOR = ",(?=(?:[^\"]|\"[^\"]*\")*$)";
    private static final int ID = 0;
    private static final int COMP = 1;
    private static final int LOC = 2;
    private static final int DATE = 3;
    private static final int DET = 4;
    private static final int ROCK_ST = 5;
    private static final int COST = 6;
    private static final int MISS_ST = 7;

    private static RocketStatus parseRocketStatus(String str) {
        return switch (str) {
            case "StatusRetired" -> RocketStatus.STATUS_RETIRED;
            case "StatusActive" -> RocketStatus.STATUS_ACTIVE;
            default -> throw new IllegalStateException("Unexpected value: " + str);
        };
    }

    private static MissionStatus parseMissionStatus(String str) {
        return switch (str) {
            case "Success" -> MissionStatus.SUCCESS;
            case "Failure" -> MissionStatus.FAILURE;
            case "Partial Failure" -> MissionStatus.PARTIAL_FAILURE;
            case "Prelaunch Failure" -> MissionStatus.PRELAUNCH_FAILURE;
            default -> throw new IllegalStateException("Unexpected value: " + str);
        };
    }

    public Double getCost() {
        return cost.orElse(-1.);
    }

    public String getRocket() {
        return detail.rocketName();
    }

    public String getCountry() {
        return List.of(location.split(" ")).getLast();
    }

    public static Mission of(String line) {
        String[] data = line.split(LINE_SEPARATOR);
        return new Mission(data[ID], data[COMP], data[LOC].replaceAll("\"", ""),
                LocalDate.parse(data[DATE].substring(1, data[DATE].length() - 1),
                        DateTimeFormatter.ofPattern("E MMM dd, yyyy").withLocale(Locale.US)), Detail.of(data[DET]),
                parseRocketStatus(data[ROCK_ST]),
                (data[COST].isBlank() || data[COST].isEmpty() ? Optional.empty() :
                        Optional.of(Double.parseDouble(data[COST].substring(1, data[COST].length() - 1)))),
                parseMissionStatus(data[MISS_ST]));
    }
}
