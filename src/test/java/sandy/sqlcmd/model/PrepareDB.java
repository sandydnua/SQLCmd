package sandy.sqlcmd.model;

import sandy.sqlcmd.model.databasemanagement.JDBCDatabaseManager;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class PrepareDB extends JDBCDatabaseManager {

    public static String ADDRESS_AND_PORT;
    public static String DB_NAME;
    public static String PASS;
    public static String ROOT_NAME;

    static {
        try {
            FileInputStream fileProperties = new FileInputStream("src/test/resources/config.properties");
            Properties properties = new Properties();
            properties.load(fileProperties);

            ADDRESS_AND_PORT = properties.getProperty("address.and.port");
            DB_NAME = properties.getProperty("db.name");
            PASS = properties.getProperty("pass");
            ROOT_NAME = properties.getProperty("root.name");
        } catch (java.io.IOException e) {
            System.out.println("Ошибка при получении properties");
            e.printStackTrace();
        }
    }

    public static final String DB_TEST = "testdb";
    public static final String USER_TEST = "usertest";

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
