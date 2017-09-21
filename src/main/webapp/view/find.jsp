<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find</title>
</head>
<body>
<a href="mainpage">На главную</a><br>

<p>
Таблица ${tablename}
<p>
<table border="0">
    <%
        String[][] table = (String[][])request.getAttribute("table");
        StringBuilder row = new StringBuilder("<tr>");

        if (table != null) for (int i = 0; i < table[0].length; i++) {
            row.append("<th>");
            row.append(table[0][i]);
            row.append("</th>");
        }
        row.append("<th></th>");
        row.append("<th></th></tr>");

        if (table != null) for (int i = 1; i < table.length; i++) {
            row.append(String.format("<tr><form id=\"delete%d\" action=\"delete\" method=\"post\">", i));
            row.append(String.format("<form id=\"update%d\" action=\"update\" method=\"post\">", i));

            for (int j = 0; j < table[i].length; j++) {
                row.append("<td>");
                row.append(String.format("<input form=\"update%d\" type=\"text\" name=\"valuesNew\" value=\"%s\"/>", i, table[i][j]));
                row.append(String.format("<input form=\"update%d\" type=\"text\" name=\"values\" value=\"%s\" hidden/>", i, table[i][j]));
                row.append(String.format("<input form=\"update%d\" type=\"text\" name=\"fields\" value=\"%s\" hidden/>", i, table[0][j]));

                row.append(String.format("<input form=\"delete%d\" type=\"text\" name=\"fields\" value=\"%s\" hidden/>", i, table[0][j]));
                row.append(String.format("<input form=\"delete%d\" type=\"text\" name=\"values\" value=\"%s\" hidden/>", i, table[i][j]));
                row.append("</td>");
            }
            row.append("<td>");
            row.append(String.format("<input  id=\"delete%d\" type=\"text\" name=\"table\" value=\"%s\" hidden/>", i, request.getAttribute("tablename")));
            row.append("<input   type=\"submit\" value=\"Delete\"/></td>");
            row.append("</form></td>");
//            row.append("</tr>");

            row.append("<td>");
            row.append(String.format("<input   type=\"text\" name=\"table\" value=\"%s\" hidden/>", request.getAttribute("tablename")));
            row.append("<input  type=\"submit\" value=\"Update\"/></td>");
            row.append("</form></td></tr>");


        }
        row.append("<tr><form action=\"insert\" method=\"post\">");
        if (table != null) for (int i = 0; i < table[0].length; i++) {
            row.append("<td>");
            row.append(String.format("<input type=\"text\" name=\"values\"/>"));
            row.append(String.format("<input type=\"text\" name=\"fields\" value=\"%s\" hidden/>",table[0][i]));
            row.append("</td>");
        }
        row.append(String.format("<input type=\"text\" name=\"table\" value=\"%s\" hidden/>", request.getAttribute("tablename")));
        row.append("<td><input type=\"submit\" value=\"Insert\"/></td>");
        row.append("<td></td>");
        row.append("</form></tr>");
            %>
    <%=row.toString()%>
</table>

</body>
</html>

