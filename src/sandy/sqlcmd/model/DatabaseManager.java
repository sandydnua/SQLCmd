package sandy.sqlcmd.model;

public interface DatabaseManager{

    void connect(String database, String userName, String password) throws MainProcessExeption;
    void disconnect() throws MainProcessExeption;
    DataSet executeQuery(String sqlQuery) throws MainProcessExeption;
    void executeUpdate(String sqlQuery) throws MainProcessExeption;
    boolean isConnect();
}
