package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.IncorretParametersQuery;

public interface SQLConstructor {

    void setColumnAndValueForWhere(String column, String values);

    void addTables(String... tables);

    void addColumnForSelectAndInsert(String... columns);

    void addValuesForInsert(String... values);

    String getQueryTables();

    String getQueryFind() throws IncorretParametersQuery;

    String getQueryClear() throws IncorretParametersQuery;

    void setForColumnNewValue(String param, String param1);

    String getQuerySelect() throws IncorretParametersQuery;

    String getQueryUpdate() throws IncorretParametersQuery;

    String getQueryDrop() throws IncorretParametersQuery;

    String getQueryInsert() throws IncorretParametersQuery;
}
