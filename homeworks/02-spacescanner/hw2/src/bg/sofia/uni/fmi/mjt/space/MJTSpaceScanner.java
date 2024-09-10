package bg.sofia.uni.fmi.mjt.space;

import bg.sofia.uni.fmi.mjt.space.algorithm.Rijndael;
import bg.sofia.uni.fmi.mjt.space.exception.CipherException;
import bg.sofia.uni.fmi.mjt.space.exception.TimeFrameMismatchException;
import bg.sofia.uni.fmi.mjt.space.mission.Mission;
import bg.sofia.uni.fmi.mjt.space.mission.MissionStatus;
import bg.sofia.uni.fmi.mjt.space.rocket.Rocket;
import bg.sofia.uni.fmi.mjt.space.rocket.RocketStatus;

import javax.crypto.SecretKey;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MJTSpaceScanner implements SpaceScannerAPI {

    private final Rijndael cipher;
    private final Collection<Rocket> rockets;
    private final Collection<Mission> missions;

    private void checkDate(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Invalid input!");
        }
        if (to.isBefore(from)) {
            throw new TimeFrameMismatchException();
        }
    }

    public  MJTSpaceScanner(Reader missionsReader, Reader rocketsReader, SecretKey secretKey) {
        cipher = new Rijndael(secretKey);
        rockets = new ArrayList<>();
        missions = new ArrayList<>();
        String line;
        try (BufferedReader bufferedReaderMission = new BufferedReader(missionsReader);
            BufferedReader bufferedReaderRockets = new BufferedReader(rocketsReader)) {
            while ((line = bufferedReaderMission.readLine()) != null) {
                missions.add(Mission.of(line));
            }
            while ((line = bufferedReaderRockets.readLine()) != null) {
                rockets.add(Rocket.of(line));
            }
        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred while writing of file", e);
        }
    }

    /**
     * Returns all missions in the dataset.
     * If there are no missions, return an empty collection.
     */
    @Override
    public Collection<Mission> getAllMissions() {
        return Collections.unmodifiableList((List<? extends Mission>) missions);
    }

    /**
     * Returns all missions in the dataset with a given status.
     * If there are no missions, return an empty collection.
     *
     * @param missionStatus the status of the missions
     * @throws IllegalArgumentException if missionStatus is null
     */
    @Override
    public Collection<Mission> getAllMissions(MissionStatus missionStatus) {
        if (missionStatus == null) {
            throw new IllegalArgumentException("MissionStatus is null!");
        }
        return missions.stream()
                .parallel()
                .filter((el) -> el.missionStatus().equals(missionStatus))
                .toList();
    }

    /**
     * Returns the company with the most successful missions in a given time period.
     * If there are no missions, return an empty string.
     *
     * @param from the inclusive beginning of the time frame
     * @param to   the inclusive end of the time frame
     * @throws IllegalArgumentException   if from or to is null
     * @throws TimeFrameMismatchException if to is before from
     */
    @Override
    public String getCompanyWithMostSuccessfulMissions(LocalDate from, LocalDate to) {
        checkDate(from, to);
        Optional<Map.Entry<String, List<Mission>>> opt = missions.parallelStream()
                .filter(mission -> mission.missionStatus().equals(MissionStatus.SUCCESS))
                .filter(mission -> mission.date().isAfter(from) && mission.date().isBefore(to))
                .collect(Collectors.groupingBy(Mission::company))
                .entrySet()
                .parallelStream()
                .max(Comparator.comparingInt(el -> el.getValue().size()));
        return (opt.isPresent() ? opt.get().getKey() : "");
    }

    /**
     * Groups missions by country.
     * If there are no missions, return an empty map.
     */
    @Override
    public Map<String, Collection<Mission>> getMissionsPerCountry() {
        return missions.stream()
                .collect(Collectors.groupingBy(Mission::getCountry, Collectors.toCollection(ArrayList::new)));
    }

    /**
     * Returns the top N least expensive missions, ordered from cheapest to more expensive.
     * If there are no missions, return an empty list.
     *
     * @param n             the number of missions to be returned
     * @param missionStatus the status of the missions
     * @param rocketStatus  the status of the rockets
     * @throws IllegalArgumentException if n is less than or equal to 0, missionStatus or rocketStatus is null
     */
    @Override
    public List<Mission> getTopNLeastExpensiveMissions(int n, MissionStatus missionStatus, RocketStatus rocketStatus) {
        if (n <= 0 || missionStatus == null || rocketStatus == null) {
            throw new IllegalArgumentException("Invalid input!");
        }
        return missions.parallelStream()
                .filter(mission -> mission.missionStatus().equals(missionStatus))
                .filter(mission -> mission.rocketStatus().equals(rocketStatus))
                .sorted(Comparator.comparingDouble(Mission::getCost))
                .limit(n)
                .toList();
    }

    /**
     * Returns the most desired location for missions per company.
     * If there are no missions, return an empty map.
     */
    @Override
    public Map<String, String> getMostDesiredLocationForMissionsPerCompany() {
        Set<String> companies = missions.parallelStream()
                .map(Mission::company)
                .collect(Collectors.toUnmodifiableSet());
        Map<String, String> res = new HashMap<>();
        for (String company : companies) {
            Optional<Map.Entry<String, Long>> opt = missions.parallelStream()
                    .filter(mission -> mission.company().equals(company))
                    .map(Mission::location)
                    .collect(Collectors.groupingBy(missions -> missions, Collectors.counting()))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue());
            if (opt.isPresent())
                res.put(company, opt.get().getKey());
        }
        return res;
    }

    /**
     * Returns the company mapped to its location with most successful missions.
     * If there are no missions, return an empty map.
     *
     * @param from the inclusive beginning of the time frame
     * @param to   the inclusive end of the time frame
     * @throws IllegalArgumentException   if from or to is null
     * @throws TimeFrameMismatchException if to is before from
     */
    @Override
    public Map<String, String> getLocationWithMostSuccessfulMissionsPerCompany(LocalDate from, LocalDate to) {
        checkDate(from, to);
        Set<String> companies = missions.parallelStream()
                .map(Mission::company)
                .collect(Collectors.toUnmodifiableSet());
        Map<String, String> res = new HashMap<>();
        for (String company : companies) {
            Optional<Map.Entry<String, Long>> opt = missions.parallelStream()
                    .filter(mission -> mission.company().equals(company))
                    .filter(mission -> mission.missionStatus().equals(MissionStatus.SUCCESS))
                    .filter(mission -> mission.date().isAfter(from) && mission.date().isBefore(to))
                    .map(Mission::location)
                    .collect(Collectors.groupingBy(missions -> missions, Collectors.counting()))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue());
            if (opt.isPresent())
                res.put(company, opt.get().getKey());
        }
        return res;
    }

    /**
     * Returns all rockets in the dataset.
     * If there are no rockets, return an empty collection.
     */
    @Override
    public Collection<Rocket> getAllRockets() {
        return Collections.unmodifiableList((List<? extends Rocket>) rockets);
    }

    /**
     * Returns the top N tallest rockets, in decreasing order.
     * If there are no rockets, return an empty list.
     *
     * @param n the number of rockets to be returned
     * @throws IllegalArgumentException if n is less than ot equal to 0
     */
    @Override
    public List<Rocket> getTopNTallestRockets(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Invalid input!");
        }
        return rockets.parallelStream()
                .sorted(Comparator.comparingDouble(Rocket::getHeight).reversed())
                .limit(n)
                .toList();
    }

    /**
     * Returns a mapping of rockets (by name) to their respective wiki page (if present).
     * If there are no rockets, return an empty map.
     */
    @Override
    public Map<String, Optional<String>> getWikiPageForRocket() {
        return rockets.parallelStream()
                .collect(Collectors.toMap(Rocket::name, Rocket::wiki));
    }

    /**
     * Returns the wiki pages for the rockets used in the N most expensive missions.
     * If there are no missions, return an empty list.
     *
     * @param n             the number of missions to be returned
     * @param missionStatus the status of the missions
     * @param rocketStatus  the status of the rockets
     * @throws IllegalArgumentException if n is less than ot equal to 0, or missionStatus or rocketStatus is null
     */
    @Override
    public List<String> getWikiPagesForRocketsUsedInMostExpensiveMissions(int n, MissionStatus missionStatus,
                                                                          RocketStatus rocketStatus) {
        if (n <= 0 || missionStatus == null || rocketStatus == null) {
            throw new IllegalArgumentException("Invalid input!");
        }
        List<String> topNRockets = missions.parallelStream()
                .filter(mission -> mission.rocketStatus().equals(rocketStatus))
                .filter(mission -> mission.missionStatus().equals(missionStatus))
                .sorted(Comparator.comparingDouble(Mission::getCost).reversed())
                .limit(n)
                .map(Mission::getRocket)
                .toList();
        List<String> res = new ArrayList<>();
        for (String nRocket : topNRockets) {
            List<String> find = rockets.parallelStream()
                    .filter(rocket -> rocket.name().equals(nRocket))
                    .map(Rocket::getWiki)
                    .toList();
            if (!find.isEmpty() && !find.getFirst().isEmpty())
                res.add(find.getFirst());
        }
        return res;
    }

    /**
     * Saves the name of the most reliable rocket in a given time period in an encrypted format.
     *
     * @param outputStream the output stream where the encrypted result is written into
     * @param from         the inclusive beginning of the time frame
     * @param to           the inclusive end of the time frame
     * @throws IllegalArgumentException   if outputStream, from or to is null
     * @throws CipherException            if the encrypt/decrypt operation cannot be completed successfully
     * @throws TimeFrameMismatchException if to is before from
     */
    @Override
    public void saveMostReliableRocket(OutputStream outputStream, LocalDate from, LocalDate to) throws CipherException {
        checkDate(from, to);
        Set<String> rocketsForAnalyse = missions.parallelStream()
                .filter(mission -> mission.date().isAfter(from) && mission.date().isBefore(to))
                .map(Mission::getRocket)
                .collect(Collectors.toUnmodifiableSet());
        Map<String, Long> rocketMission = missions.parallelStream()
                .filter(mission -> mission.date().isAfter(from) && mission.date().isBefore(to))
                .collect(Collectors.groupingBy(Mission::getRocket, Collectors.counting()));
        Map<String, Long> rocketSuccessfulMission = missions.parallelStream()
                .filter(mission -> mission.missionStatus().equals(MissionStatus.SUCCESS))
                .filter(mission -> mission.date().isAfter(from) && mission.date().isBefore(to))
                .collect(Collectors.groupingBy(Mission::getRocket, Collectors.counting()));
        double max = -1;
        double curr;
        String name = "";
        for (String rocketForAnalyse : rocketsForAnalyse) {
            double success = rocketSuccessfulMission.containsKey(rocketForAnalyse) ?
                    rocketSuccessfulMission.get(rocketForAnalyse) : 0;
            double all = rocketMission.containsKey(rocketForAnalyse) ?
                    rocketMission.get(rocketForAnalyse) : 0;
            curr = ((2 * success) + (all - success)) / (2 * all);
            if (Double.compare(curr, max) > 0) {
                max = curr;
                name = rocketForAnalyse;
            }
        }
        cipher.encrypt(new ByteArrayInputStream(name.getBytes()), outputStream);
    }
}
