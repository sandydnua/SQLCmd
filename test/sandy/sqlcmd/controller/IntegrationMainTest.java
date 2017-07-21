package sandy.sqlcmd.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.PrepareDB;
import sandy.sqlcmd.view.View;

import java.io.*;

import static org.junit.Assert.*;

public class IntegrationMainTest {

    String lineSeparator;

    private ByteArrayOutputStream out;
    private InputStream systemIn;
    private PrintStream systemOut;
    private View view;

    private StdIn in;

    private PrepareDB dbTest;

    @Before
    public void setup() {

        lineSeparator = System.getProperty("line.separator");

        try {
            dbTest = PrepareDB.createAndConnect();
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

        in.add("connect "+PrepareDB.getNameDbTest()+" usertest 7561");
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

        in.add("connect "+PrepareDB.getNameDbTest()+" usertest 7561");
        in.add("create newtable title");
        in.add("insert newtable title \"First String\"");
        in.add("insert newtable title \"Second String\"");
        in.add("find newtable");
        in.add("delete newtable title \"First String\"");
        in.add("find newtable");

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

    @After
    public void end(){

        System.setErr(systemOut);
        System.setOut(systemOut);

        System.setIn(systemIn);

        try {
            PrepareDB.closeAndDelete(dbTest);
        } catch (MainProcessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}