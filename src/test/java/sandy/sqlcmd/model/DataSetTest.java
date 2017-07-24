package sandy.sqlcmd.model;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataSetTest {

    @Test
    public void testEquals() throws Exception {

        DataSet dataFirst = new DataSet();
        DataSet dataSecond = new DataSet();

        dataFirst.addString("Первая строка");
        dataFirst.addString(new String[]{"Вторая строка", "Третья строка"});
        dataFirst.addRow();
        dataFirst.addField(0,"Первое поле первой строки");
        dataFirst.addField(0,"Второе поле первой строки");

        assertEquals(false, dataFirst.equals(dataSecond));

        dataSecond.addString("Первая строка");
        dataSecond.addString(new String[]{"Вторая строка", "Третья строка"});
        dataSecond.addRow();
        dataSecond.addField(0,"Первое поле первой строки");

        assertEquals(false, dataFirst.equals(dataSecond));

        dataSecond.addField(0,"Второе поле первой строки");

        assertEquals(true, dataFirst.equals(dataSecond));
    }

    @Test
    public void QuantityRowsEmptyDataSetExpectZero(){

        DataSet dataSet = new DataSet();
        int expected = 0;
        int result = dataSet.quantityRows();

        assertEquals(expected, result);
    }

    @Test
    public void QuantityRows(){

        DataSet dataSet = new DataSet();
        int expected = 1;
        dataSet.addRow();
        int result = dataSet.quantityRows();

        assertEquals(expected, result);
    }
}
