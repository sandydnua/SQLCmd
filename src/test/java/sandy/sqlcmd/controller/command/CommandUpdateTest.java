package sandy.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.controller.web.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructorPostgre;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CommandUpdateTest {

    private DatabaseManager dbManager;

    private String sqlQueryUpdate;
    private String sqlQuerySelect;

    private Command command;
    private DataSet data;

    @Before
    public void setup() throws MainProcessException {

        sqlQueryUpdate = "UPDATE tableName SET columnName = 'columnValueNew' WHERE column = 'value'";
        sqlQuerySelect = "SELECT * FROM tableName WHERE column = 'value'";
        String[] params = {"update", "tableName", "column", "value", "columnName", "columnValueNew"};

        dbManager = mock(DatabaseManager.class);
        command = new CommandUpdate(params);
        command.setDbManager(dbManager);

        when(dbManager.isConnect()).thenReturn(true);
        when(dbManager.getSQLConstructor()).thenReturn( new SQLConstructorPostgre());
        when(dbManager.existTable(anyString())).thenReturn(true);
        when(dbManager.existColumns(anyString(),anyInt(), anyString(),anyString())).thenReturn(true);
    }

    @Test
    public void executeMainProcess() throws Exception {

        data = new DataSet();
        data.addRow();
        data.addRow();

        when(dbManager.executeQuery(anyString())).thenReturn(data);

        DataSet expected = new DataSet("Эти строки будут обновлены");
        expected.addRow();
        expected.addRow();
        DataSet actual = command.execute();

        assertTrue(expected.equals(actual));

        verify(dbManager, times(1)).existTable("tableName");
        verify(dbManager, times(1)).existColumns("tableName",DatabaseManager.EXISTENCE_THESE_FIELDS, "column", "columnName");
        verify(dbManager, times(1)).executeQuery(sqlQuerySelect);
        verify(dbManager, times(1)).executeUpdate(sqlQueryUpdate);
    }

    @Test
    public void executeMainProcessWhenRowNotFound() throws Exception {

        data = new DataSet();
        data.addRow();

        when(dbManager.executeQuery(anyString())).thenReturn(data);
        DataSet expected = new DataSet("С такими параметрами строки не найдены.");
        DataSet actual = command.execute();

        assertTrue(expected.equals(actual));
        verify(dbManager, times(1)).executeQuery(sqlQuerySelect);
        verify(dbManager, times(0)).executeUpdate(sqlQueryUpdate);
    }

}