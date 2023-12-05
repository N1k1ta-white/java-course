package bg.sofia.uni.fmi.mjt.csvprocessor.table;

import bg.sofia.uni.fmi.mjt.csvprocessor.exceptions.CsvDataNotCorrectException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TableTest {
    static Table test;

    @Test
    @BeforeAll
    static void setUpTestsCast() throws CsvDataNotCorrectException {
        test = new BaseTable();
        test.addData(new String[]{"head1", "head2", "head3"});
        test.addData(new String[]{"cont1", "cont2", "cont3"});
        test.addData(new String[]{"cont1.1", "cont2.1", "cont3.1"});
    }

    @Test
    void testAddDataCsvDataNotCorrect() {
        assertThrows(CsvDataNotCorrectException.class ,() -> test.addData(new String[]{"1", "2", "3", "4"}));
        assertThrows(CsvDataNotCorrectException.class ,() -> test.addData(new String[]{"1", "4"}));
        assertThrows(CsvDataNotCorrectException.class, () -> test.addData(new String[]{"cont1.1", "cont2.1", "cont3.1"}));
    }

    @Test
    void testAddDataNullArgument() {
        assertThrows(IllegalArgumentException.class ,() -> test.addData(null));
    }

    @Test
    void testAddDataIsWorkingCorrect() {
        assertIterableEquals(test.getColumnData("head1"), List.of("cont1", "cont1.1"));
    }

    @Test
    void testGetColumnNamesWorking() {
        assertIterableEquals(List.of("head1", "head2", "head3"), test.getColumnNames());
    }

    @Test
    void testGetColumnNamesCsvException() {
        BaseTable test2 = new BaseTable();
        assertThrows(CsvDataNotCorrectException.class ,() -> test2.addData(new String[]{"head1", "head2", "head3", "head3"}));
    }

    @Test
    void testGetColumnException() {
        assertThrows(IllegalArgumentException.class ,() -> test.getColumnData("f"));
        assertThrows(IllegalArgumentException.class ,() -> test.getColumnData(""));
        assertThrows(IllegalArgumentException.class ,() -> test.getColumnData(null));
    }

    @Test
    void testGetRowsCountIsWorking() {
        assertEquals(3, test.getRowsCount());
    }
}
