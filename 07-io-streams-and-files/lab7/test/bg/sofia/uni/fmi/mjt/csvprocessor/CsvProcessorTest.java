package bg.sofia.uni.fmi.mjt.csvprocessor;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CsvProcessorTest {
    private static String read =
            """
            head1,head2,head3
            test1,test2,test3
            test1.1,test2.1,test3.1""";

    @Test
    void testReadCsv() {
        CsvProcessor test = new CsvProcessor();
        try (var file = new StringReader(read)) {
            test.readCsv(file, ",");
        } catch (Exception ex) {
            fail();
        }
        assertEquals(3, test.getTableRowsCount());
    }

    @Test
    void testWriteCsv() {
        CsvProcessor test = new CsvProcessor();
        try (var file = new StringReader(read)) {
            test.readCsv(file, ",");
        } catch (Exception ex) {
            fail();
        }
        try (var file = new StringWriter()) {
            test.writeTable(file);
            assertEquals(file.toString(), "|\shead1\s\s\s|\shead2\s\s\s|\shead3\s\s\s|" + System.lineSeparator() +
                    "|\s-------\s|\s-------\s|\s-------\s|" + System.lineSeparator() +
                    "|\stest1\s\s\s|\stest2\s\s\s|\stest3\s\s\s|" + System.lineSeparator() +
                    "|\stest1.1\s|\stest2.1\s|\stest3.1\s|");
        } catch (Exception ex) {
            fail();
        }
    }
}
