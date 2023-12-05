package bg.sofia.uni.fmi.mjt.csvprocessor.table.printer;

import bg.sofia.uni.fmi.mjt.csvprocessor.service.Constants;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class MarkdownTablePrinter implements TablePrinter {

    private String getStrNTime(String str, int time) {
        String res = "";
        for (int i = 0; i < time; i++) {
            res = res.concat(str);
        }
        return res;
    }

    private String getAlignment(ColumnAlignment alignment, int maxLength) {
        return switch (alignment) {
            case RIGHT -> "\s".concat(getStrNTime("-", maxLength - 1).concat(":\s|"));
            case LEFT -> "\s:".concat(getStrNTime("-", maxLength - 1).concat("\s|"));
            case CENTER -> "\s:".concat(getStrNTime("-", maxLength - 2).concat(":\s|"));
            case NOALIGNMENT -> "\s".concat(getStrNTime("-", maxLength).concat("\s|"));
        };
    }

    private String getWord(String word, int maxLength) {
        return "\s".concat(word + getStrNTime("\s", maxLength - word.length()) + "\s|");
    }

    private int getMaxLength(Iterator<String> iterCol) {
        int maxLength = Constants.getMinLength();
        while (iterCol.hasNext()) {
            int temp = iterCol.next().length();
            if (temp > maxLength) {
                maxLength = temp;
            }
        }
        return maxLength;
    }

    @Override
    public Collection<String> printTable(Table table, ColumnAlignment... alignments) {
        ArrayList<String> print = new ArrayList<>();
        int length = table.getRowsCount();
        Iterator<ColumnAlignment> alignmentIterator = Arrays.asList(alignments).iterator();
        for (int i = 0; i < length + 1; i++) {
            print.add("|");
        }
        Collection<String> headers = table.getColumnNames();
        for (String header : headers) {
            Collection<String> column = table.getColumnData(header);
            Iterator<String> iterCol = column.iterator();
            int maxLength = Math.max(header.length(), getMaxLength(iterCol));
            iterCol = column.iterator();
            print.set(0, print.get(0).concat(getWord(header, maxLength)));
            print.set(1, print.get(1).concat(getAlignment(
                    (alignmentIterator.hasNext() ? alignmentIterator.next() : ColumnAlignment.NOALIGNMENT)
                    , maxLength)));
            for (int i = 2; i < length + 1; i++) {
                print.set(i, print.get(i).concat(getWord(iterCol.next(), maxLength)));
            }
        }
        return print;
    }
}
