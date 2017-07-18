package sandy.sqlcmd.model;

import org.junit.Test;
import org.mockito.Mock;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class XMLHelpReaderTest {
    String inputString = "<root>" +
            "\t<description>\n" +
            "\t\t<description>\n" +
            "\t\tКоманды можно вводить в любом регистре: help, Help, HELP, hElP. \n" +
            "Для справки по конкретной команде введите help | commandName\n" +
            "\t\t</description>\n" +
            "\t</description>\n" +
            "<main>\t\n" +
            "\t<exit title = \"Завершение работы\"><description>\n" +
            "\t\tКоманда завершает работу.\n" +
            "\t\t</description>\n" +
            "\t</exit>\n" +
            "</main>\n" +
            "</root>\n";

    @Test
    public void testGetGeneralDescriptionDefaultFile() throws Exception {
        String fileName = "help.xml";
        String[] expected = {"Команды можно вводить в любом регистре: help, Help, HELP, hElP. \n" +
                "Для справки по конкретной команде введите help | commandName"};
        HelpReader reader = new XMLHelpReader(fileName);
        String[] actual = reader.getGeneralDescription();
        assertArrayEquals( expected, actual );
    }
    @Test
        public void testGetGeneralDescription() throws Exception {
            String[] expected = {"Команды можно вводить в любом регистре: help, Help, HELP, hElP. \n" +
                    "Для справки по конкретной команде введите help | commandName"};

            DocumentBuilder documentBuilder = null;
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));

            HelpReader reader = new XMLHelpReader(documentBuilder.parse(inputStream));
            String[] actual = reader.getGeneralDescription();
            assertArrayEquals( expected, actual );
        }

    @Test
    public void getListSupportedComnads() throws Exception {
        String[] expected = {"exit - Завершение работы"};

        DocumentBuilder documentBuilder = null;
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));

        HelpReader reader = new XMLHelpReader(documentBuilder.parse(inputStream));
        String[] actual = reader.getListSupportedComnads();
        assertArrayEquals( expected, actual );

    }

    @Test
    public void getCommandDescription() throws Exception {
        String commandName = "exit";
        String[] expected = {"exit", "Команда завершает работу."};

        DocumentBuilder documentBuilder = null;
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));

        HelpReader reader = new XMLHelpReader(documentBuilder.parse(inputStream));
        String[] actual = reader.getCommandDescription(commandName);
        assertArrayEquals( expected, actual );

    }

}