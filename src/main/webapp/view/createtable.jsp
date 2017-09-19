<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Table</title>
</head>
<body>

<%
    String[] fields = request.getParameterValues("fields");
    StringBuilder form = new StringBuilder("<form action=\"create\" method=\"post\">");
    String queryStr = new String("");
    form.append("Имя таблицы <input type=\"text\" name=\"table\" /><p>");
    if (fields != null) {
        for(int i = 0; i < fields.length; i++) {
            form.append(String.format("Имя поля <input type=\"text\" name=\"fields\" value=\"%s\"/><p>", fields[i]));
        }
        queryStr = request.getQueryString();
    }
    form.append("Добавить нужкое количество полей, потом ввести их названия.<p>");
    form.append(String.format("<a  href=\"createtable?%s&fields=\">Добавить поле</a><p>", queryStr));
    form.append("<input type=\"submit\" value=\"Создать таблицу\"/>");
    form.append("</form>");
%>
<%=form.toString()%>
</body>
</html>
