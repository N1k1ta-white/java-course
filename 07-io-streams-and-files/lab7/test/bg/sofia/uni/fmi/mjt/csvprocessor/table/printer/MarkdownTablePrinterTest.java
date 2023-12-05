package bg.sofia.uni.fmi.mjt.csvprocessor.table.printer;

import bg.sofia.uni.fmi.mjt.csvprocessor.exceptions.CsvDataNotCorrectException;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.BaseTable;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.Table;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class MarkdownTablePrinterTest {
    static Table test;
    static TablePrinter testPrinter;

    @Test
    @BeforeAll
    static void setUpTestsCast() throws CsvDataNotCorrectException {
        test = new BaseTable();
        testPrinter = new MarkdownTablePrinter();
        test.addData(new String[]{"head1", "head2", "head3"});
        test.addData(new String[]{"cont1", "cont2", "cont3"});
        test.addData(new String[]{"cont1.1", "cont2.1", "cont3.1"});
    }

    @Test
    void testPrintTabletIsWorkingCorrect() {
        Collection<String> t = testPrinter.printTable(test);
        assertIterableEquals(testPrinter.printTable(test), List.of(
                "|\shead1\s\s\s|\shead2\s\s\s|\shead3\s\s\s|",
                "|\s-------\s|\s-------\s|\s-------\s|",
                "|\scont1\s\s\s|\scont2\s\s\s|\scont3\s\s\s|",
                "|\scont1.1\s|\scont2.1\s|\scont3.1\s|"));
    }

    @Test
    void testPrintTabletAlignmentsChecking() {
        assertIterableEquals(testPrinter.printTable(test, ColumnAlignment.CENTER,
                ColumnAlignment.RIGHT, ColumnAlignment.LEFT), List.of(
                "|\shead1\s\s\s|\shead2\s\s\s|\shead3\s\s\s|",
                "|\s:-----:\s|\s------:\s|\s:------\s|",
                "|\scont1\s\s\s|\scont2\s\s\s|\scont3\s\s\s|",
                "|\scont1.1\s|\scont2.1\s|\scont3.1\s|"));
    }
}
