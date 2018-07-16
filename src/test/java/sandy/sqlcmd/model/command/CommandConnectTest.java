package sandy.sqlcmd.model.command;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CommandConnectTest {
    private DatabaseManager dbManager;

    @Before
    public void setup() {
        dbManager = mock(DatabaseManager.class);
        when(dbManager.isConnect()).thenReturn(true);
    }

    @Test
    public void executeMainProcessToLocalDB() throws Exception {

        String[] params = {"connect", "localhost:5432","dbName", "userName", "password"};
        Command command = new CommandConnect();
        command.setParams(params);
        command.setDbManager(dbManager);
        DataSet expected = new DataSet("Connected to the database");
        DataSet result = command.execute();

        assertTrue( expected.equals(result) );
        verify(dbManager, times(1)).connect("localhost:5432",
                                                                   "dbName",
                                                                   "userName",
                                                                   "password"
                                                                   );
    }
}