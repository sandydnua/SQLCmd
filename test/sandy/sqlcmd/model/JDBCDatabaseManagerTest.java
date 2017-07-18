package sandy.sqlcmd.model;

import org.junit.Test;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

import static org.junit.Assert.*;

public class JDBCDatabaseManagerTest {

    @Test
    public void connectToSpecificDatabase() throws Exception {
        DatabaseManager dbManager = new JDBCDatabaseManager();
        String[] params = {"postgres", "postgres", "7561"};

        dbManager.connect(params[0], params[1], params[2]);

    }

    @Test ( expected = MainProcessException.class )
    public void connectWitjIncorrectParameters() throws Exception {
        DatabaseManager dbManager = new JDBCDatabaseManager();
        String[] params = {"rave", "rave", "rave"};

        dbManager.connect(params[0], params[1], params[2]);

    }

}