package sandy.sqlcmd.model;

import java.util.ArrayList;
import java.util.Iterator;

public class DataSet {

    private final ArrayList<ArrayList<String>> table;
    private final ArrayList<String> text;

    public DataSet() {
        text = new ArrayList<>();
        table = new ArrayList<>();
    }

    public DataSet(String srt) {
        this();
        addString(srt);
    }

    @Override
    public boolean equals(Object data){

        if( null == data || data.getClass() != this.getClass()){
            return false;
        }

        Iterator iteratorThis = this.iteratorText();
        Iterator iteratorData = ((DataSet) data).iteratorText();

        while (iteratorData.hasNext() && iteratorThis.hasNext() ) {
            if( !iteratorData.next().equals(iteratorThis.next())){
                return false;
            }
        }

        if( iteratorData.hasNext() || iteratorThis.hasNext()){
            return false;
        }

        if( this.quantityRows() != ((DataSet) data).quantityRows()){
            return false;
        }

        int quantityRows = this.quantityRows();

        for (int i = 0; i < quantityRows; i++) {

            int quantityFields = this.quantityFieldsInRow(i);
            if( quantityFields != ((DataSet) data).quantityFieldsInRow(i)){
                return false;
            }

            for(int j = 0; j < quantityFields; j ++){
                if( !this.getField( i, j ).equals(((DataSet) data).getField( i, j ))){
                    return false;
                }
            }
        }

        return true;
    }

    public Iterator<String> iteratorText() {
        return text.iterator();
    }

    public int addRow() {
        table.add(new ArrayList<>());
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

    public void addField(int indexRow, String field) {
        if (existingIndexRow(indexRow)) {
            table.get(indexRow).add(field);
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
