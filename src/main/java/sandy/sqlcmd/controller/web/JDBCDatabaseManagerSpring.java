package sandy.sqlcmd.controller.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.Exceptions.MainProcessException;
import sandy.sqlcmd.model.SQLConstructor;
import sandy.sqlcmd.model.SQLConstructorPostgre;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class JDBCDatabaseManagerSpring implements DatabaseManager {

    private DataSource dataSource;
    private JdbcTemplate template;

    @Override
    public SQLConstructor getSQLConstructor() {
        return new SQLConstructorPostgre();
    }

    @Override
    public void connect(String address, String database, String userName, String password)  {

        ApplicationContext context = new FileSystemXmlApplicationContext("src/main/webapp/application-context.xml");

        String driverClassName = "org.postgresql.Driver";
        String url = String.format("jdbc:postgresql://%s/%s?loggerLevel=OFF", address, database);
        dataSource = (DataSource) context.getBean("datasource", driverClassName, url, userName, password);

        template = new JdbcTemplate(dataSource);

    }

    @Override
    public void disconnect() {
        dataSource = null;
    }
    @Override
    public DataSet executeQuery(String sqlQuery) {

        SqlRowSet result = template.queryForRowSet(sqlQuery);
        return RowSetToDataSet(result);
    }

    private DataSet RowSetToDataSet(SqlRowSet result) {
        DataSet dataSet = new DataSet();

        int indexRow = dataSet.addRow();
        for(String columnName : result.getMetaData().getColumnNames()) {
            dataSet.addField(indexRow, columnName);
        }
        int columnCount = result.getMetaData().getColumnCount();
        result.beforeFirst();
        while (result.next()){
            indexRow = dataSet.addRow();
            for (int i = 1; i <= columnCount; i++) {
                dataSet.addField(indexRow, result.getString(i));
            }
        }

        return dataSet;

    }

    @Override
    public void executeUpdate(String sqlQuery) throws MainProcessException {
        template.update(sqlQuery);
    }

    @Override
    public boolean isConnect() {
        try {
            if (dataSource == null){
                return false;
            }
            Connection connection = template.getDataSource().getConnection();
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean existTable(String tableName) {

        if ( null == tableName || tableName.length() == 0) {
            return false;
        }

        SQLConstructor sqlConstructor = this.getSQLConstructor();
        sqlConstructor.addTables(tableName);
        DataSet data = executeQuery(sqlConstructor.getQueryExistTable());
        return data.quantityRows() > 1;
    }

    @Override
    public boolean existColumns(String tableName, int mode, String... columns) {

        SQLConstructor sqlConstructor= this.getSQLConstructor();
        sqlConstructor.addTables(tableName);

        DataSet data;
        try {
            data = this.executeQuery(sqlConstructor.getQuerySelect());
        } catch ( Exception ex) {
            return false;
        }

        if ( mode == FULL_COVERAGES && data.quantityFieldsInRow(NUMBER_OF_FIRST_ROW_IN_TABLE) != columns.length) {
            return false;
        }
        Set<String> setFromParameters = new HashSet<>(Arrays.asList(columns));
        Set<String> setFromTables = new HashSet<>();

        for (int i = 0; i < data.quantityFieldsInRow(NUMBER_OF_FIRST_ROW_IN_TABLE); i++) {
            setFromTables.add(data.getField(NUMBER_OF_FIRST_ROW_IN_TABLE, i));
        }
        int diffSize = 0;
            if( mode == FULL_COVERAGES) {

            setFromTables.removeAll(setFromParameters);
            diffSize = setFromTables.size();
        }
        if( mode == EXISTENCE_THESE_FIELDS ) {

            setFromParameters.removeAll(setFromTables);
            diffSize = setFromParameters.size();
        }

            return diffSize == 0;
    }

}
