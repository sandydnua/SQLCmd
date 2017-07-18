package sandy.sqlcmd.controller.command;


import org.junit.Test;
import sandy.sqlcmd.controller.command.Command;
import sandy.sqlcmd.controller.command.CommandExit;
import sandy.sqlcmd.model.Exceptions.CompletionOfWorkException;

import static junit.framework.TestCase.assertEquals;

public class CommandExitTest {
    @Test(expected = CompletionOfWorkException.class)
    public void testExecuteExpectedException(){
        String[] params = {"exit"};
        Command command = new CommandExit(params);
        command.execute();
    }
}
