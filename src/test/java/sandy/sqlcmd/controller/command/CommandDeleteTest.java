package sandy.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.controller.command.Command;
import sandy.sqlcmd.controller.command.CommandDelete;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructorPostgre;

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

        sqlQueryDelete = "DELETE FROM tableName WHERE columnName = 'columnValue'";
        sqlQuerySelect = "SELECT * FROM tableName WHERE columnName = 'columnValue'";
        String[] params = {"delete", "tableName", "columnName", "columnValue"};

        dbManager = mock(DatabaseManager.class);
        command = new CommandDelete(params);
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