package sandy.sqlcmd.services;

import sandy.sqlcmd.model.DataSet;

import javax.servlet.http.HttpServletRequest;

public class Services {

    public static String[][] getTable(DataSet data) {
        int quantityRows = data.quantityRows();
        int quantityColuns = data.quantityFieldsInRow(0);

        String[][] table = new String[quantityRows][quantityColuns];
        for (int i = 0; i < quantityRows; i++) {
            for (int j = 0; j < quantityColuns; j++) {
                table[i][j] = data.getField(i,j);
            }
        }
        return table;
    }

    public static String[] BuilCommandString(String action, HttpServletRequest req) {
        if (action.equals("tables") || action.equals("disconnect")) {
            return new String[]{action};
        } else if(action.equals("find") || action.equals("drop")) {
            req.setAttribute("tablename",req.getParameter("table"));
            return new String[]{action, req.getParameter("table")};
        } if(action.equals("insert") || action.equals("delete")) {
            String[] fields = req.getParameterValues("fields");
            String[] values = req.getParameterValues("values");
            String table = req.getParameter("table");
            String[] result = new String[fields.length*2+2];
            result[0] = action;
            result[1] = table;
            for (int i = 0; i < fields.length; i++) {
                result[2+i*2] = fields[i];
                result[3+i*2] = values[i];
            }
            return result;
        } else if(action.equals("create")) {
            String[] fields = req.getParameterValues("fields");
            String table = req.getParameter("table");
            String[] result = new String[fields.length + 2];
            result[0] = action;
            result[1] = table;
            for (int i = 0; i < fields.length; i++) {
                result[i + 2] = fields[i];
            }
            return result;
        }
        return null;
    }
}
