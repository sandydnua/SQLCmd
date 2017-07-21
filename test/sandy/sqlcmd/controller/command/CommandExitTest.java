package sandy.sqlcmd.controller.command;


import org.junit.Test;
import sandy.sqlcmd.model.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.CompletionOfWorkException;
import sandy.sqlcmd.model.Exceptions.IncorretParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommandExitTest {

    @Test(expected = CompletionOfWorkException.class)
    public void testExecuteExpectedException() throws CompletionOfWorkException, IncorretParametersQuery, MainProcessException, CantExecuteException {

        String[] params = {"exit"};
        Command command = new CommandExit(params);
        command.execute();
    }

    @Test(expected = CompletionOfWorkException.class)
    public void testExitWithoutClosingConnection() throws CompletionOfWorkException, IncorretParametersQuery, MainProcessException, CantExecuteException {

        String[] params = {"exit"};

        DatabaseManager dbManager = mock( DatabaseManager.class );

        when(dbManager.isConnect()).thenReturn(true);
        doThrow( MainProcessException.class ).when(dbManager).disconnect();

        Command command = new CommandExit(params);
        command.setDbManager( dbManager );
        command.execute();
    }
}
