package sandy.sqlcmd.services;

import org.junit.Test;
import org.mockito.Mock;
import sandy.sqlcmd.model.DataSet;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicesTest {
    @Mock
    HttpServletRequest request = mock(HttpServletRequest.class);

    @Test
    public void toTable() throws Exception {
        DataSet actuallyDataSet = new DataSet();
        int indexRow = actuallyDataSet.addRow();
        actuallyDataSet.addField(indexRow, "first-first");
        actuallyDataSet.addField(indexRow, "second-first");

        indexRow = actuallyDataSet.addRow();
        actuallyDataSet.addField(indexRow, "first-second");
        actuallyDataSet.addField(indexRow, "second-second");

        String[][] expected = {{"first-first", "second-first"},{"first-second", "second-second"}};

        Object[][] actually = Services.toTable(actuallyDataSet);
        assertArrayEquals(expected, actually);
    }

    @Test
    public void buildStringOfCommandsDrop() throws Exception {
        String tableName = "tableName";
        String action = "drop";
        when(request.getServletPath()).thenReturn("/" + action);
        when(request.getParameter("table")).thenReturn(tableName);

        String[] expected = {action, tableName};
        String[] actually = Services.buildStringOfCommand(request);
        assertArrayEquals(expected, actually);

    }
    @Test
    public void buildStringOfCommandsTables() throws Exception {
        String action = "tables";
        when(request.getServletPath()).thenReturn("/" + action);

        String[] expected = {action};
        String[] actually = Services.buildStringOfCommand(request);
        assertArrayEquals(expected, actually);
    }
    @Test
    public void buildStringOfCommandConnect() throws Exception {
        String[] expected = {"connect", "localhost:27270", "sqlCmd", "admin", "7561"};

        when(request.getServletPath()).thenReturn("/connect");
        when(request.getParameter("address")).thenReturn("localhost");
        when(request.getParameter("port")).thenReturn("27270");
        when(request.getParameter("dbName")).thenReturn("sqlCmd");
        when(request.getParameter("login")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("7561");

        String[] actually = Services.buildStringOfCommand(request);
        assertArrayEquals(expected, actually);

    }
    @Test
    public void buildStringOfCommandUpdate() throws Exception {
        String[] expected = {"update", "tableName", "field", "data", "field", "newData"};
        String[] fields = {"field"};
        String[] values = {"data"};
        String[] valuesNew = {"newData"};

        when(request.getServletPath()).thenReturn("/update");
        when(request.getParameterValues("fields")).thenReturn(fields);
        when(request.getParameterValues("values")).thenReturn(values);
        when(request.getParameterValues("valuesNew")).thenReturn(valuesNew);
        when(request.getParameter("table")).thenReturn("tableName");

        String[] actually = Services.buildStringOfCommand(request);
        assertArrayEquals(expected, actually);

    }
    @Test
    public void buildStringOfCommandInsert() throws Exception {
        String[] expected = {"insert", "tableName", "field", "data"};
        String[] fields = {"field"};
        String[] values = {"data"};

        when(request.getServletPath()).thenReturn("/insert");
        when(request.getParameterValues("fields")).thenReturn(fields);
        when(request.getParameterValues("values")).thenReturn(values);
        when(request.getParameter("table")).thenReturn("tableName");

        String[] actually = Services.buildStringOfCommand(request);
        assertArrayEquals(expected, actually);

    }
    @Test
    public void buildStringOfCommandCreate() throws Exception {
        String[] expected = {"create", "tableName", "field_1", "field_2"};
        String[] fields = {"field_1", "field_2"};

        when(request.getServletPath()).thenReturn("/create");
        when(request.getParameterValues("fields")).thenReturn(fields);
        when(request.getParameter("table")).thenReturn("tableName");

        String[] actually = Services.buildStringOfCommand(request);
        assertArrayEquals(expected, actually);

    }
}