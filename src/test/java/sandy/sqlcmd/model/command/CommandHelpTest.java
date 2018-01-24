package sandy.sqlcmd.model.command;

import org.junit.Test;
import sandy.sqlcmd.model.DataSet;

import static org.junit.Assert.*;

public class CommandHelpTest {


    @Test
    public void executeMainProcessFullHelp() throws Exception {

        Command command = new CommandHelp( new String[]{"help"});
        DataSet expected = new DataSet();

        DataSet actual = command.execute();

        assertFalse( expected.equals(actual) );
    }

}