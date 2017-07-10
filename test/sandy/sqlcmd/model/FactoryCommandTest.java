package sandy.sqlcmd.model;

import junit.framework.TestCase;
import org.junit.Test;

public class FactoryCommandTest extends TestCase {
    @Test
    public void testGetCommand(){
        String[] params = new String[]{"FIND","tableName"};
        assertTrue( (new CommandFind(params)).getClass() == FactoryCommand.getCommand(params).getClass() );
    }
}
