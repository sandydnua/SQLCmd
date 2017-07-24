package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;
import java.util.*;

public class SQLConstructorPostgre implements SQLConstructor {

    private final Map<String, String> templates;
    private final StringBuilder tablesForFrom;
    private final StringBuilder columnsForSelectInsertCreate;
    private String coupleForSet;
    private String coupleForWhere;
    private final StringBuilder valuesForInsert;

    public SQLConstructorPostgre() {

        templates = new HashMap<>();

        columnsForSelectInsertCreate = new StringBuilder();
        tablesForFrom = new StringBuilder();
        valuesForInsert = new StringBuilder();

        coupleForWhere = "true";
        coupleForSet = null;

        templates.put("tables", "SELECT table_name FROM information_schema.tables WHERE table_schema='public'");
        templates.put("existtable", "SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_name='<table>'");
        templates.put("find", "SELECT <columns> FROM <tables>");
        templates.put("select", "SELECT <columns> FROM <tables> WHERE <where>");
        templates.put("clear", "DELETE FROM <tables>");
        templates.put("delete", "DELETE FROM <tables> WHERE <where>");
        templates.put("update", "UPDATE <tables> SET <coupleForSet> WHERE <where>");
        templates.put("drop", "DROP TABLE <tables>");
        templates.put("insert", "INSERT INTO <tables> ( <columns> ) VALUES ( <values> )");
        templates.put("createTable", "CREATE TABLE <table> ( <columnsAndTypes> )");
    }
    @Override
    public String getQueryCreateTable() {

        String defaultColumnType = "varchar(255)";
        String columnsAndTypes = columnsForSelectInsertCreate.toString().replace( ","," "+ defaultColumnType +",") + " "+ defaultColumnType;

        String sqlQuery = templates.get("createTable");
        sqlQuery = sqlQuery.replace("<table>", tablesForFrom);
        sqlQuery = sqlQuery.replace("<columnsAndTypes>",columnsAndTypes);

        return  sqlQuery;
    }


    @Override
    public String getQueryExistTable() {

        return templates.get("existtable").replace("<table>", tablesForFrom.toString());
    }

    @Override
    public void setForColumnNewValue(String column, String value) {
        coupleForSet = String.format("%s = '%s'", column, value);
    }

    @Override
    public void setColumnAndValueForWhere(String column, String value){

        coupleForWhere = String.format("%s = '%s'", column, value);
    }

    @Override
    public void addTables(String... tables) {
        addParameter( tablesForFrom, tables);
    }

    @Override
    public void addColumnForSelectInsertCreate(String... columns) {
        addParameter(columnsForSelectInsertCreate, columns);
    }

    private void addParameter( StringBuilder stringBuilder, String... params) {
        if ( null == params || params.length == 0 ) {
            return;
        }
        int i = 0;
        if( stringBuilder.length() == 0) {
            stringBuilder.append(params[i++]);
        } else {
            stringBuilder.append(", ");
            stringBuilder.append(params[i++]);
        }
        while ( i < params.length) {
            stringBuilder.append(", ");
            stringBuilder.append(params[i++]);
        }
    }

    @Override
    public String getQueryTables() {
        return templates.get("tables");
    }

    @Override
    public String getQueryFind() throws IncorrectParametersQuery {

        String columns = getColumns();
        String sqlQuery = templates.get("find").replace( "<columns>", columns);
        sqlQuery = sqlQuery.replace("<tables>", getTables());

        return sqlQuery;
    }
    private String getColumns() {

        if ( columnsForSelectInsertCreate.length() == 0) {
            return "*";
        }  else {
            return columnsForSelectInsertCreate.toString();
        }
    }
    private String getTables() throws IncorrectParametersQuery {

        if ( tablesForFrom.length() == 0) {
            throw new IncorrectParametersQuery( "Не указаны таблицы для запроса" );
        }  else {
            return tablesForFrom.toString();
        }
    }

    private String getValues() throws IncorrectParametersQuery {

        if ( valuesForInsert.length() == 0) {
            throw new IncorrectParametersQuery( "Не указаны значения для вставки" );
        }  else {
            return valuesForInsert.toString();
        }
    }

    @Override
    public String getQueryClear() throws IncorrectParametersQuery {
        return templates.get("clear").replace("<tables>", getTables() );
    }

    @Override
    public String getQueryInsert() throws IncorrectParametersQuery {

        String sqlQuery = templates.get("insert").replace("<tables>", getTables());
        sqlQuery = sqlQuery.replace("<columns>", getColumns());
        sqlQuery = sqlQuery.replace("<values>", getValues());

        return sqlQuery;
    }

    @Override
    public String getQuerySelect() throws IncorrectParametersQuery {

        String sqlQuery = templates.get("select").replace("<where>", coupleForWhere);
        sqlQuery = sqlQuery.replace("<tables>", getTables());
        sqlQuery = sqlQuery.replace("<columns>", getColumns());

        return sqlQuery;
    }

    @Override
    public String getQueryUpdate() throws IncorrectParametersQuery {

        String sqlQuery = templates.get("update").replace("<tables>", getTables());
        sqlQuery = sqlQuery.replace("<coupleForSet>", coupleForSet);
        sqlQuery = sqlQuery.replace("<where>", coupleForWhere);

        return sqlQuery;
    }

    @Override
    public String getQueryDrop() throws IncorrectParametersQuery {

        return templates.get("drop").replace("<tables>", getTables());
    }

    @Override
    public void addValuesForInsert(String... values) {

        for (int i = 0; i < values.length; i++) {
                values[i] = String.format("'%s'", values[i]);
        }

        addParameter(valuesForInsert, values);
    }
    @Override
    public String getQueryDelete() throws IncorrectParametersQuery {

        return templates.get("delete").replace("<tables>", getTables() ).replace("<where>", coupleForWhere);
    }
}
