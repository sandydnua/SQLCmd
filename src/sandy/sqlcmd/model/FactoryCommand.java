package sandy.sqlcmd.model;

import java.sql.*;

public class FactoryCommand {
    public static Command getCommand(String[] params){
        switch (params[0].toUpperCase()){
            case "HELP":
                return new CommandHelp();
            case "EXIT":
                return new CommandExit();
            case "CONNECT":
                return new CommandConnect(params);
            case "DISCONNECT":
                return new CommandDisonnect(params);
            case "TABLES":
                return new CommandTables(params);
            case "FIND":
                return new CommandFind(params);
            case "DROP":
                return new CommandDrop(params);
            case "CLEAR":
                return new CommandClear(params);
            case "DELETE":
                return new CommandDelete(params);
            case "CREATE":
                return new CommandCreate(params);
            case "UPDATE":
                return new CommandUpdate(params);
            case "INSERT":
                return new CommandInsert(params);
            default: return new UnknownCommnad();
        }
    }

    public static interface DatabaseManager{

        void connect(String database, String userName, String password) throws CommandUpdate.MainProcessExepion;
        void disconnect() throws CommandUpdate.MainProcessExepion;
        DataSet executeQuery(String sqlQuery) throws CommandUpdate.MainProcessExepion;
        void executeUpdate(String sqlQuery) throws CommandUpdate.MainProcessExepion;
        boolean isConnect();
    }

    public static class JDBCDatabaseManager implements DatabaseManager {

        private Connection connection;

        public JDBCDatabaseManager(){
            connection = null;
        }

        @Override
        public void connect(String database, String userName, String password) throws CommandUpdate.MainProcessExepion {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                throw new CommandUpdate.MainProcessExepion("org.postgresql.Driver не подключен");
            }
            if( isConnect()) {
                disconnect();
            }

            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + database+ "?loggerLevel=OFF", userName, password);
            }catch (SQLException e) {
                connection = null;
                throw new CommandUpdate.MainProcessExepion("Ошибка подключения к базе");
            }
        }

        @Override
        public void disconnect() throws CommandUpdate.MainProcessExepion {
           if( null != connection ) try {
               connection.close();
           } catch (SQLException e) {
               connection = null;
               throw new CommandUpdate.MainProcessExepion("Ошибка при закрытии подключения");
           }else{
               throw new CommandUpdate.MainProcessExepion("Подключения не было");
           }
        }

        @Override
        public DataSet executeQuery(String sqlQuery) throws CommandUpdate.MainProcessExepion {
            DataSet data;
            try(Statement stmt = getStmt(); ResultSet result = stmt.executeQuery(sqlQuery)) {
                data = formResult(result);

            } catch (SQLException e) {
                throw new CommandUpdate.MainProcessExepion("Ошибка при выполнении запроса к базе "+e.getMessage());
            }
            return data;
        }

        @Override
        public void executeUpdate(String sqlQuery) throws CommandUpdate.MainProcessExepion {
            try(Statement stmt = getStmt()) {
                stmt.executeUpdate(sqlQuery);
            } catch (SQLException e) {
                throw new CommandUpdate.MainProcessExepion("Ошибка при выполнении запроса к базе: "+e.getMessage());
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
}
