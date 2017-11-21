<%--
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="tables">
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
                out.println(row.toString());
            }
        }
    %>
</table>
</div>--%>
