package sandy.sqlcmd.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

import static org.junit.Assert.*;

public class JDBCDatabaseManagerTest {

    private PrepareDB dbTest;

    @Before
    public void setup() {

        try {
            dbTest = PrepareDB.createAndConnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
}

    @Test
    public void connectToSpecificDatabase() throws Exception {

        DatabaseManager dbManager = new JDBCDatabaseManager();
        String[] params = {PrepareDB.DB_NAME, PrepareDB.ROOT_NAME, PrepareDB.PASS};

        dbManager.connect(params[0], params[1], params[2]);
}

    @Test
    public void testCreateAndTables () throws MainProcessException {

        DataSet expected = new DataSet();
        expected.addRow();
        expected.addField(0,"id");
        expected.addField(0,"title");

        dbTest.executeUpdate("CREATE TABLE test ( id varchar(255), title varchar(255))");
        DataSet actual = dbTest.executeQuery("SELECT * FROM test");

        assertTrue( expected.equals(actual));
}
@Test ( expected = MainProcessException.class)
    public void testCreateAndTablesBadParametr () throws MainProcessException {

        DataSet expected = new DataSet();
        expected.addRow();
        expected.addField(0,"id");
        expected.addField(0,"title");

        dbTest.executeUpdate("CREATE TABLE test ( id , title varchar(255))");
        dbTest.executeQuery("SELECT * FROM test");
}

    @Test
    public void testExistTable () throws MainProcessException {

        dbTest.executeUpdate("CREATE TABLE test ( id varchar(255), title varchar(255))");
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

        dbTest.executeUpdate("CREATE TABLE test ( id varchar(255), title varchar(255))");
        Boolean actual = dbTest.existColumns( "test", DatabaseManager.FULL_COVERAGES, "id","title");

        assertTrue( actual);
}
    @Test
    public void testExistColumnsFullNotExist () throws MainProcessException {

        dbTest.executeUpdate("CREATE TABLE test ( id varchar(255), title varchar(255))");
        Boolean actual = dbTest.existColumns( "test", DatabaseManager.FULL_COVERAGES, "title");

        assertFalse( actual);
}
    @Test
    public void testExistAnyColumns () throws MainProcessException {

        dbTest.executeUpdate("CREATE TABLE test ( id varchar(255), title varchar(255))");
        Boolean actual = dbTest.existColumns( "test", DatabaseManager.EXISTENCE_THESE_FIELDS, "title");

        assertTrue( actual);
}
    @Test
    public void testExistAnyColumnNotExist () throws MainProcessException {

        dbTest.executeUpdate("CREATE TABLE test ( id varchar(255), title varchar(255))");
        Boolean actual = dbTest.existColumns( "test", DatabaseManager.EXISTENCE_THESE_FIELDS, "title-Bla-bla");

        assertFalse( actual);
}

    @Test
    public void testExistAnyColumnUnknownTable() throws MainProcessException {

        dbTest.executeUpdate("CREATE TABLE test ( id varchar(255), title varchar(255))");
        Boolean actual = dbTest.existColumns( "bla-bla", DatabaseManager.EXISTENCE_THESE_FIELDS, "title-Bla-bla");

        assertFalse( actual);
}


    @Test ( expected = MainProcessException.class )
    public void connectWithIncorrectParameters() throws Exception {

        DatabaseManager dbManager = new JDBCDatabaseManager();
        String[] params = {"rave", "rave", "rave"};

        dbManager.connect(params[0], params[1], params[2]);
}

    @After
    public void end() {
        try {
            PrepareDB.closeAndDelete(dbTest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}