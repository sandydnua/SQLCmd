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
    private static final String NAME_FILE_XML = "help.xml";
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
        DataSet data = new DataSet();
        HelpReader helpReader;
        try {
            helpReader = new XMLHelpReader(NAME_FILE_XML);
        } catch (Exception e) {
            throw new MainProcessExeption("Не найден файл"+ NAME_FILE_XML + " или нарушена его структура. " + e.getMessage());
        }

        if(params.length == 2) {
            data.addString(helpReader.getCommandDescription(params[1]));
        }else {
            data.addString(helpReader.getGeneralDescription());
            data.addString("Реализованне команды:");
            data.addString(helpReader.getListSupportedComnads());
        }
        return data;
    }

    @Override
    protected void canExecute() throws CanExecuteExeption {
    }


}
