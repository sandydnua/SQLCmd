<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="createtable">
<%
    String[] fields = request.getParameterValues("fields");
    StringBuilder form = new StringBuilder("<form action=\"create\" method=\"post\">");
    String queryStr = new String("");
    form.append("Имя таблицы <input type=\"text\" name=\"table\" /><p>");
    if (fields != null) {
        for (String field : fields) {
            form.append(String.format("Имя поля <input type=\"text\" name=\"fields\" value=\"%s\"/><p>", field));
        }
        queryStr = request.getQueryString();
    }
    form.append("Добавить нужкое количество полей, потом ввести их названия.<p>");
    form.append(String.format("<a  href=\"createtable?%s&fields=\">Добавить поле</a><p>", queryStr));
    form.append("<input type=\"submit\" value=\"Создать таблицу\"/>");
    form.append("</form>");
    out.println(form.toString());

%>
</div>
