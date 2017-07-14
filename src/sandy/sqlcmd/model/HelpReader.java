package sandy.sqlcmd.model;

import sandy.sqlcmd.model.Exceptions.MainProcessException;

public interface HelpReader {
    String[] getGeneralDescription() throws MainProcessException;
    String[] getListSupportedComnads() throws MainProcessException;
    String[] getCommandDescription(String commandName) throws MainProcessException;
}
