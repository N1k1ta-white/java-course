package bg.sofia.uni.fmi.mjt.csvprocessor;

import bg.sofia.uni.fmi.mjt.csvprocessor.exceptions.CsvDataNotCorrectException;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.BaseTable;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.Table;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.printer.ColumnAlignment;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.printer.MarkdownTablePrinter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Iterator;

public class CsvProcessor implements CsvProcessorAPI {
    Table table;
    public CsvProcessor() {
        this(new BaseTable());
    }

    public CsvProcessor(Table table) {
        this.table = table;
    }

    @Override
    public void readCsv(Reader reader, String delimiter) throws CsvDataNotCorrectException {
        try (var bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                table.addData(line.split("\\Q" + delimiter + "\\E"));
            }
        } catch (IOException ex) {
            throw new UncheckedIOException("A problem occurred while writing to a file", ex);
        }
    }

    @Override
    public void writeTable(Writer writer, ColumnAlignment... alignments) {
        try (var bufferedWriter = new BufferedWriter(writer)) {
            MarkdownTablePrinter printer = new MarkdownTablePrinter();
            Iterator<String> iter = printer.printTable(table, alignments).iterator();
            while (iter.hasNext()) {
                bufferedWriter.write(iter.next());
                if (!iter.hasNext()) {
                    break;
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException ex) {
            throw new UncheckedIOException("A problem occurred while writing to a file", ex);
        }
    }

    public int getTableRowsCount() {
        return table.getRowsCount();
    }
}
