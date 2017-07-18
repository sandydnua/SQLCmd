package sandy.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.controller.command.Command;
import sandy.sqlcmd.controller.command.CommandConnect;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructorPostgre;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CommandConnectTest {
    DatabaseManager dbManager;

    @Before
    public void setup() {
        dbManager = mock(DatabaseManager.class);
        when(dbManager.getSQLConstructor()).thenReturn( new SQLConstructorPostgre());


    }

    @Test
    public void executeMainProcessToLocalDB() throws Exception {

        String[] params = {"connect", "dbName", "userName", "password"};
        Command command = new CommandConnect(params);
        command.setDbManager(dbManager);
        DataSet expected = new DataSet("Подключился к базе");
        DataSet result = command.execute();

        assertTrue( expected.equals(result) );
        verify(dbManager, times(1)).connect("dbName", "userName", "password");
    }
    @Test
    public void executeMainProcessWithExepion() throws Exception {

        String[] params = {"connect", "dbName", "userName", "password"};
        Command command = new CommandConnect(params);
        command.setDbManager(dbManager);
        doThrow( new MainProcessException("Error") ).when(dbManager).connect(anyString(), anyString(), anyString());
        DataSet actual = command.execute();
        DataSet expected = new DataSet("Error");
        assertTrue( expected.equals(actual) );
    }

}