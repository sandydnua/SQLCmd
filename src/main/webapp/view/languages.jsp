<%@ page import="sandy.sqlcmd.model.entity.Language" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Help</title>
</head>
<body>
<%
    List<Language> languages = (List<Language> ) request.getAttribute("languages");
    out.println("<table border=\"1\">");
    out.println("<tr>");
    out.println("<th>Id</th>");
    out.println("<th>Язык</th>");
    out.println("<th>Обозначение</th>");
    out.println("<th></th>");
    out.println("</tr>");

    if ( null != languages) for (int i = 0; i < languages.size(); i++) {
        out.println("<tr>");
        out.println("<td>");
        out.println(String.valueOf(languages.get(i).getId()));
        out.println("</td>");

        out.println("<td>");
        out.println(languages.get(i).getLanguage());
        out.println("</td>");

        out.println("<td>");
        out.println(languages.get(i).getShortName());
        out.println("</td>");

        out.println("<td>");
        out.println(String.format("<form id=\"deleteLanguage_%d\" action=\"deleteLanguage\" method=\"post\">" +
                "<input type=\"text\" value=\"%d\" name=\"id\" hidden/>" +
                "<input type=\"submit\" value=\"Удалить\"/>" +
                "</form>", i, languages.get(i).getId()));
        out.println("</td>");
        out.println("</tr>");

    }
    out.println("<tr>");
    out.println("<td>");
    out.println("</td>");

    out.println("<td>");
    out.println("<input form=\"insertLanguage\" type=\"text\" name=\"language\" />");
    out.println("</td>");

    out.println("<td>");
    out.println("<input form=\"insertLanguage\" type=\"text\" name=\"shortName\" />");
    out.println("</td>");

    out.println("<td>");
    out.println("<input form=\"insertLanguage\" type=\"submit\" value=\"Добавить\"/>");

    out.println("<form id=\"insertLanguage\" action=\"insertLanguage\" method=\"post\"></form>");
    out.println("</td>");
    out.println("</tr>");
%>
</body>
</html>
