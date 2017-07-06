package sandy.sqlcmd.model;

import java.util.ArrayList;
import java.util.Iterator;

public class DataSet {
    private ArrayList<ArrayList<String>> table;
    private ArrayList<String> text;

    public int getSizeTable() {
        return table.size();
    }

    public DataSet() {
        text = new ArrayList<>();
        table = new ArrayList<>();
    }

    public DataSet(String srt) {
        text = new ArrayList<>();
        table = new ArrayList<>();
        addString(srt);
    }

    public Iterator<String> iteratorText() {
        return text.iterator();
    }

    public int addRow(String str) {
        int i = addRow();
        addField(i, str);
        return table.size() - 1;
    }

    public void addString(String str) {
        text.add(str);
    }

    public void addString(String[] strings) {
        for (String s : strings) {
            addString(s);
        }
    }

    public int addRow() {
        table.add(new ArrayList<String>());
        return table.size() - 1;
    }

    public void addRows(String[] strings) {
        for (String s : strings) {
            addRow(s);
        }
    }

    public int addRow(ArrayList<String> row) {
        table.add(row);
        return table.size() - 1;
    }

    public void addField(int indexRow, String field) {
        if (existingIndexRow(indexRow)) {
            table.get(indexRow).add(field);
        } else {
        }
    }

    public String getField(int i, int j) {
        if (existingIndexRow(i) && j < table.get(i).size() && j >= 0) {
            return table.get(i).get(j);
        } else {
            return null;
        }
    }

    public int quantityRows() {
        return table.size();
    }

    public int QuantityStrings() {
        return text.size();
    }

    public String getString(int i) {
        if (i >= 0 && i < text.size()) {
            return text.get(i);
        } else {
            return null;
        }
    }

    public int quantityFieldsInRow(int i) {
        if (existingIndexRow(i)) {
            return table.get(i).size();
        } else {
            return 0;
        }
    }

    private boolean existingIndexRow(int i) {
        return i < table.size() && i >= 0;
    }
}
