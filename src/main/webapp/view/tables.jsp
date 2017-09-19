<%@ page import="java.util.Map" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tables</title>
</head>
<body>
<%--TODO ccылку на главную страницу надо сделать универсальной и не привязывать к sqlcmd--%>
<a href="/sqlcmd">На главную</a><br>
<a href="createtable">Создать таблицу</a><br>
<p>
<table border="0">
    <tr>
        <th>Название</th>
        <th></th>
    </tr>
    <%
        String[][] table = (String[][]) request.getAttribute("table");

        if( table != null) {
            for (int i = 1; i < table.length; i++) {
                StringBuilder row = new StringBuilder("<tr>");
                row.append("<td>");
                row.append(String.format("<a href=\"find?table=%s\">%s</a>", table[i][0],table[i][0]));
                row.append("</td>");
                row.append("<td>");
                row.append(String.format("<form action=\"drop\" method=\"post\">" +
                                             "<input type=\"text\" name=\"table\" value=\"%s\" hidden/>" +
                                             "<input type=\"submit\" name=\"table\" value=\"Удалить\"/> " +
                                         "</form>", table[i][0]));
                row.append("</td>");
                row.append("</tr>");
            %>
            <%=row.toString()%>
            <%
            }
        }
    %>
</table>

<a href="/sqlcmd">На главную</a>
</body>
</html>
