package sandy.sqlcmd.model;

import org.junit.*;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;
import sandy.sqlcmd.model.databasemanagement.JDBCDatabaseManager;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import static org.junit.Assert.*;

public class JDBCDatabaseManagerTest {

    private static PrepareDB dbTest;

    @BeforeClass
    public static void setup() {

        try {
            PrepareDB.create();
            dbTest = PrepareDB.connect();
            dbTest.executeUpdate("CREATE TABLE test ( id varchar(255), title varchar(255), firstname varchar(255))");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void connectToSpecificDatabase() throws Exception {

        DatabaseManager dbManager = new JDBCDatabaseManager();

        dbManager.connect( PrepareDB.ADDRESS_AND_PORT, PrepareDB.DB_NAME,
                           PrepareDB.ROOT_NAME, PrepareDB.PASS
                         );
    }

    @AfterClass
    public static void end() {
        try {
            PrepareDB.close(dbTest);
            PrepareDB.delete(dbTest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testCreateAndTables () throws MainProcessException {

        DataSet expected = new DataSet();
        expected.addRow();
        expected.addField(0,"id");
        expected.addField(0,"title");
        expected.addField(0,"firstname");

        DataSet actual = dbTest.executeQuery("SELECT * FROM test");

        assertTrue( expected.equals(actual));
    }

    @Test ( expected = MainProcessException.class)
        public void testCreateAndTablesBadParametr () throws MainProcessException {

            dbTest.executeUpdate("CREATE TABLE test ( id , title varchar(255))");
            dbTest.executeQuery("SELECT * FROM test");
    }

    @Test
    public void testExistTable () throws MainProcessException {

        Boolean actual = dbTest.existTable("test");

        assertTrue( actual);
    }

    @Test
    public void testExistTableVoidParametr () throws MainProcessException {

        Boolean actual = dbTest.existTable("");

        assertFalse( actual);
    }

    @Test
    public void testExistColumnsFull () throws MainProcessException {

        String[] columns = new String[]{"id","title", "firstname"};
        Boolean actual = dbTest.existColumns( "test", DatabaseManager.FULL_COVERAGES, columns);

        assertTrue( actual);
    }

    @Test
    public void testExistColumnsFullNotExist () throws MainProcessException {


        Boolean actual = dbTest.existColumns( "test", DatabaseManager.FULL_COVERAGES, "title");

        assertFalse( actual);
    }

    @Test
    public void testExistAnyColumns () throws MainProcessException {

        Boolean actual = dbTest.existColumns( "test", DatabaseManager.EXISTENCE_THESE_FIELDS, "title");

        assertTrue( actual);
    }

    @Test
    public void testExistAnyColumnNotExist () throws MainProcessException {

        Boolean actual = dbTest.existColumns( "test", DatabaseManager.EXISTENCE_THESE_FIELDS, "title-Bla-bla");

        assertFalse( actual);
    }

    @Test
    public void testExistAnyColumnUnknownTable() throws MainProcessException {

        Boolean actual = dbTest.existColumns( "bla-bla", DatabaseManager.EXISTENCE_THESE_FIELDS, "title-Bla-bla");

        assertFalse( actual);
    }

    @Test ( expected = MainProcessException.class )
    public void connectWithIncorrectParameters() throws Exception {

    DatabaseManager dbManager = new JDBCDatabaseManager();
    String[] params = {"rave", "rave", "rave", "rave"};

    dbManager.connect(params[0], params[1], params[2], params[3]);
    }
}