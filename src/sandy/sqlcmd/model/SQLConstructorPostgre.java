package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.IncorretParametersQuery;
import java.util.*;

public class SQLConstructorPostgre implements SQLConstructor {
    private Map<String, String> templates;
    private StringBuilder tablesForFrom;
    private StringBuilder columnsForSelectAndInsert;
    private String coupleForSet;
    private String coupleForWhere;
    private StringBuilder valuesForInsert;

    public SQLConstructorPostgre() {
        templates = new HashMap<>();

        columnsForSelectAndInsert = new StringBuilder();
        tablesForFrom = new StringBuilder();
        valuesForInsert = new StringBuilder();

        coupleForWhere = "true";
        coupleForSet = null;

        templates.put("tables", "SELECT table_name FROM information_schema.tables WHERE table_schema='public'");
        templates.put("find", "SELECT <columns> FROM <tables>");
        templates.put("select", "SELECT <columns> FROM <tables> WHERE <where>");
        templates.put("clear", "DELETE FROM <tables>");
        templates.put("update", "UPDATE <tables> SET <coupleForSet> WHERE <where>");
        templates.put("drop", "DROP TABLE <tables>");
        templates.put("insert", "INSERT INTO <tables> ( <columns> ) VALUES ( <values> )");
    }

    @Override
    public void setColumnAndValueForWhere(String column, String value){
        coupleForWhere = String.format("%s = %s", column, value);
    }

    @Override
    public void addTables(String... tables) {
        addParameter( tablesForFrom, tables);
    }

    @Override
    public void addColumnForSelectAndInsert(String... columns) {
        addParameter(columnsForSelectAndInsert, columns);
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
    public String getQueryFind() throws IncorretParametersQuery {
        String columns = getColumns();
        String sqlQuery = templates.get("find").replace( "<columns>", columns);
        sqlQuery = sqlQuery.replace("<tables>", getTables());

        return sqlQuery;
    }

    private String getColumns() {
        if ( columnsForSelectAndInsert.length() == 0) {
            return "*";
        }  else {
            return columnsForSelectAndInsert.toString();
        }
    }
    private String getTables() throws IncorretParametersQuery {
        if ( tablesForFrom.length() == 0) {
            throw new IncorretParametersQuery( "Не указаны таблицы для запроса" );
        }  else {
            return tablesForFrom.toString();
        }
    }
    private String getValues() throws IncorretParametersQuery {
        if ( valuesForInsert.length() == 0) {
            throw new IncorretParametersQuery( "Не указаны значения для вставки" );
        }  else {
            return valuesForInsert.toString();
        }
    }

    @Override
    public String getQueryClear() throws IncorretParametersQuery {
        return templates.get("clear").replace("<tables>", getTables() );
    }

    @Override
    public void setForColumnNewValue(String column, String value) {
        coupleForSet = String.format("%s = %s", column, value);
    }

    @Override
    public String getQueryInsert() throws IncorretParametersQuery {
        String sqlQuery = templates.get("insert").replace("<tables>", getTables());
        sqlQuery = sqlQuery.replace("<columns>", getColumns());
        sqlQuery = sqlQuery.replace("<values>", getValues());
        return sqlQuery;
    }

    @Override
    public String getQuerySelect() throws IncorretParametersQuery {

        String sqlQuery = templates.get("select").replace("<where>", coupleForWhere);
        sqlQuery = sqlQuery.replace("<tables>", getTables());
        sqlQuery = sqlQuery.replace("<columns>", getColumns());

        return sqlQuery;
    }

    @Override
    public String getQueryUpdate() throws IncorretParametersQuery {
        String sqlQuery = templates.get("update").replace("<tables>", getTables());
        sqlQuery = sqlQuery.replace("<coupleForSet>", coupleForSet);
        sqlQuery = sqlQuery.replace("<where>", coupleForWhere);
        return sqlQuery;
    }

    @Override
    public String getQueryDrop() throws IncorretParametersQuery {
        String sqlQuery = templates.get("drop").replace("<tables>", getTables());
        return sqlQuery;
    }

    @Override
    public void addValuesForInsert(String... values) {
        for (int i = 0; i < values.length; i++) {
                values[i] = String.format("'%s'", values[i]);
        }
        addParameter(valuesForInsert, values);
    }
}