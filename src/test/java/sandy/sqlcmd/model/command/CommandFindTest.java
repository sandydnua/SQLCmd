package sandy.sqlcmd.model.command;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;
import sandy.sqlcmd.model.databasemanagement.SQLConstructorPostgre;


import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;



public class CommandFindTest {

    private DatabaseManager dbManager;

    @Before
    public void setup() {

        dbManager = mock (DatabaseManager.class);
        when(dbManager.isConnect()).thenReturn(true);
        when(dbManager.getSQLConstructor()).thenReturn( new SQLConstructorPostgre());
    }
    @Test
    public void testExecuteMainProcess() throws Exception {

        String[] params = {"find","tableName"};
        String sqlQuery = "SELECT * FROM tableName";

        Command command = new CommandFind(params);
        command.setDbManager(dbManager);

        DataSet data = new DataSet();
        data.addRow();

        when(dbManager.existTable(anyString())).thenReturn(true);
        when(dbManager.executeQuery(anyString())).thenReturn(data);

        command.execute();
        verify(dbManager, times(1)).executeQuery(sqlQuery);
    }
    @Test
    public void testExecuteMainProcessWhenTableIsEmpty() throws Exception {

        String[] params = {"find","tableName"};
        Command command = new CommandFind(params);
        command.setDbManager(dbManager);

        DataSet data = new DataSet();
        DataSet expected = new DataSet();
        expected.addString("Таблица пуста. Содержит следующие поля:");
        when(dbManager.existTable(anyString())).thenReturn(true);
        when(dbManager.executeQuery(anyString())).thenReturn(data);

        DataSet actual = command.execute();
        assertTrue(expected.equals(actual));
    }

}