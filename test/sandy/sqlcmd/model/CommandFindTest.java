package sandy.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CommandFindTest {
    DatabaseManager dbManager;
    @Before
    public void setup() {
        dbManager = mock (DatabaseManager.class);
    }
    @Test
    public void testExecuteMainProcess() throws Exception {
        String[] params = {"find","tableName"};
        String sqlQuery = "SELECT * FROM tableName";
        Command command = new CommandFind(params);
        command.setDbManager(dbManager);

        DataSet data = new DataSet();
        data.addRow();
        when(dbManager.isConnect()).thenReturn(true);
        when(dbManager.executeQuery(anyString())).thenReturn(data);
        command.execute();
        verify(dbManager, times(1)).executeQuery(sqlQuery);
    }
    @Test
    public void tastExecuteMainProcessWhenTableIsEmpty() throws Exception {
        String[] params = {"find","tableName"};
        Command command = new CommandFind(params);
        command.setDbManager(dbManager);

        DataSet data = new DataSet();
        DataSet expeted = new DataSet();
        expeted.addString("Таблица пуста. Содержит следующие поля:");

        when(dbManager.isConnect()).thenReturn(true);
        when(dbManager.executeQuery(anyString())).thenReturn(data);

        DataSet actual = command.execute();
        assertTrue(expeted.equals(actual));
    }

}