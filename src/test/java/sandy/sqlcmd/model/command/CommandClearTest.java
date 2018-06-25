package sandy.sqlcmd.model.command;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;
import sandy.sqlcmd.model.databasemanagement.SQLConstructorPostgre;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class CommandClearTest {

    private DatabaseManager dbManager;
    @Before
    public void setup(){
        dbManager = mock(DatabaseManager.class);
    }

    @Test
    public void executeMainProcess() throws Exception {
        String[] params = {"clear","tableName"};

        Command command = new CommandClear(params);
        command.setDbManager(dbManager);

        DataSet expected = new DataSet();
        DataSet result;
        expected.addString("Таблица " + params[1] + " очищена");

        when(dbManager.isConnect()).thenReturn(true);
        result = command.execute();

        assertTrue( expected.equals(result));
    }
}