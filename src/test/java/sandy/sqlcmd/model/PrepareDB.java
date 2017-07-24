package sandy.sqlcmd.model;

import java.sql.SQLException;
import java.sql.Statement;

public class PrepareDB extends JDBCDatabaseManager {


    public static final String DB_NAME = "postgres";
    public static final String DB_TEST = "testdb";
    public static final String ROOT_NAME = "admin";
    public static final String USER_TEST = "usertest";
    public static final String PASS = "7561";

    private PrepareDB() {
        super();
    }

    private void dropDatabase() throws Exception {

        try (Statement statement = getStmt()) {
            statement.executeUpdate("DROP DATABASE IF EXISTS " + PrepareDB.DB_TEST);
        } catch (SQLException e) {
            throw new Exception("Ошибка удаления базы данных");
        }
    }

    private void createDatabase() throws Exception {

        try (Statement statement = getStmt()) {
            statement.executeUpdate("CREATE DATABASE " + PrepareDB.DB_TEST);
        } catch (SQLException e) {
            throw new Exception("Ошибка создания базы данных");
        }
    }

    private void createUserTest() throws Exception {
        try (Statement statement = getStmt()) {
            statement.executeUpdate("CREATE ROLE userTest LOGIN\n" +
                    "  PASSWORD '" + PASS + "'\n" +
                    "  SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION;\n");
        } catch (SQLException e) {
            throw new Exception("Ошибка создания пользователя " + e.getMessage());
        }
    }

    private void deleteUserTest() throws Exception {

        try (Statement statement = getStmt()) {
            statement.executeUpdate("DROP ROLE IF EXISTS usertest");
        } catch (SQLException e) {
            throw new Exception("Ошибка удаления пользователя " + e.getMessage());
        }
    }

    public static PrepareDB createAndConnect() throws Exception {

        PrepareDB dbManager = new PrepareDB();
        dbManager.connect(DB_NAME, ROOT_NAME, PASS);
        dbManager.dropDatabase();
        dbManager.createDatabase();

        dbManager.deleteUserTest();
        dbManager.createUserTest();

        dbManager.disconnect();
        dbManager.connect(DB_TEST, USER_TEST, PASS);
        return dbManager;
    }

    public static void closeAndDelete(PrepareDB dbManager) throws Exception {

        dbManager.disconnect();
        dbManager.connect(DB_NAME, ROOT_NAME, PASS);
        dbManager.dropDatabase();
        dbManager.deleteUserTest();
        dbManager.disconnect();
    }


}
