package sandy.sqlcmd.model.databasemanagement;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

// TODO надо этот интерфейс переместить в другой каталог, но тогда не работает Autowired
public interface DatabaseManager{

    int EXISTENCE_THESE_FIELDS = 1;
    int FULL_COVERAGES = 0;
    int NUMBER_OF_FIRST_ROW_IN_TABLE = 0;

    SQLConstructor getSQLConstructor();

    void connect( String address, String database, String userName, String password) throws MainProcessException;
    void disconnect() throws MainProcessException;

    DataSet executeQuery(String sqlQuery) throws MainProcessException;
    void executeUpdate(String sqlQuery) throws MainProcessException;

    boolean isConnect();
    boolean existTable(String tableName) throws MainProcessException;
    boolean existColumns(String tableName, int mode, String... columns);
}
