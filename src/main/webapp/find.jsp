<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find</title>
</head>
<body>
<a href="mainpage">На главную</a><br>
<%  String[][] table = (String[][])request.getAttribute("table");
    if (table == null)
%>
<p>
Таблица ${tablename}
<p>
<table border="1">
    <%
        StringBuilder head = new StringBuilder("<tr>");
        List<String> fields = new ArrayList();
        StringBuilder fieldsSet = new StringBuilder();

        if (table != null) for (int i = 0; i < table[0].length; i++) {
            head.append("<th>");
            head.append(table[0][i]);
            head.append("</th>");
            fields.add(table[0][i]);
            fieldsSet.append(String.format("fields=%s&", table[0][i]));
        }
        head.append("<th></th>");
        head.append("</tr>");
    %>
    <%=head.toString()%>
    <%
        if (table != null) for (int i = 1; i < table.length; i++) {
            StringBuilder row = new StringBuilder("<tr>");
            StringBuilder valuesSet = new StringBuilder();
            for (int j = 0; j < table[i].length; j++) {
                row.append("<td>");
                row.append(table[i][j]);
                valuesSet.append(String.format("values=%s&", table[i][j]));
                row.append("</td>");
            }
            row.append(String.format("<td><a href=\"delete?table=%s&%s%s\">Удалить строку</a></td>", request.getAttribute("tablename"), fieldsSet.toString(), valuesSet.toString()));
            row.append("</tr>");
            %>
            <%=row.toString()%>
            <%
        }
        StringBuilder insert = new StringBuilder("<tr><form action=\"insert\" method=\"get\">");
        if (table != null) for (int i = 0; i < table[0].length; i++) {
            insert.append("<td>");
            insert.append(String.format("<input type=\"text\" name=\"values\"/>"));
            insert.append(String.format("<input type=\"text\" name=\"fields\" value=\"%s\"hidden/>",table[0][i]));
            insert.append("</td>");
        }
        insert.append(String.format("<input type=\"text\" name=\"table\" value=\"%s\" hidden/>", request.getAttribute("tablename")));
        insert.append("<td><input type=\"submit\" value=\"Insert\"/></td></tr>");
        insert.append("</tr>");
            %>
    <%=insert.toString()%>
</table>
<%--<input type="submit" value="Insert"/>--%>
</form>
<p>
<a href="mainpage">На главную</a>
</body>
</html>

