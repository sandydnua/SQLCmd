package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.CanExecuteException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

public class CommandHelp extends Command {
    private static final int FIRST_NODE = 0;
    private static final String NAME_FILE_XML = "help.xml";
    private static final int SHORT_HELP = 1;
    private static final int FULL_HELP = 0;
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
    protected void canExecute() throws CanExecuteException {
    }


}
