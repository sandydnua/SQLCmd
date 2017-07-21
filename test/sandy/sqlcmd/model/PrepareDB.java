package sandy.sqlcmd.model;

import java.sql.SQLException;
import java.sql.Statement;

public class PrepareDB extends JDBCDatabaseManager {


    private static final String DB_NAME = "postgres";
    private static final String DB_TEST = "testdb";
    private static final String ROOT_NAME = "admin";
    private static final String USER_TEST = "usertest";
    private static final String PASS = "7561";

    public PrepareDB() {
        super();
    }
    public static  String getNameDbTest() {

        return DB_TEST;
    }

    public void dropDatabase(String databaseName) throws Exception {

        try (Statement statement = getStmt()) {
            statement.executeUpdate("DROP DATABASE IF EXISTS " + databaseName);
        } catch (SQLException e) {
            throw new Exception("Ошибка удаления базы данных");
        }
    }

    public void createDatabase(String databaseName) throws Exception {

        try (Statement statement = getStmt()) {
            statement.executeUpdate("CREATE DATABASE " + databaseName);
        } catch (SQLException e) {
            throw new Exception("Ошибка создания базы данных");
        }
    }

    public void createUserTest() throws Exception {
        try (Statement statement = getStmt()) {
            statement.executeUpdate("CREATE ROLE userTest LOGIN\n" +
                    "  PASSWORD '7561'\n" +
                    "  SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION;\n");
        } catch (SQLException e) {
            throw new Exception("Ошибка создания пользователя " + e.getMessage());
        }
    }

    public void deleteUserTest() throws Exception {

        try (Statement statement = getStmt()) {
            statement.executeUpdate("DROP ROLE IF EXISTS usertest");
        } catch (SQLException e) {
            throw new Exception("Ошибка удаления пользователя " + e.getMessage());
        }
    }

    public static PrepareDB createAndConnect() throws Exception {

        PrepareDB dbManager = new PrepareDB();
        dbManager.connect(DB_NAME,ROOT_NAME,PASS);
        dbManager.dropDatabase(DB_TEST);
        dbManager.createDatabase(DB_TEST);

        dbManager.deleteUserTest();
        dbManager.createUserTest();

        dbManager.disconnect();
        dbManager.connect(DB_TEST,USER_TEST,PASS);
        return dbManager;
    }

    public  static  void closeAndDelete(PrepareDB dbManager) throws Exception {

        dbManager.disconnect();
        dbManager.connect(DB_NAME, ROOT_NAME, PASS);
        dbManager.dropDatabase(DB_TEST);
        dbManager.deleteUserTest();
        dbManager.disconnect();
    }




}
