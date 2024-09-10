package bg.sofia.uni.fmi.mjt.space.mission;

import bg.sofia.uni.fmi.mjt.space.rocket.RocketStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MissionTest {
    @Test
    void testOF() {
        String test1 = "12,CASC,\"LC-3, Xichang Satellite Launch Center, China\",\"Thu Jul 09, 2020\"," +
                "Long March 3B/E | Apstar-6D,StatusActive,\"29.15 \",Success";
        String test2 = "13,IAI,\"Pad 1, Palmachim Airbase, Israel\",\"Mon Jul 06, 2020\",Shavit-2 | Ofek-16," +
                "StatusActive,,Success";
        Mission obj1 = new Mission("12", "CASC", "LC-3, Xichang Satellite Launch Center, China",
                LocalDate.of(2020, 7, 9),
                new Detail("Long March 3B/E", "Apstar-6D"), RocketStatus.STATUS_ACTIVE,
                Optional.of(29.15), MissionStatus.SUCCESS);
        Mission obj2 = new Mission("13", "IAI", "Pad 1, Palmachim Airbase, Israel",
                LocalDate.of(2020, 7, 6), new Detail("Shavit-2", "Ofek-16"),
                RocketStatus.STATUS_ACTIVE, Optional.empty(), MissionStatus.SUCCESS);
        assertEquals(Mission.of(test1), obj1);
        assertEquals(Mission.of(test2), obj2);
    }
}
