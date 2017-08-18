package sandy.sqlcmd.view;
import sandy.sqlcmd.model.DataSet;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console implements View {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";

    private boolean colorPrint = false;

    public Console ( boolean colorPrint) {
        this.colorPrint = colorPrint;
    }

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
        printPartOfBorder("+");
        for (int index = 0; index < maxWidthFields.length; index++) {
            printCharSet(maxWidthFields[index] + 2, "-");
            printPartOfBorder("+");
        }
        System.out.println("");
    }

    private void drawLine(int[] maxWidthFields, DataSet data, int row) {
        printPartOfBorder("+");
        for(int index = 0; index < maxWidthFields.length; index++) {
            String field = data.getField(row,index);
            printValField(field);
            printCharSet(maxWidthFields[index] - field.length()+1, " ");
            printPartOfBorder("+");
        }
        System.out.println();
    }

    private void printCharSet(int quantity, String part){
        for (int i = 0; i < quantity; i++) {
            printPartOfBorder(part);
        }
    }

    private void printPartOfBorder (String part) {
        if ( colorPrint ) {
            System.out.print(ANSI_PURPLE + part + ANSI_RESET);
        } else {
            System.out.print(part);
        }
    }

    private void printValField (String field) {
        if ( colorPrint ) {
            System.out.printf( ANSI_BLUE + " %s" + ANSI_RESET, field);
        } else {
            System.out.printf(" %s", field);
        }
    }

}
