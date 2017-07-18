package sandy.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.controller.command.Command;
import sandy.sqlcmd.controller.command.CommandInsert;
import sandy.sqlcmd.model.DatabaseManager;
import sandy.sqlcmd.model.SQLConstructorPostgre;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class CommandInsertTest {
    DatabaseManager dbManager;
    @Before
    public void setup() {
        dbManager = mock(DatabaseManager.class);
        when(dbManager.getSQLConstructor()).thenReturn( new SQLConstructorPostgre());
        when(dbManager.isConnect()).thenReturn(true);
    }
    @Test
    public void executeMainProcess() throws Exception {

        String[] params = {"insert", "tableName", "id", "1", "title", "MyName"};
        String sqlQuery = "INSERT INTO tableName ( id, title ) VALUES ( '1', 'MyName' )";
        Command command = new CommandInsert(params);
        command.setDbManager(dbManager);

        command.execute();
        verify(dbManager, times(1)).executeUpdate(sqlQuery);
    }

}