<%@ page import="java.util.List" %>
<%@ page import="sandy.sqlcmd.controller.web.HelpInformation" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Help</title>
</head>
<body>

<p>
    Справка <p>
<p>
<table border="1">
    <%
        List<HelpInformation> list= (List<HelpInformation>) request.getAttribute("help");

        String[][] table;
        if ( null == list || list.size() == 0) {
            table = new String[][]{{}};
        } else {
            table = new String[list.size()+1][];
        }

        table[0] = new String[]{"id", "Name", "Description"};
        for(int i = 0; i < list.size(); i++) {
            HelpInformation item = list.get(i);
            table[i+1] = new String[]{String.valueOf(item.getId()), item.getCommand(), item.getDescription()};
        }
        StringBuilder row = new StringBuilder("<tr>");

        if (table != null) for (int i = 0; i < 3; i++) {
            row.append("<th>");
            row.append(table[0][i]);
            row.append("</th>");
        }
        row.append("<th></th>");
        row.append("<th></th></tr>");

        if (table != null) for (int i = 1; i < table.length; i++) {
            row.append("<tr>");
            row.append(String.format("<form id=\"delHelp%d\" action=\"deleteHelp\" method=\"post\">", i));
            row.append(String.format("<form id=\"upHelp%d\" action=\"updateHelp\" method=\"post\">", i));
            row.append("<td>");


            row.append(String.format("<input form=\"upHelp%d\" type=\"text\" name=\"id\" value=\"%s\" hidden/>", i, table[i][0]));
            row.append(String.format("<input form=\"delHelp%d\" type=\"text\" name=\"id\" value=\"%s\" hidden/>",i,  table[i][0]));
            row.append(String.format("<input form=\"default\" type=\"text\" name=\"id\" value=\"%s\" disabled/>",i,  table[i][0]));
            row.append("</td>");

            row.append("<td>");
            row.append(String.format("<input form=\"upHelp%d\" type=\"text\" name=\"command\" value=\"%s\"/>", i, table[i][1]));
            row.append("</td>");

            row.append("<td>");
            row.append(String.format("<input form=\"upHelp%d\" type=\"text\" name=\"description\" value=\"%s\"/>", i, table[i][2]));
            row.append("</td>");

            row.append("<td>");

            row.append(String.format("<input   form=\"delHelp%d\" type=\"submit\" value=\"Delete\"/>", i));
            row.append("</form></td>");

            row.append("<td>");

            row.append(String.format("<input   form=\"upHelp%d\" type=\"submit\" value=\"Update\"/>", i));
            row.append("</form></td>");
            row.append("</tr>");


        }
        row.append("<tr><form action=\"insertHelp\" method=\"post\"/>");

        row.append("<td>");
        row.append("</td>");

        row.append("<td>");

        row.append("<input type=\"text\" name=\"command\" />");
        row.append("</td>");

        row.append("<td>");

        row.append("<input type=\"text\" name=\"description\" />");
        row.append("</td>");

        row.append("<td><input type=\"submit\" value=\"Insert\"/>");
        row.append("</td>");

        row.append("<td></td>");
        row.append("</form></tr>");
    %>
    <%=row.toString()%>
</table>

</body>
</html>

