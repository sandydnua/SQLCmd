package sandy.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class CommandInsertTest {
    DatabaseManager dbManager;
    @Before
    public void setup() {
        dbManager = mock(DatabaseManager.class);
    }
    @Test
    public void executeMainProcess() throws Exception {
        String[] params = {"insert", "tableName", "id", "1", "title", "MyName"};
        String sqlQuery = "INSERT INTO tableName ( id, title ) VALUES ( '1', 'MyName' )";
        Command command = new CommandInsert(params);
        command.setDbManager(dbManager);

        when(dbManager.isConnect()).thenReturn(true);
        command.execute();
        verify(dbManager, times(1)).executeUpdate(sqlQuery);
    }

}