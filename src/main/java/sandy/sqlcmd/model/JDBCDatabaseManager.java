package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.IncorretParametersQuery;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.view.Console;
import sandy.sqlcmd.view.View;

import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JDBCDatabaseManager implements DatabaseManager {

    private Connection connection;

    public JDBCDatabaseManager(){
        connection = null;
    }

    @Override
    public void connect(String database, String userName, String password) throws MainProcessException {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            throw new MainProcessException("org.postgresql.Driver не подключен");
        }

        if( isConnect()) {
            disconnect();
        }

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + database+ "?loggerLevel=OFF", userName, password);
        }catch (SQLException e) {
            connection = null;
            throw new MainProcessException(String.format("Ошибка подключения к базе. (%s,%s,%s)",database,userName,password));
        }
    }

    @Override
    public void disconnect() throws MainProcessException {

        if( null != connection ) try {
            connection.close();
        } catch (SQLException e) {
            connection = null;
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
            data.addField (0,result.getMetaData().getColumnName(i) );
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
    public boolean isConnect(){
        return (null == connection)? false : true;
    }

    @Override
    public SQLConstructor getSQLConstructor() {
        return new SQLConstructorPostgre();
    }

    @Override
    public boolean existTable(String tableName) throws MainProcessException {

        if ( null == tableName || tableName.length() == 0) {
            return false;
        }

        SQLConstructor sqlConstructor = this.getSQLConstructor();
        sqlConstructor.addTables(tableName);
        sqlConstructor.getQueryExistTable();
        DataSet data = this.executeQuery(sqlConstructor.getQueryExistTable());

        if ( data.quantityRows() > 1 ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public  boolean existColumns(String tableName, int mode, String... columns) {

        SQLConstructor sqlConstructor= this.getSQLConstructor();
        sqlConstructor.addTables(tableName);
        DataSet data;
        String sqlQuery = null;
        try {
            sqlQuery = sqlConstructor.getQuerySelect();
            data = this.executeQuery(sqlQuery);
        } catch ( Exception ex) {
            return false;
        }

        if ( mode == FULL_COVERAGES && data.quantityFieldsInRow(0) != columns.length) {
            return false;
        }
        Set<String> setFromParameters = new HashSet(Arrays.asList(columns));
        Set<String> setFromTables = new HashSet();

        for (int i = 0; i < data.quantityFieldsInRow(0); i++) {
            setFromTables.add(data.getField(0, i));
        }

        if( mode == FULL_COVERAGES) {

            setFromTables.removeAll(setFromParameters);
            if ( setFromTables.size() == 0 ) {
                return true;
            } else {
                return false;
            }
        }
        if( mode == EXISTENCE_THESE_FIELDS ) {

            setFromParameters.removeAll(setFromTables);
            if ( setFromParameters.size() == 0 ) {
                return true;
            } else {
                return false;
            }
        }

        return true;
    }
}
