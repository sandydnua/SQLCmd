package sandy.sqlcmd.sandy.sqlcmd.model;

import sandy.sqlcmd.model.DataSet;
import java.sql.*;

public class JDBCDatabaseManager implements DatabaseManager {

    private Connection connection;

    public JDBCDatabaseManager(){
        connection = null;
    }

    @Override
    public void connect(String database, String userName, String password) throws MainProcessExepion {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            throw new MainProcessExepion("org.postgresql.Driver не подключен");
        }
        if( isConnect()) {
            disconnect();
        }

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + database+ "?loggerLevel=OFF", userName, password);
        }catch (SQLException e) {
            connection = null;
            throw new MainProcessExepion("Ошибка подключения к базе");
        }
    }

    @Override
    public void disconnect() throws MainProcessExepion {
       if( null != connection ) try {
           connection.close();
       } catch (SQLException e) {
           connection = null;
           throw new MainProcessExepion("Ошибка при закрытии подключения");
       }else{
           throw new MainProcessExepion("Подключения не было");
       }
    }

    @Override
    public DataSet executeQuery(String sqlQuery) throws MainProcessExepion {
        DataSet data;
        try(Statement stmt = getStmt(); ResultSet result = stmt.executeQuery(sqlQuery)) {
            data = formResult(result);

        } catch (SQLException e) {
            throw new MainProcessExepion("Ошибка при выполнении запроса к базе "+e.getMessage());
        }
        return data;
    }

    @Override
    public void executeUpdate(String sqlQuery) throws MainProcessExepion {
        try(Statement stmt = getStmt()) {
            stmt.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            throw new MainProcessExepion("Ошибка при выполнении запроса к базе: "+e.getMessage());
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
