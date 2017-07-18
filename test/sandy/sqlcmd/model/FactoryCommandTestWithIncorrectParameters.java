package sandy.sqlcmd.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class FactoryCommandTestWithIncorrectParameters {
    @Test
    public void testGetCommandWithIncorrectParameters(){
        String[] params = null;
        Command expCommand = new UnknownCommnad(params);
        assertTrue( expCommand.getClass() == FactoryCommand.getCommand(params).getClass() );
    }

}