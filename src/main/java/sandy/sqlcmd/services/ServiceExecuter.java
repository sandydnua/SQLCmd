package sandy.sqlcmd.services;

import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;

public class ServiceExecuter implements Executer {
    private DatabaseManager databaseManager;

    @Override
    public void setDbManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    @Override
    public DataSet getTables() {
        return databaseManager.getTables();
    }
}
