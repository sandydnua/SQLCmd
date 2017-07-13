package sandy.sqlcmd.model;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.w3c.dom.Node.ELEMENT_NODE;

public class CommandHelp extends Command {
    private static final int FIRST_NODE = 0;
    private static final String nameFileXML = "help.xml";
    private static final int SHORT_HELP = 1;
    private static final int FULL_HELP = 0;
    DataSet data;

    public CommandHelp(){

    }
    public CommandHelp(String[] params){
        super();
        setParams(params);
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessExeption {
        DataSet data;
        if(params.length == 2) {
            data = helpForCommand();
        }else {
            data = fullHelp();
        }
        return data;
    }

    private DataSet helpForCommand() throws MainProcessExeption {
        return getTextHelpFormNode(params[1], FULL_HELP);
    }

    private DataSet fullHelp() throws MainProcessExeption {
        return getTextHelpFormNode("main", SHORT_HELP);
    }


    private DataSet getTextHelpFormNode(String nodeName, int format) throws MainProcessExeption {
        DataSet data = new DataSet();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(nameFileXML);
            if(format == SHORT_HELP) {
                data.addString(document.getElementsByTagName("description").item(FIRST_NODE).getTextContent().trim());
                data.addString("");
            }

            NodeList nodeList = document.getElementsByTagName(nodeName.toLowerCase()).item(FIRST_NODE).getChildNodes();

            int length = nodeList.getLength();
            for (int i = 0; i < length; i++) {
                if( format == SHORT_HELP){
                    if(nodeList.item(i).getNodeType() == ELEMENT_NODE ) {
                        data.addString(nodeList.item(i).getNodeName());
                    }
                }else  if(nodeList.item(i).getNodeType() == ELEMENT_NODE ) {
                    data.addString(nodeList.item(i).getParentNode().getNodeName());
                    data.addString(nodeList.item(i).getTextContent().trim());
                }
            }

        } catch(NullPointerException e){
            throw new MainProcessExeption("Ошибка чтения help.xml; Возможно не найдена команда; ");
        } catch (ParserConfigurationException e) {
            throw new MainProcessExeption("Ошибка чтения help.xml; "+e.getMessage());
        } catch (SAXException e) {
            throw new MainProcessExeption("Ошибка чтения help.xml; "+e.getMessage());
        } catch (IOException e) {
            throw new MainProcessExeption("Ошибка чтения help.xml; "+e.getMessage());
        }
        return  data;
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
    }


}
