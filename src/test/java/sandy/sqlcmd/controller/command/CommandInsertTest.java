package sandy.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.controller.web.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.CantExecuteOrNoConnectionException;
import sandy.sqlcmd.model.SQLConstructorPostgre;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class CommandInsertTest {

    private DatabaseManager dbManager;

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
        when(dbManager.existTable(params[1])).thenReturn(true);
        when(dbManager.existColumns(params[1], DatabaseManager.FULL_COVERAGES, "id", "title")).thenReturn(true);

        command.execute();
        verify(dbManager, times(1)).executeUpdate(sqlQuery);
    }
    @Test ( expected = CantExecuteOrNoConnectionException.class)
    public void testIncorrectQuantityParameters() throws Exception {

        String[] params = {"insert", "tableName", "id", "1", "title"};
        Command command = new CommandInsert(params);
        command.setDbManager(dbManager);

        command.execute();
    }
    @Test ( expected = CantExecuteOrNoConnectionException.class)
    public void testIncorrectFewParameters() throws Exception {

        String[] params = {"insert", "tableName", "id"};
        Command command = new CommandInsert(params);
        command.setDbManager(dbManager);

        command.execute();
    }

}