package sandy.sqlcmd.sandy.sqlcmd.model;

import sandy.sqlcmd.model.DataSet;

public interface DatabaseManager{

    void connect(String database, String userName, String password) throws MainProcessExepion;
    void disconnect() throws MainProcessExepion;
    DataSet executeQuery(String sqlQuery) throws MainProcessExepion;
    void executeUpdate(String sqlQuery) throws MainProcessExepion;
    boolean isConnect();
}
