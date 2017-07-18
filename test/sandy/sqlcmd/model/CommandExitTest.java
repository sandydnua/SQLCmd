package sandy.sqlcmd.model;


import org.junit.Test;
import sandy.sqlcmd.model.Exceptions.CompletionOfWorkException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

import static junit.framework.TestCase.assertEquals;

public class CommandExitTest {
    @Test(expected = CompletionOfWorkException.class)
    public void testExecuteExpectedException(){
        String[] params = {"exit"};
        Command command = new CommandExit(params);
        command.execute();
    }
}
