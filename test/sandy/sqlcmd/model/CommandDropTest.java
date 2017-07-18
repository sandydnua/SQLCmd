package sandy.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CommandDropTest {
    DatabaseManager dbManager;
    @Before
    public void setup(){
        dbManager = mock(DatabaseManager.class);
    }

    @Test
    public void executeMainProcess() throws Exception {
        String[] params = {"drop","tableName"};
        String sqlQuery = "DROP TABLE tableName";

        Command command = new CommandDrop(params);
        command.setDbManager(dbManager);

        DataSet expected = new DataSet();
        DataSet result = new DataSet();
        expected.addString("Таблица " + params[1] + " удалена");

        when(dbManager.isConnect()).thenReturn(true);
        result = command.execute();

        assertTrue( expected.equals(result));
        verify(dbManager,times(1)).executeUpdate(sqlQuery);
    }

}