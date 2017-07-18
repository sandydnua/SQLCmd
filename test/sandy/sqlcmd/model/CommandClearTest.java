package sandy.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class CommandClearTest {

    DatabaseManager dbManager;
    @Before
    public void setup(){
        dbManager = mock(DatabaseManager.class);
    }

    @Test
    public void executeMainProcess() throws Exception {
        String[] params = {"clear","tableName"};
        String sqlQuery = "DELETE FROM tableName";

        Command command = new CommandClear(params);
        command.setDbManager(dbManager);

        DataSet expected = new DataSet();
        DataSet result = new DataSet();
        expected.addString("Таблица " + params[1] + " очищена");

        when(dbManager.isConnect()).thenReturn(true);
        result = command.execute();

        assertTrue( expected.equals(result));
        verify(dbManager,times(1)).executeUpdate(sqlQuery);
    }
}