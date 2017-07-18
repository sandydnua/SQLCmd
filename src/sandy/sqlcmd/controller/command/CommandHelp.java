package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.HelpReader;
import sandy.sqlcmd.model.XMLHelpReader;

public class CommandHelp extends Command {
    private static final String NAME_FILE_XML = "help.xml";
    DataSet data;

    public CommandHelp(String[] params){
        super(params);
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException {

        DataSet data = new DataSet();
        HelpReader helpReader;
        try {
            helpReader = new XMLHelpReader(NAME_FILE_XML);
        } catch (Exception e) {
            throw new MainProcessException("Не найден файл"+ NAME_FILE_XML + " или нарушена его структура. " + e.getMessage());
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
    protected void canExecute() throws CantExecuteException {
    }


}
