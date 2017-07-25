package sandy.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class XMLHelpReaderTest {

    private String inputString;

    @Before
    public void setup() {
        String lineSeparator = System.getProperty("line.separator");

        inputString = "<root>" + lineSeparator +
                "\t<description>" + lineSeparator +
                "\t\t<description>" + lineSeparator +
                "Команды можно вводить в любом регистре: help, Help, HELP, hElP." + lineSeparator +
                "Для справки по конкретной команде введите help commandName." + lineSeparator +
                "Если параметр состоит из нескольких слов, то такую строку надо заключить в кавычки." + lineSeparator +
                "\t\t</description>" + lineSeparator +
                "\t</description>" + lineSeparator +
                "<main>\t" + lineSeparator +
                "\t<exit title = \"Завершение работы\"><description>" + lineSeparator +
                "\t\tКоманда завершает работу." + lineSeparator +
                "\t\t</description>" + lineSeparator +
                "\t</exit>" + lineSeparator +
                "</main>" + lineSeparator +
                "</root>" + lineSeparator;
    }

    @Test
    public void testGetGeneralDescriptionDefaultFile() throws Exception {

        String fileName = "help.xml";
        String[] expected = {"Команды можно вводить в любом регистре: help, Help, HELP, hElP.\n" +
                "Для справки по конкретной команде введите help commandName.\n" +
                "Если параметр состоит из нескольких слов, то такую строку надо заключить в кавычки.\n" +
                "Имена таблиц и столбцов надо вводить в нижнем регистре."};

        HelpReader reader = new XMLHelpReader(fileName);
        String[] actual = reader.getGeneralDescription();

        assertArrayEquals( expected, actual );
    }

    @Test
        public void testGetGeneralDescription() throws Exception {

            String[] expected = {"Команды можно вводить в любом регистре: help, Help, HELP, hElP.\n" +
                "Для справки по конкретной команде введите help commandName.\n" +
                "Если параметр состоит из нескольких слов, то такую строку надо заключить в кавычки."};

            DocumentBuilder documentBuilder;
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));

            HelpReader reader = new XMLHelpReader(documentBuilder.parse(inputStream));
            String[] actual = reader.getGeneralDescription();

            assertArrayEquals( expected, actual );
        }

    @Test
    public void getListSupportedCommands() throws Exception {

        String[] expected = {"exit - Завершение работы"};

        DocumentBuilder documentBuilder;
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));

        HelpReader reader = new XMLHelpReader(documentBuilder.parse(inputStream));
        String[] actual = reader.getListSupportedCommands();

        assertArrayEquals( expected, actual );
    }

    @Test
    public void getCommandDescription() throws Exception {

        String commandName = "exit";
        String[] expected = {"exit", "Команда завершает работу."};

        DocumentBuilder documentBuilder;
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));

        HelpReader reader = new XMLHelpReader(documentBuilder.parse(inputStream));
        String[] actual = reader.getCommandDescription(commandName);

        assertArrayEquals( expected, actual );
    }

    @Test
    public void getUnknownCommandDescription() throws Exception {

        String commandName = "fignya";
        String[] expected = {"Справки по такой команде нет"};

        DocumentBuilder documentBuilder;
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));

        HelpReader reader = new XMLHelpReader(documentBuilder.parse(inputStream));
        String[] actual = reader.getCommandDescription(commandName);

        assertArrayEquals( expected, actual );
    }

}