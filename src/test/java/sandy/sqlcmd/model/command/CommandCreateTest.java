package sandy.sqlcmd.model.command;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CommandCreateTest {

    private DatabaseManager dbManager;

    @Before
    public void setup() {
        dbManager = mock(DatabaseManager.class);
        when(dbManager.isConnect()).thenReturn(true);
    }

    @Test
    public void executeMainProcess() throws Exception {
        String tableName = "newTable";
        String[] params = {"create",tableName,"id","title"};

        Command command = new CommandCreate();
        command.setParams(params);
        command.setDbManager(dbManager);

        DataSet expected = new DataSet("Таблица создана");
        DataSet actual;
        actual = command.execute();
        assertTrue(expected.equals(actual));
        String[] newFields = {"id", "title"};
        verify(dbManager,times(1)).createTable(tableName, newFields);
    }

}