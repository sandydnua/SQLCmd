package sandy.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class CommandTablesTest{
    private DatabaseManager dbManager;

    @Before
    public void setup(){
        dbManager = mock(DatabaseManager.class);
    }

    @Test
    public void validateRequesInCommand() throws Exception {
        String[] params = {"tables"};
        Command command = new CommandTables(params);
        String sqlQuery = "select table_name from information_schema.tables where table_schema='public'";

        command.setDbManager(dbManager);
        when(dbManager.isConnect()).thenReturn(true);

        command.execute();
        verify(dbManager,times(1)).executeQuery(sqlQuery);
    }

}