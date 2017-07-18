package sandy.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CommandUpdateTest {
    DatabaseManager dbManager;
    String sqlQueryUpdate;
    String sqlQuerySelect;
    Command command;
    DataSet data;

    @Before
    public void setup() throws MainProcessException {
        dbManager = mock(DatabaseManager.class);
        String[] params = {"update", "tableName", "column", "value", "columnName", "columnValueNew"};
        sqlQueryUpdate = "UPDATE tableName SET columnName = columnValueNew WHERE column = value";
        sqlQuerySelect = "SELECT * FROM tableName WHERE column = value";
        command = new CommandUpdate(params);
        command.setDbManager(dbManager);
        data = new DataSet();
        when(dbManager.isConnect()).thenReturn(true);
        when(dbManager.executeQuery(anyString())).thenReturn(data);
    }

    @Test
    public void executeMainProcess() throws Exception {

        data.addRow();
        data.addRow();
        data.addString("Cтроки, которые будут обновлены");
        DataSet actual = command.execute();
        assertTrue(data.equals(actual));
        verify(dbManager, times(1)).executeQuery(sqlQuerySelect);
        verify(dbManager, times(1)).executeUpdate(sqlQueryUpdate);
    }

    @Test
    public void executeMainProcessWhenRowNotFaund() throws Exception {

        data.addString("С такими параметрами строки не найдены.");
        DataSet actual = command.execute();
        assertTrue(data.equals(actual));
        verify(dbManager, times(1)).executeQuery(sqlQuerySelect);
        verify(dbManager, times(0)).executeUpdate(sqlQueryUpdate);
    }

}