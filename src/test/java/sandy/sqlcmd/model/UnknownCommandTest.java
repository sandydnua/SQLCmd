package sandy.sqlcmd.model;

import org.junit.Test;
import org.mockito.Mock;
import sandy.sqlcmd.controller.command.Command;

import static org.junit.Assert.*;

public class UnknownCommandTest {
    @SuppressWarnings("WeakerAccess")
    @Mock
    String[] params;

    @Test
    public void executeMainProcess() throws Exception {

        DataSet expected = new DataSet();

        expected.addString("Что Ты ввёл?!");
        expected.addString("Введи Help для справки.");

        Command command = new UnknownCommand(params);

        DataSet actual = command.execute();
        assertTrue( expected.equals(actual) );
    }

}