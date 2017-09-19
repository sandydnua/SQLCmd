package sandy.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.controller.web.Command;
import sandy.sqlcmd.controller.web.CommandClear;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.controller.web.DatabaseManager;
import sandy.sqlcmd.model.SQLConstructorPostgre;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class CommandClearTest {

    private DatabaseManager dbManager;
    @Before
    public void setup(){
        dbManager = mock(DatabaseManager.class);
        when(dbManager.getSQLConstructor()).thenReturn( new SQLConstructorPostgre());
    }

    @Test
    public void executeMainProcess() throws Exception {
        String[] params = {"clear","tableName"};
        String sqlQuery = "DELETE FROM tableName";

        Command command = new CommandClear(params);
        command.setDbManager(dbManager);

        DataSet expected = new DataSet();
        DataSet result;
        expected.addString("Таблица " + params[1] + " очищена");

        when(dbManager.isConnect()).thenReturn(true);
        result = command.execute();

        assertTrue( expected.equals(result));
        verify(dbManager,times(1)).executeUpdate(sqlQuery);
    }
}