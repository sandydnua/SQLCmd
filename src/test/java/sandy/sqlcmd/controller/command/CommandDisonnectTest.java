package sandy.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.DatabaseManager;
import sandy.sqlcmd.model.SQLConstructorPostgre;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommandDisonnectTest {

    DatabaseManager dbManager;
    String[] params;
    Command command;

    @Before
    public void setup() {

        params = new String[]{"disconnect"};

        dbManager = mock(DatabaseManager.class);
        when(dbManager.getSQLConstructor()).thenReturn( new SQLConstructorPostgre());
        command = new CommandDisonnect(params);
        command.setDbManager(dbManager);
    }

    @Test
    public void executeMainProcess() throws Exception {

        when(dbManager.isConnect()).thenReturn(true);
        DataSet expected = new DataSet("Подключение закрыто");
        DataSet actual = command.execute();
        assertTrue( expected.equals(actual) );
    }

}