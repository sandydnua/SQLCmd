package sandy.sqlcmd.model.command;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CommandDeleteTest {

    private DatabaseManager dbManager;

    private String tableName = "tableName";
    private Command command;
    private DataSet data;

    @Before
    public void setup() throws MainProcessException, IncorrectParametersQuery {

        dbManager = mock(DatabaseManager.class);
        command = new CommandDelete();
        command.setDbManager(dbManager);

        data = new DataSet();

        when(dbManager.isConnect()).thenReturn(true);
        when(dbManager.existTable(tableName)).thenReturn(true);

    }



    @Test
    public void executeMainProcess() throws Exception {

        data.addRow();
        data.addRow();
        data.addString("Удалены следующие строки");

        String column = "columnName";
        String value = "columnValue";
        String[] params = {"delete", tableName, column, value};

        command.setParams(params);

        Map<String, String> condition = new HashMap<>();
        condition.put(column, value);

        when(dbManager.find(tableName, condition)).thenReturn(data);

        DataSet actual = command.execute();
        assertTrue(data.equals(actual));

        verify(dbManager, times(1)).delete(tableName , condition);
        verify(dbManager, times(1)).find(tableName , condition);
        verify(dbManager, times(1)).existTable(tableName);
    }
    @Test
    public void executeMainProcessManyParameters() throws Exception {
        String[] params = {"delete", tableName, "columnName", "columnValue", "id", "num"};

        Map<String, String> condition = new HashMap<>();
        condition.put("columnName", "columnValue");
        condition.put("id", "num");

        command = new CommandDelete();
        command.setParams(params);
        command.setDbManager(dbManager);

        data.addRow();
        data.addRow();
        data.addString("Удалены следующие строки");

        when(dbManager.find(tableName, condition)).thenReturn(data);

        DataSet actual = command.execute();
        assertTrue(data.equals(actual));
        verify(dbManager, times(1)).delete(tableName , condition);
        verify(dbManager, times(1)).find(tableName , condition);
        verify(dbManager, times(1)).existTable(tableName);
    }

    @Test
    public void executeMainProcessWhenRowNotFound() throws Exception {
        String[] params = {"delete", tableName, "something", "something"};

        data.addString("Нечего удалять");

        command = new CommandDelete();
        command.setParams(params);
        command.setDbManager(dbManager);

        Map<String, String> condition = new HashMap<>();
        condition.put("something", "something");

        when(dbManager.find(tableName, condition)).thenReturn(data);

        DataSet actual = command.execute();
        assertTrue(data.equals(actual));

        verify(dbManager, times(1)).existTable(tableName);
        verify(dbManager, times(1)).find(tableName , condition);
        verify(dbManager, times(0)).delete(tableName , condition);
    }
    @Test(expected = MainProcessException.class)
    public void executeMainProcessWhenIncorrectNumberOfParameters() throws Exception {
        String[] params = {"delete", tableName, "something", "something", "something"};

        command = new CommandDelete();
        command.setParams(params);
        command.setDbManager(dbManager);
        command.execute();

    }
    @Test(expected = MainProcessException.class)
    public void executeMainProcessWhenFewParameters() throws Exception {
        String[] params = {"delete", tableName, "something"};

        command = new CommandDelete();
        command.setParams(params);
        command.setDbManager(dbManager);
        command.execute();

    }
}