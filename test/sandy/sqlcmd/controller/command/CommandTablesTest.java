package sandy.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.controller.command.Command;
import sandy.sqlcmd.controller.command.CommandTables;
import sandy.sqlcmd.model.DatabaseManager;
import sandy.sqlcmd.model.SQLConstructorPostgre;

import static org.mockito.Mockito.*;

public class CommandTablesTest{
    private DatabaseManager dbManager;

    @Before
    public void setup(){
        dbManager = mock(DatabaseManager.class);
        when(dbManager.isConnect()).thenReturn(true);
        when(dbManager.getSQLConstructor()).thenReturn( new SQLConstructorPostgre());
    }

    @Test
    public void validateRequesInCommand() throws Exception {
        String[] params = {"tables"};
        Command command = new CommandTables(params);

        command.setDbManager(dbManager);

        command.execute();
        verify(dbManager,times(1)).executeQuery(anyString());
    }

}