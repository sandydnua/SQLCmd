package sandy.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CommandCreateTest {
    DatabaseManager dbManager;
    @Before
    public void setup() {
        dbManager = mock(DatabaseManager.class);
    }

    @Test
    public void executeMainProcess() throws Exception {
        String[] params = {"create","newTable","id","title"};
        String sqlQuery = "CREATE TABLE newTable ( id varchar(255),title varchar(255) )";
        Command command = new CommandCreate(params);
        command.setDbManager(dbManager);
        when(dbManager.isConnect()).thenReturn(true);

        DataSet expected = new DataSet("Таблица создана");
        DataSet actual;
        actual = command.execute();
        assertTrue(expected.equals(actual));
        verify(dbManager,times(1)).executeUpdate(sqlQuery);
    }

}