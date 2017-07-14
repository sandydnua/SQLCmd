package sandy.sqlcmd.view;
import sandy.sqlcmd.model.DataSet;

import java.util.Iterator;
import java.util.Scanner;

public class Console implements View {
    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
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
        int quantiyRows = data.quantityRows();
        if(quantiyRows == 0) return;

        int quantityFields = data.quantityFieldsInRow(0);
        int[] maxWidthFields = getMaximumWidthsFields(data, quantiyRows, quantityFields);

        drawLine(maxWidthFields, null,0);
        drawLine(maxWidthFields, data,0);
        drawLine(maxWidthFields, null,0);

        for(int i = 1; i < quantiyRows; i++){
            drawLine(maxWidthFields, data,i);
            drawLine(maxWidthFields, null,0);
        }
    }

    private int[] getMaximumWidthsFields(DataSet data,int quantiyRows, int quantityFields) {
        int[] maxWidthFields = new int[quantityFields];

        for(int j = 0; j < quantityFields; j++){
            for(int i = 0; i < quantiyRows; i++){
                if(maxWidthFields[j] < data.getField(i,j).length()){
                    maxWidthFields[j] = data.getField(i,j).length();
                }
            }
        }
        return maxWidthFields;
    }

    private void drawLine(int[] maxWidthFields, DataSet data, int row) {
        System.out.print("+");
        for(int index = 0; index < maxWidthFields.length; index++) {
            if (null == data){
                printCharSet(maxWidthFields[index]+2,'-');
            }else{
                String field = data.getField(row,index);
                System.out.printf(" %s",field);
                printCharSet(maxWidthFields[index] - field.length()+1,' ');
            }
            System.out.print("+");
        }
        System.out.println("");
    }
    private void printCharSet(int quantity, char ch){
        for (int i = 0; i < quantity; i++) {
            System.out.print(ch);
        }
    }
}
