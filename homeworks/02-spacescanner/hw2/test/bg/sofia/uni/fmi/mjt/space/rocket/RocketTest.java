package bg.sofia.uni.fmi.mjt.space.rocket;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RocketTest {
    @Test
    void testOf() {
        String test1 = "348,Soyuz 6,,";
        String test2 = "349,Soyuz 7,https://en.m.wikipedia.org/wiki/Soyuz-7_(rocket),";
        String test3 = "350,Soyuz FG,https://en.wikipedia.org/wiki/Soyuz-FG,49.5 m";
        String test4 = "327,Scout X-4,,25.0 m";
        assertEquals(new Rocket("348", "Soyuz 6", Optional.empty(), Optional.empty()),
                Rocket.of(test1));
        assertEquals(new Rocket("349", "Soyuz 7",
                Optional.of("https://en.m.wikipedia.org/wiki/Soyuz-7_(rocket)"), Optional.empty()),
                Rocket.of(test2));
        assertEquals(new Rocket("350", "Soyuz FG", Optional.of("https://en.wikipedia.org/wiki/Soyuz-FG"),
                Optional.of(49.5)), Rocket.of(test3));
        assertEquals(new Rocket("327", "Scout X-4", Optional.empty(), Optional.of(25.0)),
                Rocket.of(test4));
    }
}
