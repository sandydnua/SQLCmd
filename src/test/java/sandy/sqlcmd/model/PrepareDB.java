package sandy.sqlcmd.model;

import java.sql.SQLException;
import java.sql.Statement;

public class PrepareDB extends JDBCDatabaseManager {

    public static final String ADDRESS_AND_PORT = "localhost:5432";
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
            statement.executeUpdate("CREATE ROLE " + USER_TEST + " LOGIN\n" +
                    "  PASSWORD '" + PASS + "'\n" +
                    "  SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION;\n");
        } catch (SQLException e) {
            throw new Exception("Ошибка создания пользователя " + e.getMessage());
        }
    }

    private void deleteUserTest() throws Exception {

        try (Statement statement = getStmt()) {
            statement.executeUpdate("DROP ROLE IF EXISTS " + USER_TEST);
        } catch (SQLException e) {
            throw new Exception("Ошибка удаления пользователя " + e.getMessage());
        }
    }

    public static void create() throws Exception {

        PrepareDB dbManager = new PrepareDB();
        dbManager.connect(ADDRESS_AND_PORT, DB_NAME, ROOT_NAME, PASS);
        dbManager.dropDatabase();
        dbManager.createDatabase();

        dbManager.deleteUserTest();
        dbManager.createUserTest();

        dbManager.disconnect();
    }

    public static PrepareDB connect() throws Exception {
        PrepareDB dbManager = new PrepareDB();
        dbManager.connect(ADDRESS_AND_PORT, DB_TEST, USER_TEST, PASS);
        return dbManager;
    }

    public static void close(PrepareDB dbManager) throws Exception {

        dbManager.disconnect();
    }

    public static void delete(PrepareDB dbManager) throws Exception {

        dbManager.connect(ADDRESS_AND_PORT, DB_NAME, ROOT_NAME, PASS);
        dbManager.dropDatabase();
        dbManager.deleteUserTest();
        dbManager.disconnect();
    }
}
