package bg.sofia.uni.fmi.mjt.football;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static bg.sofia.uni.fmi.mjt.football.Position.GK;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    @Test
    void testParsePlayer() {
        Player test = Player.of("A. Lunin;Andriy Lunin;2/11/1999;20;190.5;79.8;GK;Ukraine;76;88;10500000;24000;Right");
        assertEquals(new Player("A. Lunin", "Andriy Lunin", LocalDate.of(1999, 2, 11),
                20, 190.5, 79.8, List.of(GK), "Ukraine", 76,
                88, 10500000, 24000, Foot.RIGHT), test);
    }

//    @Test
//    void testParseLocalDate() {
//        LocalDate test = Player.parseDate("2/11/1999");
//        assertEquals(test, LocalDate.of(1999, 11, 2));
//    }
}
