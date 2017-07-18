package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.MainProcessException;

public interface DatabaseManager{

    void connect(String database, String userName, String password) throws MainProcessException;
    void disconnect() throws MainProcessException;
    DataSet executeQuery(String sqlQuery) throws MainProcessException;
    void executeUpdate(String sqlQuery) throws MainProcessException;
    boolean isConnect();
    SQLConstructor getSQLConstructor();
}
