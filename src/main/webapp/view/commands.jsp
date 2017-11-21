<%--
<%@ page import="sandy.sqlcmd.model.entity.Language" %>
<%@ page import="java.util.List" %>
<%@ page import="sandy.sqlcmd.model.entity.Commands" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Help</title>
</head>
<body>
<%
    List<Commands> commands = (List<Commands> ) request.getAttribute("commands");
    out.println("<table border=\"1\">");
    out.println("<tr>");
    out.println("<th>Id</th>");
    out.println("<th>Команда</th>");
    out.println("<th>Формат</th>");
    out.println("<th></th>");
    out.println("</tr>");

    if ( null != commands) for (int i = 0; i < commands.size(); i++) {
        out.println("<tr>");
        out.println("<td>");
        out.println(String.valueOf(commands.get(i).getId()));
        out.println("</td>");

        out.println("<td>");
        out.println(commands.get(i).getCommandName());
        out.println("</td>");

        out.println("<td>");
        out.println(commands.get(i).getFormat());
        out.println("</td>");

        out.println("<td>");
        out.println(String.format("<form id=\"deleteCommand_%d\" action=\"deleteCommand\" method=\"post\">" +
                "<input type=\"text\" value=\"%d\" name=\"id\" hidden/>" +
                "<input type=\"submit\" value=\"Удалить\"/>" +
                "</form>", i, commands.get(i).getId()));
        out.println("</td>");
        out.println("</tr>");

    }
    out.println("<tr>");
    out.println("<td>");
    out.println("</td>");

    out.println("<td>");
    out.println("<input form=\"insertCommand\" type=\"text\" name=\"commandName\" />");
    out.println("</td>");

    out.println("<td>");
    out.println("<input form=\"insertCommand\" type=\"text\" name=\"format\" />");
    out.println("</td>");

    out.println("<td>");
    out.println("<input form=\"insertCommand\" type=\"submit\" value=\"Добавить\"/>");

    out.println("<form id=\"insertCommand\" action=\"insertCommand\" method=\"post\"></form>");
    out.println("</td>");
    out.println("</tr>");
%>
</body>
</html>
--%>
