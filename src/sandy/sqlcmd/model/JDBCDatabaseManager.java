package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.MainProcessException;

import java.sql.*;

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
            throw new MainProcessException("Ошибка подключения к базе");
        }
    }

    @Override
    public void disconnect() throws MainProcessException {
        if( null != connection ) try {
            connection.close();
        } catch (SQLException e) {
            connection = null;
            throw new MainProcessException("Ошибка при закрытии подключения");
        }else{
            throw new MainProcessException("Подключения не было");
        }
    }

    @Override
    public DataSet executeQuery(String sqlQuery) throws MainProcessException {
        DataSet data;
        try(Statement stmt = getStmt(); ResultSet result = stmt.executeQuery(sqlQuery)) {
            data = formResult(result);

        } catch (SQLException e) {
            throw new MainProcessException("Ошибка при выполнении запроса к базе "+e.getMessage());
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



    private Statement getStmt() throws SQLException {
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
}
