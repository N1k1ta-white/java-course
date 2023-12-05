package bg.sofia.uni.fmi.mjt.itinerary;

import bg.sofia.uni.fmi.mjt.itinerary.exception.CityNotKnownException;
import bg.sofia.uni.fmi.mjt.itinerary.exception.NoPathToDestinationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static bg.sofia.uni.fmi.mjt.itinerary.vehicle.VehicleType.BUS;
import static bg.sofia.uni.fmi.mjt.itinerary.vehicle.VehicleType.PLANE;
import static bg.sofia.uni.fmi.mjt.itinerary.vehicle.VehicleType.TRAIN;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RideRightTest {
    static City sofia = new City("Sofia", new Location(0, 2000));
    static City plovdiv = new City("Plovdiv", new Location(4000, 1000));
    static City varna = new City("Varna", new Location(9000, 3000));
    static City burgas = new City("Burgas", new Location(9000, 1000));
    static City ruse = new City("Ruse", new Location(7000, 4000));
    static City blagoevgrad = new City("Blagoevgrad", new Location(0, 1000));
    static City kardzhali = new City("Kardzhali", new Location(3000, 0));
    static City tarnovo = new City("Tarnovo", new Location(5000, 3000));

    static City belgrad = new City("Belgrad", new Location(-1000, 5000));
    static RideRight rideRight;
    @BeforeAll
    static void setUpTestCase() {
        List<Journey> schedule = List.of(
                new Journey(BUS, sofia, blagoevgrad, new BigDecimal("20")),
//                new Journey(BUS, blagoevgrad, sofia, new BigDecimal("20")),
                new Journey(BUS, sofia, plovdiv, new BigDecimal("90")),
                new Journey(BUS, plovdiv, sofia, new BigDecimal("90")),
                new Journey(BUS, plovdiv, kardzhali, new BigDecimal("50")),
                new Journey(BUS, kardzhali, plovdiv, new BigDecimal("50")),
                new Journey(BUS, plovdiv, burgas, new BigDecimal("90")),
                new Journey(BUS, burgas, plovdiv, new BigDecimal("90")),
                new Journey(BUS, burgas, varna, new BigDecimal("60")),
                new Journey(BUS, varna, burgas, new BigDecimal("60")),
                new Journey(BUS, sofia, tarnovo, new BigDecimal("150")),
                new Journey(BUS, tarnovo, sofia, new BigDecimal("150")),
                new Journey(BUS, plovdiv, tarnovo, new BigDecimal("40")),
                new Journey(BUS, tarnovo, plovdiv, new BigDecimal("40")),
                new Journey(BUS, tarnovo, ruse, new BigDecimal("70")),
                new Journey(BUS, ruse, tarnovo, new BigDecimal("70")),
                new Journey(BUS, varna, ruse, new BigDecimal("70")),
                new Journey(BUS, ruse, varna, new BigDecimal("70")),
                new Journey(PLANE, varna, burgas, new BigDecimal("200")),
                new Journey(PLANE, burgas, varna, new BigDecimal("200")),
                new Journey(PLANE, burgas, sofia, new BigDecimal("150")),
                new Journey(PLANE, sofia, burgas, new BigDecimal("250")),
                new Journey(PLANE, varna, sofia, new BigDecimal("290")),
                new Journey(PLANE, sofia, varna, new BigDecimal("300")),
                new Journey(PLANE, belgrad, sofia, new BigDecimal("300"))
        );

        rideRight = new RideRight(schedule);
    }

    @Test
    void testFindCheapestPath() throws CityNotKnownException, NoPathToDestinationException {
        assertIterableEquals(rideRight.findCheapestPath(varna, kardzhali, true), List.of(new Journey(BUS, varna, burgas, BigDecimal.valueOf(60)),
        new Journey(BUS, burgas, plovdiv, BigDecimal.valueOf(90)),
        new Journey(BUS, plovdiv, kardzhali, BigDecimal.valueOf(50))));
        assertIterableEquals(rideRight.findCheapestPath(sofia, tarnovo, true), List.of(new Journey(BUS, sofia, plovdiv, BigDecimal.valueOf(90)),
                new Journey(BUS, plovdiv, tarnovo, BigDecimal.valueOf(40))));
        assertIterableEquals(rideRight.findCheapestPath(burgas, blagoevgrad, true), List.of(new Journey(PLANE, burgas, sofia, BigDecimal.valueOf(150)),
                new Journey(BUS, sofia, blagoevgrad, BigDecimal.valueOf(20))));
        assertIterableEquals(rideRight.findCheapestPath(varna, sofia, true), List.of(new Journey(BUS, varna, burgas, BigDecimal.valueOf(60)),
                new Journey(PLANE, burgas, sofia, BigDecimal.valueOf(150))));
        assertIterableEquals(rideRight.findCheapestPath(belgrad, kardzhali, true), List.of(new Journey(PLANE, belgrad, sofia, BigDecimal.valueOf(300)),
                new Journey(BUS, sofia, plovdiv, BigDecimal.valueOf(90)),
                new Journey(BUS, plovdiv, kardzhali, BigDecimal.valueOf(50))));
    }

    @Test
    void testFindCheapestPathSameName() throws CityNotKnownException, NoPathToDestinationException {
        City a = new City("A", new Location(0, 0));
        City b = new City("B", new Location(500, 0));
        City c = new City("C", new Location(0, 500));
        City m = new City("M", new Location(1000, 1000));
        List<Journey> schedule2 = List.of(new Journey(TRAIN, a, c, new BigDecimal("50")),
                new Journey(TRAIN, b, m, new BigDecimal("50")),
                new Journey(TRAIN, a, b, new BigDecimal("50")),
                new Journey(TRAIN, c, m, new BigDecimal("50")));
        RideRight test = new RideRight(schedule2);
        assertIterableEquals(test.findCheapestPath(a, m, true),
                List.of(new Journey(TRAIN, a, b, new BigDecimal("50")),
                        new Journey(TRAIN, b, m, new BigDecimal("50"))));
    }

    @Test
    void testFindCheapestPathThrowNoPath() {
        assertThrows(NoPathToDestinationException.class ,() -> rideRight.findCheapestPath(varna, kardzhali, false));
        assertThrows(NoPathToDestinationException.class, ()->rideRight.findCheapestPath(blagoevgrad, varna, true));
        assertThrows(NoPathToDestinationException.class, ()->rideRight.findCheapestPath(varna, belgrad, true));
    }

    @Test
    void testFindCheapestPathHasOnePath() throws CityNotKnownException, NoPathToDestinationException {
        assertIterableEquals(rideRight.findCheapestPath(varna, burgas, false), List.of(new Journey(BUS, varna, burgas, BigDecimal.valueOf(60))));
        assertIterableEquals(rideRight.findCheapestPath(burgas, sofia, false), List.of(new Journey(PLANE, burgas, sofia, BigDecimal.valueOf(150))));
    }

    @Test
    void testFindCheapestPathThrowNoCity() {
        assertThrows(CityNotKnownException.class ,() -> rideRight.findCheapestPath(new City("Test1", new Location(1, 1)), kardzhali, false));
        assertThrows(CityNotKnownException.class ,() -> rideRight.findCheapestPath(varna ,new City("Test1", new Location(1, 1)), false));
        assertThrows(CityNotKnownException.class ,() -> rideRight.findCheapestPath(new City("Test2", new Location(1, 1)) ,new City("Test1", new Location(1, 1)), false));
    }
}
