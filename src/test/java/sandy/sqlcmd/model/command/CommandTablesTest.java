package sandy.sqlcmd.model.command;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;

import static org.mockito.Mockito.*;

public class CommandTablesTest{

    private DatabaseManager dbManager;

    @Before
    public void setup(){
        dbManager = mock(DatabaseManager.class);
        when(dbManager.isConnect()).thenReturn(true);
    }

    @Test
    public void validateRequestInCommand() throws Exception {
        String[] params = {"tables"};
        Command command = new CommandTables();
        command.setParams(params);

        command.setDbManager(dbManager);

        command.execute();
        verify(dbManager,times(1)).getTables();
    }

}