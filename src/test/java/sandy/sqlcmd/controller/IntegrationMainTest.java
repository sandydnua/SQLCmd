package sandy.sqlcmd.controller;

import org.junit.*;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.PrepareDB;
import sandy.sqlcmd.view.*;
import sandy.sqlcmd.view.Console;

import java.io.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IntegrationMainTest {

    private String lineSeparator;

    private ByteArrayOutputStream out;
    private InputStream systemIn;
    private PrintStream systemOut;

    private StdIn in;

    private static PrepareDB dbTest;

    @BeforeClass
    public static void createTestDB() throws Exception {
        try {
            PrepareDB.create();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Тестовая база не создана");
        }
    }

    @AfterClass
    public static void deleteTestDB() {
        try {
            PrepareDB.delete(dbTest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() {

        lineSeparator = System.getProperty("line.separator");

        try {
            dbTest = PrepareDB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        in = new StdIn();
        out = new ByteArrayOutputStream();
        systemOut = System.out;

        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(out));

        systemIn = System.in;
        System.setIn(in);
    }

    @After
    public void end(){

        System.setErr(systemOut);
        System.setOut(systemOut);

        System.setIn(systemIn);

        try {
            PrepareDB.close(dbTest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMainExitCommand() throws Exception {

        in.add("exit");

        String expected = "Для справки введите HELP" + lineSeparator +
                          "Завершение работы." + lineSeparator;
        Main.main(new String[0]);
        String actual = getText();

        assertEquals( expected, actual);
    }

    @Test
    public void testMainConnectCommand() throws Exception {

        in.add(String.format("connect %s %s %s", PrepareDB.DB_TEST, PrepareDB.USER_TEST, PrepareDB.PASS));
        in.add("disconnect");
        in.add("exit");

        String expected = "Для справки введите HELP" + lineSeparator +
                "Подключился к базе" + lineSeparator +
                "Подключение закрыто" + lineSeparator +
                "Завершение работы." + lineSeparator;
        Main.main(new String[0]);
        String actual = getText();

        assertEquals( expected, actual);
    }

    @Test
    public void testMainConnectCreateInsertDeleteFind() throws Exception {

        in.add(String.format("connect %s %s %s", PrepareDB.DB_TEST, PrepareDB.USER_TEST, PrepareDB.PASS));
        in.add("create new_table title");
        in.add("insert new_table title \"First String\"");
        in.add("insert new_table title \"Second String\"");
        in.add("find new_table");
        in.add("delete new_table title \"First String\"");
        in.add("find new_table");

        in.add("exit");

        String expected = "Для справки введите HELP" + lineSeparator +
                "Подключился к базе" + lineSeparator +
                "Таблица создана" + lineSeparator +
                "Операция прошла успешно" + lineSeparator +
                "Операция прошла успешно" + lineSeparator +
                "+---------------+" + lineSeparator +
                "+ title         +" + lineSeparator +
                "+---------------+" + lineSeparator +
                "+ First String  +" + lineSeparator +
                "+---------------+" + lineSeparator +
                "+ Second String +" + lineSeparator +
                "+---------------+" + lineSeparator +
                "Удалены следующие строки" + lineSeparator +
                "+--------------+" + lineSeparator +
                "+ title        +" + lineSeparator +
                "+--------------+" + lineSeparator +
                "+ First String +" + lineSeparator +
                "+--------------+" + lineSeparator +
                "+---------------+" + lineSeparator +
                "+ title         +" + lineSeparator +
                "+---------------+" + lineSeparator +
                "+ Second String +" + lineSeparator +
                "+---------------+" + lineSeparator +
                "Завершение работы." + lineSeparator;
        Main.main(new String[0]);
        String actual = getText();

        assertEquals( expected, actual);
    }

    private String getText() {

        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

}