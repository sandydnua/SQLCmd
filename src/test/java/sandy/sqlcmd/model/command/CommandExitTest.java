package sandy.sqlcmd.model.command;


import org.junit.Test;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.CompletionOfWorkException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommandExitTest {

    @Test(expected = CompletionOfWorkException.class)
    public void testExecuteExpectedException() throws Exception {

        String[] params = {"exit"};
        Command command = new CommandExit(params);
        command.execute();
    }

    @Test(expected = CompletionOfWorkException.class)
    public void testExitWithoutClosingConnection() throws Exception {

        String[] params = {"exit"};

        DatabaseManager dbManager = mock( DatabaseManager.class );

        when(dbManager.isConnect()).thenReturn(true);
        doThrow( MainProcessException.class ).when(dbManager).disconnect();

        Command command = new CommandExit(params);
        command.setDbManager( dbManager );
        command.execute();
    }
}
