package sandy.sqlcmd.model;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sandy.sqlcmd.view.Console;
import sandy.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommandDisonnectTest {


    @Test
    public void executeMainProcess() throws Exception {
        String[] params = {"disconnect"};
        DatabaseManager dbManager = mock(DatabaseManager.class);
        when(dbManager.isConnect()).thenReturn(true);
        Command command = new CommandDisonnect(params);
        command.setDbManager(dbManager);
        DataSet expected = new DataSet("Подключение закрыто");
        DataSet actual = command.execute();
        assertTrue( expected.equals(actual) );
    }
    @Test
    public void executeMainProcessWithoutConnect() throws Exception {
        String[] params = {"disconnect"};
        DatabaseManager dbManager = mock(DatabaseManager.class);
        when(dbManager.isConnect()).thenReturn(false);
        Command command = new CommandDisonnect(params);
        command.setDbManager(dbManager);
        DataSet expected = new DataSet("Нет подключения к базе");
        DataSet actual = command.execute();
        assertTrue( expected.equals(actual) );
    }

}