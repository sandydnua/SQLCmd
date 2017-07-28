package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.MainProcessException;

public interface DatabaseManager{

    int EXISTENCE_THESE_FIELDS = 1;
    int FULL_COVERAGES = 0;

    SQLConstructor getSQLConstructor();

    void connect( String address, String database, String userName, String password) throws MainProcessException;
    void disconnect() throws MainProcessException;

    DataSet executeQuery(String sqlQuery) throws MainProcessException;
    void executeUpdate(String sqlQuery) throws MainProcessException;

    boolean isConnect();
    boolean existTable(String param) throws MainProcessException;
    boolean existColumns(String tableName, int mode, String... columns);
}
