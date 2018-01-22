package sandy.sqlcmd.view;
import org.springframework.stereotype.Component;
import sandy.sqlcmd.model.DataSet;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console implements View {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";

    @Override
    public String read() {
        try {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
    @Override
    public void write(String line)
    {
        if (null == line) return;
        System.out.println(line);
    }
    @Override
    public void write(DataSet data) {
        if (null == data) return;
        writeText(data);
        writeTable(data);
    }

    private void writeText(DataSet data){
        Iterator iterator = data.iteratorText();
        while ( iterator.hasNext() ){
            System.out.println(iterator.next());
        }
    }
    private void writeTable(DataSet data){
        int quantityRows = data.quantityRows();
        if(quantityRows == 0) return;

        int quantityFields = data.quantityFieldsInRow(0);
        int[] maxWidthFields = getMaximumWidthsFields(data, quantityRows, quantityFields);

        drawBorderLine(maxWidthFields);
        drawLine(maxWidthFields, data,0);
        drawBorderLine(maxWidthFields);

        for(int i = 1; i < quantityRows; i++){
            drawLine(maxWidthFields, data,i);
            drawBorderLine(maxWidthFields);
        }
    }

    private int[] getMaximumWidthsFields(DataSet data,int quantityRows, int quantityFields) {
        int[] maxWidthFields = new int[quantityFields];

        for(int j = 0; j < quantityFields; j++){
            for(int i = 0; i < quantityRows; i++){
                if(maxWidthFields[j] < data.getField(i,j).length()){
                    maxWidthFields[j] = data.getField(i,j).length();
                }
            }
        }
        return maxWidthFields;
    }

    private void drawBorderLine(int[] maxWidthFields) {
        StringBuilder line = new StringBuilder("+");
        for (int maxWidthCurrentField : maxWidthFields) {
            line.append(getStringCharSet(maxWidthCurrentField + 2, "-"));
            line.append("+");
        }
        System.out.println(ANSI_PURPLE + line.toString() + ANSI_RESET);
    }

    private void drawLine(int[] maxWidthFields, DataSet data, int row) {
        StringBuilder line = new StringBuilder(ANSI_PURPLE);
        line.append("+");
        for(int index = 0; index < maxWidthFields.length; index++) {
            String field = data.getField(row,index);
            line.append(ANSI_BLUE);
            line.append(" ");
            line.append(field);
            line.append(ANSI_PURPLE);
            line.append(getStringCharSet(maxWidthFields[index] - field.length()+1, " "));
            line.append("+");
        }
        line.append(ANSI_RESET);
        System.out.println(line.toString());
    }

    private String getStringCharSet(int quantity, String part){
        StringBuilder stringCharSet = new StringBuilder();
        for (int i = 0; i < quantity; i++) {
            stringCharSet.append(part);
        }
        return stringCharSet.toString();
    }
}
