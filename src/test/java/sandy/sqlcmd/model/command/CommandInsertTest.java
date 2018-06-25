package sandy.sqlcmd.model.command;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.CantExecuteOrNoConnectionException;
import sandy.sqlcmd.model.databasemanagement.SQLConstructorPostgre;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class CommandInsertTest {

    private DatabaseManager dbManager;

    @Before
    public void setup() {
        dbManager = mock(DatabaseManager.class);
        when(dbManager.isConnect()).thenReturn(true);
    }

    @Test
    public void executeMainProcess() throws Exception {
        String[] params = {"insert", "tableName", "id", "1", "title", "MyName"};
        String tableName = params[1];
        Map<String, String> data = new HashMap<>();
        data.put( "id", "1");
        data.put( "title", "MyName");
        Set<String> columns = new HashSet<>();
        columns.add("id");
        columns.add("title");
        Command command = new CommandInsert(params);
        command.setDbManager(dbManager);
        when(dbManager.existTable(tableName)).thenReturn(true);
        when(dbManager.existColumns(tableName, DatabaseManager.FULL_COVERAGES, columns)).thenReturn(true);

        command.execute();
        verify(dbManager, times(1)).insert(tableName, data);
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