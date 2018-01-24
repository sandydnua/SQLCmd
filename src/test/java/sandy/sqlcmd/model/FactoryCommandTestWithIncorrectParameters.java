package sandy.sqlcmd.model;

import org.junit.Test;
import sandy.sqlcmd.model.command.Command;
import sandy.sqlcmd.model.command.UnknownCommand;

import static org.junit.Assert.*;

public class FactoryCommandTestWithIncorrectParameters {

    @Test
    public void testGetCommandWithIncorrectParameters(){

        Command expCommand = new UnknownCommand( null );
        assertTrue( expCommand.getClass() == AllCommands.getCommand( null ).getClass() );
    }

}