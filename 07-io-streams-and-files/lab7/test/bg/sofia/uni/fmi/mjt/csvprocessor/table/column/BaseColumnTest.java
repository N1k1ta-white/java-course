package bg.sofia.uni.fmi.mjt.csvprocessor.table.column;

import bg.sofia.uni.fmi.mjt.csvprocessor.table.printer.ColumnAlignment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BaseColumnTest {
    static Column test;

    @Test
    @BeforeAll
    static void setUpTestCase() {
        test = new BaseColumn();
    }

    @Test
    void testAddData() {
        test.addData("Hi");
        test.addData("Hello");
        assertIterableEquals(test.getData(), List.of("Hi", "Hello"));
    }

    @Test
    void testAddDataExceptionIllegalParam() {
        assertThrows(IllegalArgumentException.class, () -> test.addData(null));
        assertThrows(IllegalArgumentException.class, () -> test.addData(""));
    }

    @Test
    void testColumnAlignment() {
        assertEquals(ColumnAlignment.NOALIGNMENT.getAlignmentCharactersCount(), 0);
    }
}
