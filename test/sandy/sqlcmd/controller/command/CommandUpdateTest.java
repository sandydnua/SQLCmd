package sandy.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.controller.command.Command;
import sandy.sqlcmd.controller.command.CommandUpdate;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructorPostgre;

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
        when(dbManager.getSQLConstructor()).thenReturn( new SQLConstructorPostgre());
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