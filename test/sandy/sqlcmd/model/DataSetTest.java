package sandy.sqlcmd.model;
import junit.framework.TestCase;
import org.junit.Test;

public class DataSetTest extends TestCase {

    @Test
    public void QuantityRowsEmptyDataSetExpectZero(){
        DataSet dataSet = new DataSet();
        int expected = 0;
        int result = dataSet.quantityRows();

        assertEquals(expected,result);
    }

    @Test
    public void QuantityRows(){
        DataSet dataSet = new DataSet();
        int expected = 1;
        dataSet.addRow();
        int result = dataSet.quantityRows();

        assertEquals(expected,result);
    }


}
