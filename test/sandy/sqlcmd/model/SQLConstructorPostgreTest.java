package sandy.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.model.Exceptions.IncorretParametersQuery;

import static org.junit.Assert.assertEquals;

public class SQLConstructorPostgreTest {

    SQLConstructorPostgre sqlConstructor;

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
    public void testGetQueryDrop() throws IncorretParametersQuery {

        String expected = "DROP TABLE tableName";
        sqlConstructor.addTables("tableName");
        String actual = sqlConstructor.getQueryDrop();
        assertEquals( expected, actual);
    }

    @Test
    public void testGetQueryFind() throws IncorretParametersQuery {

        String expected = "SELECT * FROM tableName";

        sqlConstructor.addTables("tableName");
        String actual = sqlConstructor.getQueryFind();

        assertEquals( expected, actual);
    }

    @Test
    public void testGetQueryFindWithColumns() throws IncorretParametersQuery {

        String expected = "SELECT id, title FROM tableName";
        sqlConstructor.addColumnForSelectInsertCreate("id", "title");
        sqlConstructor.addTables("tableName");
        String actual = sqlConstructor.getQueryFind();

        assertEquals( expected, actual);
    }

    @Test ( expected = IncorretParametersQuery.class)
    public void testGetQueryFindMissingTable() throws IncorretParametersQuery {

        String expected = "SELECT * FROM tableName";
        String actual = sqlConstructor.getQueryFind();
        assertEquals( expected, actual);
    }

    @Test ( expected = IncorretParametersQuery.class)
    public void testGetQueryFindEmptyTable() throws IncorretParametersQuery {

        String expected = "SELECT * FROM tableName";
        sqlConstructor.addTables( null );
        String actual = sqlConstructor.getQueryFind();
        assertEquals( expected, actual);
    }

    @Test
    public void testGetQueryFindShowTwoField() throws IncorretParametersQuery {

        String expected = "SELECT firstField, secondField FROM tableName";
        sqlConstructor.addColumnForSelectInsertCreate("firstField", "secondField");
        sqlConstructor.addTables("tableName");
        String actual = sqlConstructor.getQueryFind();

        assertEquals( expected, actual);
    }

    @Test
    public void testGetQueryClear() throws IncorretParametersQuery {

        String expected = "DELETE FROM tableName";
        sqlConstructor.addTables("tableName");
        String actual = sqlConstructor.getQueryClear();

        assertEquals( expected, actual);
    }

    @Test
    public void  testGetQuerySelect() throws IncorretParametersQuery {

        String expected = "SELECT * FROM tableName WHERE column = 'value'";
        sqlConstructor.addTables("tableName");
        sqlConstructor.setColumnAndValueForWhere("column", "value");
        String actual = sqlConstructor.getQuerySelect();

        assertEquals( expected, actual);
    }

    @Test
    public void  testGetQueryUpdate() throws IncorretParametersQuery {
        String expected = "UPDATE tableName SET column = newValue WHERE column = 'value'";
        sqlConstructor.addTables("tableName");
        sqlConstructor.setColumnAndValueForWhere("column", "value");
        sqlConstructor.setForColumnNewValue( "column", "newValue");
        String actual = sqlConstructor.getQueryUpdate();

        assertEquals( expected, actual);
    }

    @Test
    public void  testGetQueryInsert() throws IncorretParametersQuery {

        String expected = "INSERT INTO tableName ( id, title ) VALUES ( '1', 'bla-bla' )";
        sqlConstructor.addTables("tableName");
        sqlConstructor.addColumnForSelectInsertCreate("id", "title");
        sqlConstructor.addValuesForInsert("1", "bla-bla");
        String actual = sqlConstructor.getQueryInsert();

        assertEquals( expected, actual);
    }

    @Test (expected = IncorretParametersQuery.class )
    public void  testGetQueryInsertWithoutValues() throws IncorretParametersQuery {

        String expected = "INSERT INTO tableName ( id, title ) VALUES ( '1', 'bla-bla' )";
        sqlConstructor.addTables("tableName");
        sqlConstructor.addColumnForSelectInsertCreate("id", "title");
        String actual = sqlConstructor.getQueryInsert();
    }

    @Test
    public void  testGetQueryDelete() throws IncorretParametersQuery {

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