package sandy.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CommandDeleteTest {
    DatabaseManager dbManager;
    String sqlQueryDelete;
    String sqlQuerySelect;
    Command command;
    DataSet data;

    @Before
    public void setup() throws MainProcessException {
        dbManager = mock(DatabaseManager.class);
        String[] params = {"delete", "tableName", "columnName", "columnValue"};
        sqlQueryDelete = "DELETE FROM tableName WHERE columnName = columnValue";
        sqlQuerySelect = "SELECT * FROM tableName WHERE columnName = columnValue";
        command = new CommandDelete(params);
        command.setDbManager(dbManager);
        data = new DataSet();
        when(dbManager.isConnect()).thenReturn(true);
        when(dbManager.executeQuery(anyString())).thenReturn(data);
    }

    @Test
    public void executeMainProcess() throws Exception {

        data.addRow();
        data.addRow();
        data.addString("Удалены следующие строки");
        DataSet actual = command.execute();
        assertTrue(data.equals(actual));
        verify(dbManager, times(1)).executeUpdate(sqlQueryDelete);
        verify(dbManager, times(1)).executeQuery(sqlQuerySelect);
    }

    @Test
    public void executeMainProcessWhenRowNotFaund() throws Exception {

        data.addString("Нечего удалять");
        DataSet actual = command.execute();
        assertTrue(data.equals(actual));
        verify(dbManager, times(1)).executeQuery(sqlQuerySelect);
        verify(dbManager, times(0)).executeUpdate(sqlQueryDelete);
    }
}