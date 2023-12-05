package bg.sofia.uni.fmi.mjt.csvprocessor.table;

import bg.sofia.uni.fmi.mjt.csvprocessor.exceptions.CsvDataNotCorrectException;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.column.BaseColumn;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.column.Column;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseTable implements Table {
    Collection<String> headers;
    int length;
    Map<String, Column> columns;
    {
        headers = new ArrayList<>();
        length = 0;
        columns = new HashMap<>();
    }

    @Override
    public void addData(String[] data) throws CsvDataNotCorrectException {
        if (data == null) {
            throw new IllegalArgumentException("The data is null!");
        }
        if (headers.isEmpty()) {
            headers.addAll(List.of(data));
            if (Set.copyOf(headers).size() != headers.size()) {
                throw new CsvDataNotCorrectException();
            }
            for (String head : headers) {
                columns.put(head, new BaseColumn());
            }
        } else if (data.length != headers.size()) {
            throw new CsvDataNotCorrectException();
        } else {
            Iterator<String> iterHeader = headers.iterator();
            String curr;
            for (int i = 0; i < headers.size(); i++) {
                curr = iterHeader.next();
                if (columns.get(curr).getData().contains(data[i])) {
                    throw new CsvDataNotCorrectException();
                }
                columns.get(curr).addData(data[i]);
            }
        }
        length++;
    }

    @Override
    public Collection<String> getColumnNames() {
        return List.copyOf(headers);
    }

    @Override
    public Collection<String> getColumnData(String column) {
        if (column == null) {
            throw new IllegalArgumentException("null isn't in the column data!");
        } else if (column.isBlank()) {
            throw new IllegalArgumentException("blank string isn't in the column data!");
        } else if (!columns.containsKey(column)) {
            throw new IllegalArgumentException(column + " isn't in the column data!");
        }
        return List.copyOf(columns.get(column).getData());
    }

    @Override
    public int getRowsCount() {
        return length;
    }
}
