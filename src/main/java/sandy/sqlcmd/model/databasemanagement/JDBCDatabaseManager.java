package sandy.sqlcmd.model.databasemanagement;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JDBCDatabaseManager implements DatabaseManager {


    private Connection connection;

    public JDBCDatabaseManager() {
        connection = null;
    }

    @Override
    public void connect(String address, String database, String userName, String password) throws MainProcessException {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            throw new MainProcessException("org.postgresql.Driver не подключен");
        }

        if( isConnect()) {
            disconnect();
        }
        try {
            connection = DriverManager.getConnection( String.format("jdbc:postgresql://%s/%s?loggerLevel=OFF", address, database ),
                                                      userName, password);
        }catch (SQLException e) {
            connection = null;
            throw new MainProcessException("Ошибка подключения к базе.");
        }
    }

    @Override
    public void disconnect() throws MainProcessException {

        if( null != connection ) try {
            connection.close();
        } catch (SQLException e) {
            throw new MainProcessException("Ошибка при закрытии подключения");
        }
    }

    @Override
    public DataSet executeQuery(String sqlQuery) throws MainProcessException {

        DataSet data;
        try(Statement stmt = getStmt(); ResultSet result = stmt.executeQuery(sqlQuery)) {
            data = formResult(result);

        } catch (SQLException e) {
            throw new MainProcessException("Ошибка при выполнении запроса к базе"+e.getMessage());
        }
        return data;
    }

    @Override
    public void executeUpdate(String sqlQuery) throws MainProcessException {

        try(Statement stmt = getStmt()) {
            stmt.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            throw new MainProcessException("Ошибка при выполнении запроса к базе: "+e.getMessage());
        }
    }

    protected Statement getStmt() throws SQLException {
        return connection.createStatement();
    }

    private DataSet formResult(ResultSet result) throws SQLException {

        DataSet data = new DataSet();
        int columnCount = result.getMetaData().getColumnCount();
        data.addRow();

        for ( int i = 1; i <= columnCount; i++ ) {
            data.addField (NUMBER_OF_FIRST_ROW_IN_TABLE,result.getMetaData().getColumnName(i) );
        }

        while(result.next()){
            int i = data.addRow();
            for( int j = 0; j < columnCount; j++ ){
                data.addField(i,result.getString(j+1));
            }
        }

        return data;
    }
    @Override
    public boolean isConnect() {
        if ( null == connection) {
            return false;
        } else {
            try {
                return !connection.isClosed();
            } catch (SQLException e) {
                return false;
            }
        }
    }

    @Override
    public SQLConstructor getSQLConstructor() {
        return new SQLConstructorPostgre();
    }

    @Override
    public boolean existTable(String tableName) throws MainProcessException {

        if ( null == tableName || tableName.length() == 0) {
            return false;
        } else {
            SQLConstructor sqlConstructor = this.getSQLConstructor();
            sqlConstructor.addTables(tableName);
            DataSet data = this.executeQuery(sqlConstructor.getQueryExistTable());
            return data.quantityRows() > 1;
        }
    }

    @Override
    public  boolean existColumns(String tableName, int mode, String... columns) {

        SQLConstructor sqlConstructor= this.getSQLConstructor();
        sqlConstructor.addTables(tableName);
        DataSet data;
        try {
            String sqlQuery = sqlConstructor.getQuerySelect();
            data = this.executeQuery(sqlQuery);
        } catch ( Exception ex) {
            return false;
        }

        if ( mode == FULL_COVERAGES && data.quantityFieldsInRow(NUMBER_OF_FIRST_ROW_IN_TABLE) != columns.length) {
            return false;
        }
        Set<String> setFromParameters = new HashSet<>(Arrays.asList(columns));
        Set<String> setFromTables = new HashSet<>();

        for (int i = 0; i < data.quantityFieldsInRow(NUMBER_OF_FIRST_ROW_IN_TABLE); i++) {
            setFromTables.add(data.getField(NUMBER_OF_FIRST_ROW_IN_TABLE, i));
        }
        int diffSize = 0;
        if( mode == FULL_COVERAGES) {

            setFromTables.removeAll(setFromParameters);
            diffSize = setFromTables.size();
        }
        if( mode == EXISTENCE_THESE_FIELDS ) {

            setFromParameters.removeAll(setFromTables);
            diffSize = setFromParameters.size();
        }

        return diffSize == 0;
    }
}
