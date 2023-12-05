package bg.sofia.uni.fmi.mjt.football;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FootballPlayerAnalyzerTest {
    static FootballPlayerAnalyzer test;
    static FootballPlayerAnalyzer emptyTest;

    @BeforeAll
    static void setUpTest() {
        test = new FootballPlayerAnalyzer(new StringReader("""
                name;full_name;birth_date;age;height_cm;weight_kgs;positions;nationality;overall_rating;potential;value_euro;wage_euro;preferred_foot
                P. Dybala;Paulo Bruno Exequiel Dybala;11/15/1993;25;152.4;74.8;CAM,RW;Argentina;89;94;89000000;205000;Left
                Fernandinho;Fernando Luiz Rosa;5/4/1985;33;152.4;67.1;CDM;Brazil;87;87;20500000;200000;Right
                G. Higuaín;Gonzalo Gerardo Higuaín;12/10/1987;31;185.42;88.9;ST;Argentina;87;87;48500000;205000;Right
                I. Rakitić;Ivan Rakitić;3/10/1988;30;182.88;78;CM,CDM;Croatia;87;87;46500000;260000;Right
                J. Vertonghen;Jan Vertonghen;4/24/1987;31;187.96;86.2;CB;Belgium;87;87;34000000;155000;Left
                D. Mertens;Dries Mertens;5/6/1987;31;170.18;60.8;CF,ST;Belgium;87;87;45000000;135000;Right
                """
        ));
        emptyTest = new FootballPlayerAnalyzer(new StringReader(""));
    }

//    @Test
//    void test() throws FileNotFoundException {
//        FootballPlayerAnalyzer t = new FootballPlayerAnalyzer(new FileReader("test\\fifa_players_clean.csv"));
//        assertTrue(true);
//    }

    @Test
    void testGetAllPlayersEmptyList() {
        assertEquals(emptyTest.getAllPlayers(), List.of());
    }

    @Test
    void testGetAllNationalities() {
        Set<String> nations = test.getAllNationalities();
        assertTrue(nations.contains("Argentina") && nations.contains("Belgium") && nations.contains("Croatia")
        && nations.contains("Brazil"));
    }

    @Test
    void testGetAllNationalitiesEmpty() {
        assertEquals(emptyTest.getAllNationalities(), Set.of());
    }

    @Test
    void testGetHighestPaidPlayerByNationality() {
        assertEquals(test.getHighestPaidPlayerByNationality("Belgium"),
                Player.of("J. Vertonghen;Jan Vertonghen;4/24/1987;31;187.96;86.2;CB;Belgium;87;87;34000000;155000;Left"));
    }

    @Test
    void testGetHighestPaidPlayerByNationalityNull() {
        assertThrows(IllegalArgumentException.class ,() -> test.getHighestPaidPlayerByNationality(null));
    }

    @Test
    void testGroupByPosition() {
        assertTrue(test.groupByPosition().get(Position.CDM).containsAll(List.of(
                Player.of("Fernandinho;Fernando Luiz Rosa;5/4/1985;33;152.4;67.1;CDM;Brazil;87;87;20500000;200000;Right"),
                Player.of("I. Rakitić;Ivan Rakitić;3/10/1988;30;182.88;78;CM,CDM;Croatia;87;87;46500000;260000;Right")
        )));
    }

    @Test
    void testGroupByPositionEmpty() {
        assertFalse(emptyTest.groupByPosition().containsKey(Position.CDM));
    }

    @Test
    void testGetPlayersByFullNameKeyword() {
        assertEquals(test.getPlayersByFullNameKeyword("Paulo Bruno Exequiel Dybala"),
                Set.of(Player.of("P. Dybala;Paulo Bruno Exequiel Dybala;11/15/1993;25;152.4;74.8;CAM,RW;Argentina;89;94;89000000;205000;Left")));
    }

    @Test
    void testGetPlayersByFullNameKeywordNull() {
        assertThrows(IllegalArgumentException.class, () -> test.getPlayersByFullNameKeyword(null));
    }

    @Test
    void testGetTopProspectPlayerForPositionInBudget() {
        assertEquals(test.getTopProspectPlayerForPositionInBudget(Position.RW,890000000).get(),
                Player.of("P. Dybala;Paulo Bruno Exequiel Dybala;11/15/1993;25;152.4;74.8;CAM,RW;Argentina;89;94;89000000;205000;Left"));
    }

    @Test
    void testGetTopProspectPlayerForPositionInBudgetException() {
        assertThrows(IllegalArgumentException.class ,() -> test.getTopProspectPlayerForPositionInBudget(null, 10));
        assertThrows(IllegalArgumentException.class ,() -> test.getTopProspectPlayerForPositionInBudget(Position.GK, -1));
    }

    @Test
    void testGetSimilarPlayersNull() {
        assertThrows(IllegalArgumentException.class, () -> test.getSimilarPlayers(null));
    }

    @Test
    void testGetSimilarPlayers() {
        assertEquals(test.getSimilarPlayers(Player.of("P.;Paulo;11/15/1990;28;152.4;74.8;CAM,RW;Argentina;87;94;89000000;205000;Left")),
                Set.of(Player.of("P. Dybala;Paulo Bruno Exequiel Dybala;11/15/1993;25;152.4;74.8;CAM,RW;Argentina;89;94;89000000;205000;Left")));
    }
}
