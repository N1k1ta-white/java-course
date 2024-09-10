package bg.sofia.uni.fmi.mjt.space;

import bg.sofia.uni.fmi.mjt.space.algorithm.Rijndael;
import bg.sofia.uni.fmi.mjt.space.exception.CipherException;
import bg.sofia.uni.fmi.mjt.space.exception.TimeFrameMismatchException;
import bg.sofia.uni.fmi.mjt.space.mission.Mission;
import bg.sofia.uni.fmi.mjt.space.mission.MissionStatus;
import bg.sofia.uni.fmi.mjt.space.rocket.Rocket;
import bg.sofia.uni.fmi.mjt.space.rocket.RocketStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static bg.sofia.uni.fmi.mjt.space.algorithm.RijndaelTest.getKey;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MJTSpaceScannerTest {
    private static MJTSpaceScanner scanner;
    private static SecretKey key;
    private static MJTSpaceScanner emptyScan;

    @BeforeAll
    static void init() throws NoSuchAlgorithmException {
        Reader rocketsSource = new StringReader("""
                296,Proton-M/DM-3,https://en.wikipedia.org/wiki/Proton-M,53.0 m
                242,Minotaur I,https://en.wikipedia.org/wiki/Minotaur_I,19.21 m
                46,Ariane 5 ECA,https://en.wikipedia.org/wiki/Ariane_5,53.1 m
                172,Falcon Heavy,https://en.wikipedia.org/wiki/Falcon_Heavy,70.0 m
                357,Soyuz ST-B/Fregat-MT,https://en.wikipedia.org/wiki/Soyuz-2#Soyuz-2.1b,46.2 m
                """);
        Reader missionSource = new StringReader("""
                129,SpaceX,"LC-39A, Kennedy Space Center, Florida, USA","Tue Jun 25, 2019",Falcon Heavy | STP-2,StatusActive,"90.0 ",Success
                540,Arianespace,"ELA-3, Guiana Space Centre, French Guiana, France","Thu Sep 11, 2014","Ariane 5 ECA | MEASAT 3b, Optus 10",StatusActive,"200.0 ",Success
                541,SpaceX,"SLC-40, Cape Canaveral AFS, Florida, USA","Sun Sep 07, 2014",Falcon 9 v1.1 | AsiaSat 6,StatusRetired,"56.5 ",Success
                542,Arianespace,"ELS, Guiana Space Centre, French Guiana, France","Fri Aug 22, 2014",Soyuz ST-B/Fregat-MT | Galileo FOC FM01-FM02,StatusActive,,Partial Failure
                601,VKS RF,"Site 81/24, Baikonur Cosmodrome, Kazakhstan","Tue Jul 02, 2013","Proton-M/DM-3 | Cosmos 2488, 2489 & 2490",StatusActive,"65.0 ",Failure
                698,VKS RF,"Site 133/3, Plesetsk Cosmodrome, Russia","Tue Feb 01, 2011",Rokot/Briz KM | Cosmos 2470,StatusRetired,"41.8 ",Partial Failure
                889,Northrop,"LP-0B, Wallops Flight Facility, Virginia, USA","Sat Dec 16, 2006",Minotaur I | TacSat-2 & GeneSat-1,StatusActive,"40.0 ",Success
                """);
        key = getKey();
        scanner = new MJTSpaceScanner(missionSource, rocketsSource, key);
        StringReader t1 = new StringReader("");
        StringReader t2 = new StringReader("");
        emptyScan = new MJTSpaceScanner(t1, t2, key);
    }

    @Test
    void testGetAllMissions() {
        Reader rocketsSourceTest = new StringReader("""
                296,Proton-M/DM-3,https://en.wikipedia.org/wiki/Proton-M,53.0 m
                357,Soyuz ST-B/Fregat-MT,https://en.wikipedia.org/wiki/Soyuz-2#Soyuz-2.1b,46.2 m
                242,Minotaur I,https://en.wikipedia.org/wiki/Minotaur_I,19.21 m
                257,Mu-III S2,https://en.wikipedia.org/wiki/Mu_(rocket_family),
                258,Mu-IV S,https://en.wikipedia.org/wiki/Mu_(rocket_family),23.6 m
                """);
        Reader missionSourceTest = new StringReader("""
                540,Arianespace,"ELA-3, Guiana Space Centre, French Guiana, France","Thu Sep 11, 2014","Ariane 5 ECA | MEASAT 3b, Optus 10",StatusActive,"200.0 ",Success
                601,VKS RF,"Site 81/24, Baikonur Cosmodrome, Kazakhstan","Tue Jul 02, 2013","Proton-M/DM-3 | Cosmos 2488, 2489 & 2490",StatusActive,"65.0 ",Failure
                541,SpaceX,"SLC-40, Cape Canaveral AFS, Florida, USA","Sun Sep 07, 2014",Falcon 9 v1.1 | AsiaSat 6,StatusRetired,"56.5 ",Success
                542,Arianespace,"ELS, Guiana Space Centre, French Guiana, France","Fri Aug 22, 2014",Soyuz ST-B/Fregat-MT | Galileo FOC FM01-FM02,StatusActive,,Partial Failure
                698,VKS RF,"Site 133/3, Plesetsk Cosmodrome, Russia","Tue Feb 01, 2011",Rokot/Briz KM | Cosmos 2470,StatusRetired,"41.8 ",Partial Failure
                889,Northrop,"LP-0B, Wallops Flight Facility, Virginia, USA","Sat Dec 16, 2006",Minotaur I | TacSat-2 & GeneSat-1,StatusActive,"40.0 ",Success
                """);
        MJTSpaceScanner test = new MJTSpaceScanner(missionSourceTest, rocketsSourceTest, key);
        Mission obj1 = Mission.of("540,Arianespace,\"ELA-3, Guiana Space Centre, French Guiana, France\",\"Thu Sep 11, 2014\",\"Ariane 5 ECA | MEASAT 3b, Optus 10\",StatusActive,\"200.0 \",Success");
        Mission obj2 = Mission.of("601,VKS RF,\"Site 81/24, Baikonur Cosmodrome, Kazakhstan\",\"Tue Jul 02, 2013\",\"Proton-M/DM-3 | Cosmos 2488, 2489 & 2490\",StatusActive,\"65.0 \",Failure");
        Mission obj3 = Mission.of("541,SpaceX,\"SLC-40, Cape Canaveral AFS, Florida, USA\",\"Sun Sep 07, 2014\",Falcon 9 v1.1 | AsiaSat 6,StatusRetired,\"56.5 \",Success");
        Mission obj4 = Mission.of("542,Arianespace,\"ELS, Guiana Space Centre, French Guiana, France\",\"Fri Aug 22, 2014\",Soyuz ST-B/Fregat-MT | Galileo FOC FM01-FM02,StatusActive,,Partial Failure");
        Mission obj5 = Mission.of("698,VKS RF,\"Site 133/3, Plesetsk Cosmodrome, Russia\",\"Tue Feb 01, 2011\",Rokot/Briz KM | Cosmos 2470,StatusRetired,\"41.8 \",Partial Failure");
        Mission obj6 = Mission.of("889,Northrop,\"LP-0B, Wallops Flight Facility, Virginia, USA\",\"Sat Dec 16, 2006\",Minotaur I | TacSat-2 & GeneSat-1,StatusActive,\"40.0 \",Success");
        assertIterableEquals(List.of(obj1, obj2, obj3, obj4, obj5, obj6), test.getAllMissions());
        assertTrue(test.getAllMissions(MissionStatus.FAILURE).contains(obj2)
                && test.getAllMissions(MissionStatus.FAILURE).size() == 1);
        assertTrue(test.getAllMissions(MissionStatus.SUCCESS).containsAll(List.of(obj1, obj3, obj6))
                && test.getAllMissions(MissionStatus.SUCCESS).size() == 3);
        assertTrue(test.getAllMissions(MissionStatus.PARTIAL_FAILURE).containsAll(List.of(obj4, obj5))
                && test.getAllMissions(MissionStatus.PARTIAL_FAILURE).size() == 2);
        assertIterableEquals(List.of(), test.getAllMissions(MissionStatus.PRELAUNCH_FAILURE));
    }

    @Test
    void testGetAllMissionsThrow() {
        assertThrows(IllegalArgumentException.class, () -> scanner.getAllMissions(null));
    }

    @Test
    void testGetCompanyWithMostSuccessfulMissions() {
        assertEquals(scanner.getCompanyWithMostSuccessfulMissions(LocalDate.of(2014, 9, 10),
                LocalDate.of(2014, 9, 12)), "Arianespace");
        assertEquals(scanner.getCompanyWithMostSuccessfulMissions(LocalDate.of(2000, 1, 1),
                LocalDate.of(2022, 12, 12)), "SpaceX");
    }

    @Test
    void testGetCompanyWithMostSuccessfulMissionsThrow() {
        assertThrows(TimeFrameMismatchException.class ,() -> scanner.getCompanyWithMostSuccessfulMissions(LocalDate.of(2022, 12, 12),
                LocalDate.of(2000, 1, 1)));
        assertThrows(IllegalArgumentException.class ,() -> scanner.getCompanyWithMostSuccessfulMissions(null,
                LocalDate.of(2022, 12, 12)));
        assertThrows(IllegalArgumentException.class ,() -> scanner.getCompanyWithMostSuccessfulMissions(LocalDate.of(2022, 12, 12),
                null));
    }

    @Test
    void testGetMissionsPerCountry() {
        Map<String, Collection<Mission>> test = scanner.getMissionsPerCountry();
        assertIterableEquals(test.get("France"), List.of(Mission.of("540,Arianespace,\"ELA-3, Guiana Space Centre, French Guiana, France\",\"Thu Sep 11, 2014\",\"Ariane 5 ECA | MEASAT 3b, Optus 10\",StatusActive,\"200.0 \",Success"),
                Mission.of("542,Arianespace,\"ELS, Guiana Space Centre, French Guiana, France\",\"Fri Aug 22, 2014\",Soyuz ST-B/Fregat-MT | Galileo FOC FM01-FM02,StatusActive,,Partial Failure")));
        assertIterableEquals(test.get("Kazakhstan"), List.of(Mission.of("601,VKS RF,\"Site 81/24, Baikonur Cosmodrome, Kazakhstan\",\"Tue Jul 02, 2013\",\"Proton-M/DM-3 | Cosmos 2488, 2489 & 2490\",StatusActive,\"65.0 \",Failure")));
        assertTrue(emptyScan.getAllMissions().isEmpty());
    }

    @Test
    void testGetTopNLeastExpensiveMissions() {
        assertIterableEquals(scanner.getTopNLeastExpensiveMissions(3, MissionStatus.SUCCESS, RocketStatus.STATUS_ACTIVE),
                List.of(Mission.of("889,Northrop,\"LP-0B, Wallops Flight Facility, Virginia, USA\",\"Sat Dec 16, 2006\",Minotaur I | TacSat-2 & GeneSat-1,StatusActive,\"40.0 \",Success"),
                        Mission.of("129,SpaceX,\"LC-39A, Kennedy Space Center, Florida, USA\",\"Tue Jun 25, 2019\",Falcon Heavy | STP-2,StatusActive,\"90.0 \",Success"),
                        Mission.of("540,Arianespace,\"ELA-3, Guiana Space Centre, French Guiana, France\",\"Thu Sep 11, 2014\",\"Ariane 5 ECA | MEASAT 3b, Optus 10\",StatusActive,\"200.0 \",Success")));
    }

    @Test
    void testGetTopNLeastExpensiveMissionsThrow() {
        assertThrows(IllegalArgumentException.class, () -> scanner.getTopNLeastExpensiveMissions(0, MissionStatus.SUCCESS, RocketStatus.STATUS_ACTIVE));
        assertThrows(IllegalArgumentException.class, () -> scanner.getTopNLeastExpensiveMissions(3, MissionStatus.SUCCESS, null));
        assertThrows(IllegalArgumentException.class, () -> scanner.getTopNLeastExpensiveMissions(3, null, RocketStatus.STATUS_ACTIVE));
    }

    @Test
    void testGetMostDesiredLocationForMissionsPerCompany() {
        StringReader reader = new StringReader("""
                25,SpaceX,"LC-39A, Kennedy Space Center, Florida, USA","Sat May 30, 2020",Falcon 9 Block 5 | SpaceX Demo-2,StatusActive,"50.0 ",Success
                898,Boeing,"SLC-17A, Cape Canaveral AFS, Florida, USA","Thu Oct 26, 2006",Delta II 7925-10L | STEREO,StatusRetired,,Success
                929,SpaceX,"Omelek Island, Ronald Reagan Ballistic Missile Defense Test Site, Marshall Islands, USA","Fri Mar 24, 2006",Falcon 1 | FalconSat-2,StatusRetired,"7.0 ",Failure
                0,SpaceX,"LC-39A, Kennedy Space Center, Florida, USA","Fri Aug 07, 2020",Falcon 9 Block 5 | Starlink V1 L9 & BlackSky,StatusActive,"50.0 ",Success
                895,Boeing,"SLC-6, Vandenberg AFB, California, USA","Sat Nov 04, 2006",Delta IV Medium | DMSP F17,StatusRetired,"133.0 ",Success
                894,Boeing,"SLC-17A, Cape Canaveral AFS, Florida, USA","Fri Nov 17, 2006",Delta II 7925 | GPS IIRM-3,StatusRetired,,Success
                601,VKS RF,"Site 81/24, Baikonur Cosmodrome, Kazakhstan","Tue Jul 02, 2013","Proton-M/DM-3 | Cosmos 2488, 2489 & 2490",StatusActive,"65.0 ",Failure
                """);
        StringReader reader1 = new StringReader("");
        MJTSpaceScanner test = new MJTSpaceScanner(reader, reader1, key);
        Map<String, String> res = test.getMostDesiredLocationForMissionsPerCompany();
        assertEquals(res.get("SpaceX"), "LC-39A, Kennedy Space Center, Florida, USA");
        assertEquals(res.get("Boeing"), "SLC-17A, Cape Canaveral AFS, Florida, USA");
    }

    @Test
    void testGetLocationWithMostSuccessfulMissionsPerCompany() {
        StringReader reader = new StringReader("""
                25,SpaceX,"LC-39A, Kennedy Space Center, Florida, USA","Sat May 30, 2020",Falcon 9 Block 5 | SpaceX Demo-2,StatusActive,"50.0 ",Failure
                898,Boeing,"SLC-17A, Cape Canaveral AFS, Florida, USA","Thu Oct 26, 2006",Delta II 7925-10L | STEREO,StatusRetired,,Success
                929,SpaceX,"Omelek Island, Ronald Reagan Ballistic Missile Defense Test Site, Marshall Islands, USA","Fri Mar 24, 2006",Falcon 1 | FalconSat-2,StatusRetired,"7.0 ",Success
                0,SpaceX,"LC-39A, Kennedy Space Center, Florida, USA","Fri Aug 07, 2020",Falcon 9 Block 5 | Starlink V1 L9 & BlackSky,StatusActive,"50.0 ",Failure
                895,Boeing,"SLC-6, Vandenberg AFB, California, USA","Sat Nov 04, 2006",Delta IV Medium | DMSP F17,StatusRetired,"133.0 ",Success
                894,Boeing,"SLC-17A, Cape Canaveral AFS, Florida, USA","Fri Nov 17, 2006",Delta II 7925 | GPS IIRM-3,StatusRetired,,Success
                601,VKS RF,"Site 81/24, Baikonur Cosmodrome, Kazakhstan","Tue Jul 02, 2013","Proton-M/DM-3 | Cosmos 2488, 2489 & 2490",StatusActive,"65.0 ",Failure
                """);
        StringReader reader1 = new StringReader("");
        MJTSpaceScanner test = new MJTSpaceScanner(reader, reader1, key);
        Map<String, String> res = test.getLocationWithMostSuccessfulMissionsPerCompany(LocalDate.of(2000, 1, 1),
                LocalDate.of(2022, 12, 12));
        assertEquals(res.get("SpaceX"), "Omelek Island, Ronald Reagan Ballistic Missile Defense Test Site, Marshall Islands, USA");
        assertEquals(res.get("Boeing"), "SLC-17A, Cape Canaveral AFS, Florida, USA");
    }

    @Test
    void testGetAllRockets() {
        assertEquals(5, scanner.getAllRockets().size());
    }

    @Test
    void testGetTopNTallestRockets() {
        assertIterableEquals(scanner.getTopNTallestRockets(2),
                List.of(Rocket.of("172,Falcon Heavy,https://en.wikipedia.org/wiki/Falcon_Heavy,70.0 m"),
                        Rocket.of("46,Ariane 5 ECA,https://en.wikipedia.org/wiki/Ariane_5,53.1 m")));
    }

    @Test
    void testGetTopNTallestRocketsThrow() {
        assertThrows(IllegalArgumentException.class, () -> scanner.getTopNTallestRockets(-1));
    }

    @Test
    void testGetWikiPageForRocket() {
        Map<String, Optional<String>> test = scanner.getWikiPageForRocket();
        assertEquals(test.get("Minotaur I").get(), "https://en.wikipedia.org/wiki/Minotaur_I");
        assertTrue(emptyScan.getWikiPageForRocket().isEmpty());
    }

    @Test
    void testGetWikiPagesForRocketsUsedInMostExpensiveMissions() {
        assertIterableEquals(scanner.getWikiPagesForRocketsUsedInMostExpensiveMissions(2,
                MissionStatus.SUCCESS, RocketStatus.STATUS_ACTIVE), List.of(
                        "https://en.wikipedia.org/wiki/Ariane_5",
                        "https://en.wikipedia.org/wiki/Falcon_Heavy"
        ));
    }

    @Test
    void testGetWikiPagesForRocketsUsedInMostExpensiveMissionsThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                scanner.getWikiPagesForRocketsUsedInMostExpensiveMissions(0, MissionStatus.FAILURE,
                        RocketStatus.STATUS_RETIRED));
        assertThrows(IllegalArgumentException.class, () ->
                scanner.getWikiPagesForRocketsUsedInMostExpensiveMissions(2, null,
                        RocketStatus.STATUS_RETIRED));
        assertThrows(IllegalArgumentException.class, () ->
                scanner.getWikiPagesForRocketsUsedInMostExpensiveMissions(4, MissionStatus.FAILURE,
                        null));
    }

    @Test
    void testSaveMostReliableRocket() throws CipherException {
        StringReader readerMission = new StringReader("""
                410,IAI,"Pad 1, Palmachim Airbase, Israel","Tue Sep 13, 2016",Shavit-2 | Offer-11,StatusActive,,Success
                411,IAI,"Pad 1, Palmachim Airbase, Israel","Tue Sep 13, 2016",Shavit-2 | Ofek-11,StatusActive,,Success
                411,IAI,"Pad 1, Palmachim Airbase, Israel","Tue Sep 13, 2016",Shavit-2 | Ofek-11,StatusActive,,Success
                412,IAI,"Pad 1, Palmachim Airbase, Israel","Tue Sep 13, 2016",Shavit-2 | Ofek-11,StatusActive,,Failure
                377,JAXA,"Uchinoura Space Center, Japan","Sat Jan 14, 2017",SS-520 | TRICOM-1,StatusActive,,Success
                378,JAXA,"Uchinoura Space Center, Japan","Sat Jan 14, 2017",SS-520 | TRICOM-1,StatusActive,,Success
                378,JAXA,"Uchinoura Space Center, Japan","Sat Jan 14, 2017",SS-520 | TRICOM-1,StatusActive,,Failure
                378,JAXA,"Uchinoura Space Center, Japan","Sat Jan 14, 2017",SS-520 | TRICOM-1,StatusActive,,Failure
                328,MHI,"LA-Y1, Tanegashima Space Center, Japan","Sat Aug 19, 2017",H-IIA 204 | QZS-3,StatusActive,,Success
                328,MHI,"LA-Y1, Tanegashima Space Center, Japan","Sat Aug 19, 2017",H-IIA 204 | QZS-3,StatusActive,,Failure
                328,MHI,"LA-Y1, Tanegashima Space Center, Japan","Sat Aug 19, 2017",H-IIA 204 | QZS-3,StatusActive,,Failure
                329,MHI,"LA-Y1, Tanegashima Space Center, Japan","Sat Aug 19, 2017",H-IIA 204 | QZS-3,StatusActive,,Failure
                """);
        StringReader readerRockets = new StringReader("""
                330,Shavit-2,https://en.wikipedia.org/wiki/Shavit,22.0 m
                369,SS-520,https://en.wikipedia.org/wiki/S-Series_(rocket_family)#SS-520,9.54 m
                185,H-IIA 204,https://en.wikipedia.org/wiki/H-IIA,
                """);
        MJTSpaceScanner test = new MJTSpaceScanner(readerMission, readerRockets, key);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        test.saveMostReliableRocket(outputStream, LocalDate.of(2000, 1, 1),
                LocalDate.of(2022, 12, 12));
        Rijndael decode = new Rijndael(key);
        InputStream input = new ByteArrayInputStream(outputStream.toByteArray());
        outputStream.reset();
        decode.decrypt(input, outputStream);
        assertEquals(outputStream.toString(), "Shavit-2");
    }
}
