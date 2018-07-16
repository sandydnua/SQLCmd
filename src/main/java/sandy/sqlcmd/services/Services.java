package sandy.sqlcmd.services;

import sandy.sqlcmd.model.DataSet;

import javax.servlet.http.HttpServletRequest;

public class Services {

    public static String[][] toTable(DataSet data) {

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

    public static String[] buildStringOfCommand(HttpServletRequest request) {
        String action = request.getServletPath();
        if (action.startsWith("/")) {
            action = action.substring(1);
        }
        switch (action){
            case "connect" :
                return getParametersConnectCommand(action, request);
            case "tables":
            case "disconnect":
                return new String[]{action};
            case "find":
            case "drop":
            case "clear":
                return new String[]{action, request.getParameter("table")};
            case "update":{
                return getParametersUpdateCommand(action, request);
            }
            case "insert":
            case "delete": {
                return getParametersDeleteAndInsertCommands(action, request);
            }
            case "create":
                return getParametersCreateCommand(action, request);
            default:
                return null;
        }
    }

    private static String[] getParametersConnectCommand(String action, HttpServletRequest req) {
        return new String[]{action,
                req.getParameter("address") + ":" + req.getParameter("port"),
                req.getParameter("dbName"),
                req.getParameter("login"),
                req.getParameter("password")};
    }

    private static String[] getParametersUpdateCommand(String action, HttpServletRequest req) {
        String[] fields = req.getParameterValues("fields");
        String[] values = req.getParameterValues("values");
        String[] valuesNew = req.getParameterValues("valuesNew");
        String table = req.getParameter("table");

        String[] result = new String[fields.length * 4 + 2];
        result[0] = action;
        result[1] = table;

        for (int i = 0; i < fields.length; i++) {
            result[2 + i * 4] = fields[i];
            result[3 + i * 4] = values[i];

            result[4 + i * 4] = fields[i];
            result[5 + i * 4] = valuesNew[i];
        }
        return result;
    }

    private static String[] getParametersDeleteAndInsertCommands(String action, HttpServletRequest req) {
        String[] fields = req.getParameterValues("fields");
        String[] values = req.getParameterValues("values");
        String table = req.getParameter("table");
        String[] result = new String[fields.length * 2 + 2];
        result[0] = action;
        result[1] = table;
        for (int i = 0; i < fields.length; i++) {
            result[2 + i * 2] = fields[i];
            result[3 + i * 2] = values[i];
        }
        return result;
    }

    private static String[] getParametersCreateCommand(String action, HttpServletRequest req) {
        String[] fields = req.getParameterValues("fields");
        String table = req.getParameter("table");

        String[] result;
        if (null == fields) {
            result = new String[2];
        } else {
            result = new String[fields.length + 2];
            for (int i = 0; i < fields.length; i++) {
                result[i + 2] = fields[i];
            }
        }
        result[0] = action;
        result[1] = table;
        return result;
    }
}
