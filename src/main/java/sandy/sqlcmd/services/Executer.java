package sandy.sqlcmd.services;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;

public interface Executer {
    void setDbManager(DatabaseManager databaseManager);
    DataSet getTables();
}
