package sandy.sqlcmd.model;

public interface HelpReader {
    String[] getGeneralDescription() throws MainProcessExeption;
    String[] getListSupportedComnads() throws MainProcessExeption;
    String[] getCommandDescription(String commandName) throws MainProcessExeption;
}
