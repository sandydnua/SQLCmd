package sandy.sqlcmd.model;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class UnknownCommnadTest {
    @Mock
    String[] params;

    @Test
    public void executeMainProcess() throws Exception {
        DataSet expeted = new DataSet();
        expeted.addString("Что Ты ввёл?!");
        expeted.addString("Введи Help для справки.");
        Command command = new UnknownCommnad(params);

        DataSet actual = command.execute();
        assertTrue( expeted.equals(actual) );
    }

}