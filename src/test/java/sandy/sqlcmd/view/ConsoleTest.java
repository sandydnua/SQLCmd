package sandy.sqlcmd.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.model.DataSet;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class ConsoleTest {

    private String lineSeparator;
    private ByteArrayOutputStream out;
    private PrintStream systemOut;
    private View view;
    private static final boolean colorPrint = false;

    @Before
    public void setup() {

        lineSeparator = System.getProperty("line.separator");

        out = new ByteArrayOutputStream();
        systemOut = System.out;
        view = new Console(colorPrint);

        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(out));
    }

    @Test
    public void writeText() throws Exception {

        DataSet data = new DataSet("First String");
        data.addString("Second String");
        String expected = "First String" + lineSeparator + "Second String" + lineSeparator;

        view.write(data);

        String actual = new String(out.toByteArray());

        assertEquals(expected, actual);
    }

    @Test
    public void writeStringLine() throws Exception {

        String expected = "One String\r\n";

        view.write("One String");
        String actual = new String(out.toByteArray());

        assertEquals(expected, actual);
    }

    @Test
    public void writeTable() throws Exception {

        DataSet data = new DataSet();
        int i = data.addRow();

        data.addField(i, "(0,0)");
        data.addField(i, "(0,1)");
        i = data.addRow();
        data.addField(i, "(1,0)");
        data.addField(i, "(1,1)");

        String expected = "+-------+-------+" + lineSeparator +
                          "+ (0,0) + (0,1) +" + lineSeparator +
                          "+-------+-------+" + lineSeparator +
                          "+ (1,0) + (1,1) +" + lineSeparator +
                          "+-------+-------+" + lineSeparator;

        view.write(data);

        String actual = new String(out.toByteArray());

        assertEquals(expected, actual);
    }

    @Test
    public void writeTextAndTable() throws Exception {

        DataSet data = new DataSet("Title");
        data.addString("Second String");

        int i = data.addRow();
        data.addField(i, "(0,0)");
        data.addField(i, "(0,1)");
        i = data.addRow();
        data.addField(i, "(1,0)");
        data.addField(i, "(1,1)");

        String expected = "Title" + lineSeparator +
                          "Second String" + lineSeparator +
                          "+-------+-------+" + lineSeparator +
                          "+ (0,0) + (0,1) +" + lineSeparator +
                          "+-------+-------+" + lineSeparator +
                          "+ (1,0) + (1,1) +" + lineSeparator +
                          "+-------+-------+" + lineSeparator;

        view.write(data);

        String actual = new String(out.toByteArray());

        assertEquals(expected, actual);
    }

    @After
    public void end(){
        System.setErr(systemOut);
        System.setOut(systemOut);
    }
}