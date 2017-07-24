package sandy.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;

import static org.junit.Assert.assertEquals;

public class SQLConstructorPostgreTest {

    private SQLConstructorPostgre sqlConstructor;

    @Before
    public void setup() {
        sqlConstructor = new SQLConstructorPostgre();
    }

    @Test
    public void testGetQueryTables() {

        String expected = "SELECT table_name FROM information_schema.tables WHERE table_schema='public'";
        String actual = sqlConstructor.getQueryTables();
        assertEquals( expected, actual);
    }

    @Test
    public void testGetQueryDrop() throws IncorrectParametersQuery {

        String expected = "DROP TABLE tableName";
        sqlConstructor.addTables("tableName");
        String actual = sqlConstructor.getQueryDrop();
        assertEquals( expected, actual);
    }

    @Test
    public void testGetQueryFind() throws IncorrectParametersQuery {

        String expected = "SELECT * FROM tableName";

        sqlConstructor.addTables("tableName");
        String actual = sqlConstructor.getQueryFind();

        assertEquals( expected, actual);
    }

    @Test
    public void testGetQueryFindWithColumns() throws IncorrectParametersQuery {

        String expected = "SELECT id, title FROM tableName";
        sqlConstructor.addColumnForSelectInsertCreate("id", "title");
        sqlConstructor.addTables("tableName");
        String actual = sqlConstructor.getQueryFind();

        assertEquals( expected, actual);
    }

    @Test ( expected = IncorrectParametersQuery.class)
    public void testGetQueryFindMissingTable() throws IncorrectParametersQuery {

        String expected = "SELECT * FROM tableName";
        String actual = sqlConstructor.getQueryFind();
        assertEquals( expected, actual);
    }

    @Test ( expected = IncorrectParametersQuery.class)
    public void testGetQueryFindEmptyTable() throws IncorrectParametersQuery {

        String expected = "SELECT * FROM tableName";
        sqlConstructor.addTables((String[]) null);
        String actual = sqlConstructor.getQueryFind();
        assertEquals( expected, actual);
    }

    @Test
    public void testGetQueryFindShowTwoField() throws IncorrectParametersQuery {

        String expected = "SELECT firstField, secondField FROM tableName";
        sqlConstructor.addColumnForSelectInsertCreate("firstField", "secondField");
        sqlConstructor.addTables("tableName");
        String actual = sqlConstructor.getQueryFind();

        assertEquals( expected, actual);
    }

    @Test
    public void testGetQueryClear() throws IncorrectParametersQuery {

        String expected = "DELETE FROM tableName";
        sqlConstructor.addTables("tableName");
        String actual = sqlConstructor.getQueryClear();

        assertEquals( expected, actual);
    }

    @Test
    public void  testGetQuerySelect() throws IncorrectParametersQuery {

        String expected = "SELECT * FROM tableName WHERE column = 'value'";
        sqlConstructor.addTables("tableName");
        sqlConstructor.setColumnAndValueForWhere("column", "value");
        String actual = sqlConstructor.getQuerySelect();

        assertEquals( expected, actual);
    }

    @Test
    public void  testGetQueryUpdate() throws IncorrectParametersQuery {
        String expected = "UPDATE tableName SET column = 'newValue' WHERE column = 'value'";
        sqlConstructor.addTables("tableName");
        sqlConstructor.setColumnAndValueForWhere("column", "value");
        sqlConstructor.setForColumnNewValue( "column", "newValue");
        String actual = sqlConstructor.getQueryUpdate();

        assertEquals( expected, actual);
    }

    @Test
    public void  testGetQueryInsert() throws IncorrectParametersQuery {

        String expected = "INSERT INTO tableName ( id, title ) VALUES ( '1', 'bla-bla' )";
        sqlConstructor.addTables("tableName");
        sqlConstructor.addColumnForSelectInsertCreate("id", "title");
        sqlConstructor.addValuesForInsert("1", "bla-bla");
        String actual = sqlConstructor.getQueryInsert();

        assertEquals( expected, actual);
    }

    @Test (expected = IncorrectParametersQuery.class )
    public void  testGetQueryInsertWithoutValues() throws IncorrectParametersQuery {

        sqlConstructor.addTables("tableName");
        sqlConstructor.addColumnForSelectInsertCreate("id", "title");
        sqlConstructor.getQueryInsert();
    }

    @Test
    public void  testGetQueryDelete() throws IncorrectParametersQuery {

        String expected = "DELETE FROM tableName WHERE column = 'value str'";
        sqlConstructor.addTables("tableName");
        sqlConstructor.setColumnAndValueForWhere("column", "value str");
        String actual = sqlConstructor.getQueryDelete();

        assertEquals( expected, actual);
    }

    @Test
    public void testGetSQLExistTable(){

        String expected = "SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_name='tableName'";
        sqlConstructor.addTables("tableName");
        String actual = sqlConstructor.getQueryExistTable();

        assertEquals( expected, actual );
    }

    @Test
    public void testGetSQueryCreateTable(){

        String expected = "CREATE TABLE tableName ( id varchar(255), title varchar(255) )";
        sqlConstructor.addTables("tableName");
        sqlConstructor.addColumnForSelectInsertCreate( "id", "title");
        String actual = sqlConstructor.getQueryCreateTable();

        assertEquals( expected, actual );
    }
}
