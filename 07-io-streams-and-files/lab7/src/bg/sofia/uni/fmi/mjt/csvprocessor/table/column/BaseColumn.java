package bg.sofia.uni.fmi.mjt.csvprocessor.table.column;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class BaseColumn implements Column {
    private Collection<String> content;

    public BaseColumn() {
        this(new LinkedHashSet<>());
    }

    public BaseColumn(Set<String> values) {
        content = values;
    }

    @Override
    public void addData(String data) {
        if (data == null) {
            throw new IllegalArgumentException("The String is null!");
        } else if (data.isBlank()) {
            throw new IllegalArgumentException("The String is blank!");
        }
        content.add(data);
    }

    @Override
    public Collection<String> getData() {
        return List.copyOf(content);
    }

}