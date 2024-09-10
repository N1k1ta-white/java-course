package bg.sofia.uni.fmi.mjt.space.mission;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DetailTest {

    @Test
    void testOf() {
        Detail test = Detail.of("test | payload");
        assertEquals(new Detail("test", "payload"), test);
    }
}
