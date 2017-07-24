package sandy.sqlcmd.model;

import java.util.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.w3c.dom.Node.ELEMENT_NODE;

public class    XMLHelpReader implements HelpReader {

    private Document document;

    public XMLHelpReader(String nameFileXml) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilder documentBuilder;
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        document = documentBuilder.parse(nameFileXml);
    }
    public XMLHelpReader(Document document) {
        this.document = document;
    }

    @Override
    public String[] getGeneralDescription() throws MainProcessException {

        String[] result = new String[1];

        try {
            result[0] = document.getElementsByTagName("description").item(0).getTextContent().trim();

        }catch (Exception e) {
            throw new MainProcessException("Ошибка при получении общего описания.");
        }

        return result;
    }

    @Override
    public String[] getListSupportedCommands() throws MainProcessException {

        List<String> text = new ArrayList<>();
        NodeList nodeList;

        try{
            nodeList = document.getElementsByTagName("main").item(0).getChildNodes();
        }catch (Exception e){
            throw new MainProcessException("Ошибка получения списка команд. ");
        }

        int length = nodeList.getLength();
        for (int i = 0; i < length; i++) {
            if(nodeList.item(i).getNodeType() == ELEMENT_NODE ) {
                int quantityAttributes = nodeList.item(i).getAttributes().getLength();
                String title = "";
                if(quantityAttributes > 0){
                    title = nodeList.item(i).getAttributes().item(0).getNodeValue();
                }
                text.add(nodeList.item(i).getNodeName()+ " - " + title);
            }
        }
        String[] result = new String[text.size()];

        return  text.toArray(result);
    }

    @Override
    public String[] getCommandDescription(String commandName) {

        List<String> text = new ArrayList<>();
        NodeList nodeList;

        try {
            nodeList = document.getElementsByTagName(commandName.toLowerCase()).item(0).getChildNodes();
        }catch(Exception e) {
            return new String[]{"Справки по такой команде нет"};
        }

        int length = nodeList.getLength();

        for (int i = 0; i < length; i++) {
            if(nodeList.item(i).getNodeType() == ELEMENT_NODE ) {
                text.add(nodeList.item(i).getParentNode().getNodeName());
                text.add(nodeList.item(i).getTextContent().trim());
            }
        }
        String[] result = new String[text.size()];

        return  text.toArray(result);
    }

}
