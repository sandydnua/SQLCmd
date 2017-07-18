package sandy.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.controller.command.Command;
import sandy.sqlcmd.controller.command.CommandCreate;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.DatabaseManager;
import sandy.sqlcmd.model.SQLConstructorPostgre;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CommandCreateTest {
    DatabaseManager dbManager;
    @Before
    public void setup() {
        dbManager = mock(DatabaseManager.class);
        when(dbManager.getSQLConstructor()).thenReturn( new SQLConstructorPostgre());
    }

    @Test
    public void executeMainProcess() throws Exception {
        String[] params = {"create","newTable","id","title"};
        String sqlQuery = "CREATE TABLE newTable ( id varchar(255),title varchar(255) )";
        Command command = new CommandCreate(params);
        command.setDbManager(dbManager);
        when(dbManager.isConnect()).thenReturn(true);

        DataSet expected = new DataSet("Таблица создана");
        DataSet actual;
        actual = command.execute();
        assertTrue(expected.equals(actual));
        verify(dbManager,times(1)).executeUpdate(sqlQuery);
    }

}