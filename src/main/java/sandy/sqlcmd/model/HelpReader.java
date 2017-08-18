package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.MainProcessException;

public interface HelpReader {

    String[] getGeneralDescription() throws MainProcessException;
//    String[] getListSupportedCommands() throws MainProcessException;

    String[][] getListAndShortDescriptionSupportedCommands() throws MainProcessException;

    String[] getCommandDescription(String commandName);
}
