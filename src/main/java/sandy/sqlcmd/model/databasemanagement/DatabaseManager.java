package sandy.sqlcmd.model.databasemanagement;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.IncorrectParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

import java.util.Map;
import java.util.Set;

// TODO надо этот интерфейс переместить в другой каталог, но тогда не работает Autowired
public interface DatabaseManager{

    int EXISTENCE_THESE_FIELDS = 1;
    int FULL_COVERAGES = 0;
    int NUMBER_OF_FIRST_ROW_IN_TABLE = 0;

    void connect( String address, String database, String userName, String password) throws MainProcessException;
    void disconnect() throws MainProcessException;

    boolean isConnect();
    boolean existTable(String tableName) throws MainProcessException;
    boolean existColumns(String tableName, int mode,  Set<String> fields);

    DataSet getTables();

    void dropTable(String param);

    void createTable(String tableName, String[] fieldsName);

    void insert(String tableName, Map<String, String> newRecord) throws IncorrectParametersQuery;

    void delete(String tableName, Map<String, String> entry) throws IncorrectParametersQuery;

    DataSet find(String tableName) throws IncorrectParametersQuery;

    DataSet find(String tableName, Map<String, String> condition) throws IncorrectParametersQuery;

    void update(String tableName, Map<String, String> condition, Map<String, String> newValue) throws IncorrectParametersQuery;

    void clearTable(String tableName) throws IncorrectParametersQuery;
}
