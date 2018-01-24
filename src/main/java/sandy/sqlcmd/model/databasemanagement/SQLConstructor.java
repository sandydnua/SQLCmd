package sandy.sqlcmd.model.databasemanagement;

import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;

public interface SQLConstructor {

    void setColumnAndValueForWhere(String column, String values);

    void addColumnAndValueForWhere(String column, String value);

    void addTables(String... tables);

    void addColumnForSelectInsertCreate(String... columns);

    void addValuesForInsert(String... values);

    void setForColumnNewValue(String param, String param1);

    void addForColumnNewValue(String column, String value);

    String getQueryTables();

    String getQueryFind() throws IncorrectParametersQuery;

    String getQueryClear() throws IncorrectParametersQuery;

    String getQuerySelect() throws IncorrectParametersQuery;

    String getQueryUpdate() throws IncorrectParametersQuery;

    String getQueryDrop() throws IncorrectParametersQuery;

    String getQueryInsert() throws IncorrectParametersQuery;

    String getQueryDelete() throws IncorrectParametersQuery;

    String getQueryCreateTable();

    String getQueryExistTable();
}
