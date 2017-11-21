package sandy.sqlcmd.controller.command;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.CantExecuteNoConnectionException;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.HelpReader;
import sandy.sqlcmd.model.XMLHelpReader;

public class CommandHelp extends Command {
    private static String NAME_FILE_XML = "help.xml";
    private static final int INDEX_OF_COMMAND_NAME = 1;

    public CommandHelp() {
    }

    public CommandHelp(String[] params ){
        super(params);
    }

    public CommandHelp(String fileName ) {
        super(new String[0]);
        NAME_FILE_XML = fileName;
    }

    @Override
    protected DataSet executeMainProcess() throws MainProcessException {
        int INDEX_OF_NAME = 0;
        int INDEX_OF_DESCRIPTION = 1;

        HelpReader helpReader;

        try {
            helpReader = new XMLHelpReader(NAME_FILE_XML);
        } catch (Exception e) {
            throw new MainProcessException( String.format("Не найден файл %s или нарушена его структура.", NAME_FILE_XML) );
        }

        DataSet data = new DataSet();

        if( params.length  > 1 ) {
            data.addString(helpReader.getCommandDescription(params[INDEX_OF_COMMAND_NAME]));
        }else {
            data.addString(helpReader.getGeneralDescription());
            data.addString("Реализованные команды:");
            String[][] table = helpReader.getListAndShortDescriptionSupportedCommands();
            for (int i = 0; i < table.length; i++) {
                data.addRow();
                data.addField(i, table[i][INDEX_OF_NAME]);
                data.addField(i, table[i][INDEX_OF_DESCRIPTION]);
            }
        }

        return data;
    }

    @Override
    protected void canExecute() throws CantExecuteNoConnectionException {
    }


}
