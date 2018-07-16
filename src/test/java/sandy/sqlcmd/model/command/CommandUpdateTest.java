package sandy.sqlcmd.model.command;

import org.junit.Before;
import org.junit.Test;
import sandy.sqlcmd.model.DataSet;
import sandy.sqlcmd.model.databasemanagement.DatabaseManager;
import sandy.sqlcmd.model.Exceptions.MainProcessException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CommandUpdateTest {

    private DatabaseManager dbManager;

    private Command command;
    private DataSet data;

    @Before
    public void setup() throws MainProcessException {

        String[] params = {"update", "tableName", "column", "value", "columnName", "columnValueNew",
                                                  "column_", "value_", "columnName_", "columnValueNew_"};

        dbManager = mock(DatabaseManager.class);
        command = new CommandUpdate();
        command.setParams(params);
        command.setDbManager(dbManager);

        when(dbManager.isConnect()).thenReturn(true);
        when(dbManager.existTable(anyString())).thenReturn(true);
        when(dbManager.existColumns(anyString(),anyInt(), anySet())).thenReturn(true);
    }

    @Test
    public void executeMainProcess() throws Exception {
        String tableName = "tableName";
        Map<String, String> conditions = new HashMap();
        conditions.put("column", "value");
        conditions.put("column_", "value_");

        data = new DataSet();
        data.addRow();
        data.addRow();


        when(dbManager.find(tableName, conditions)).thenReturn(data);

        DataSet expected = new DataSet("Эти строки будут обновлены");
        expected.addRow();
        expected.addRow();
        DataSet actual = command.execute();

        assertTrue(expected.equals(actual));

        verify(dbManager, times(1)).existTable("tableName");
        Set<String> columns = new HashSet<>();
        columns.add("column");
        columns.add("column_");
        columns.add("columnName");
        columns.add("columnName_");
        verify(dbManager, times(1)).existColumns("tableName",DatabaseManager.EXISTENCE_THESE_FIELDS, columns);
    }

    @Test
    public void executeMainProcessWhenRowNotFound() throws Exception {

        data = new DataSet();
        data.addRow();

        when(dbManager.find(anyString(), anyMap())).thenReturn(data);
        DataSet expected = new DataSet("С такими параметрами строки не найдены.");
        DataSet actual = command.execute();

        String tableName = "tableName";
        Map<String, String> condition = new HashMap<>();
        condition.put("column", "value");
        condition.put("column_", "value_");
        assertTrue(expected.equals(actual));
        verify(dbManager, times(1)).find(tableName, condition);
        verify(dbManager, times(0)).update(anyString(), anyMap(), anyMap());
    }


}