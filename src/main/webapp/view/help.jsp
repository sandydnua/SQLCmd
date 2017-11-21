<%--
<%@ page import="java.util.Map" %>
<%@ page import="sandy.sqlcmd.model.entity.HelpTranslation" %>
<%@ page import="java.util.List" %>
<%@ page import="sandy.sqlcmd.model.entity.Language" %>
<%@ page import="static org.hibernate.sql.InFragment.NULL" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Help</title>
    <script src="resources/js/helplib.js"></script>
    <script src="resources/js/jquery-3.2.1.js"></script>
</head>
<body>
<%
    List<Language> languages = (List<Language> ) request.getAttribute("languages");
    for (Language language : languages) {
        String line = new String(String.format("<p><input name=\"languageId\" type=\"radio\" onclick=\"checkLanguage(%d)\" />%s</p>", language.getId(), language.getLanguage()));
        out.println(line);
    }
%>

<div id="translations"></div>


<table border="1">
    <tr>
        <th>Id</th>
        <th>Command</th>
        <th>Format</th>
        <th>Description</th>
        <th></th>
    </tr>
    <%
        List<HelpTranslation> helpTranslation = (List<HelpTranslation> ) request.getAttribute("help");

        for(int i = 0; i < helpTranslation.size(); i++) {
            out.println("<tr>");

            HelpTranslation help = helpTranslation.get(i);
            out.println(String.format("<form id=\"upTranstation_%d\" action=\"updateTranslation\" method=\"post\"></form>",i));

            out.println("<td>");
            out.println(String.format("<input form=\"upTranstation_%d\" type=\"text\" value=\"%d\" name=\"idTranslation\" hidden>",i, help.getId()));
            out.println(help.getId());
            out.println("</td>");

            out.println("<td>");
            out.println(help.getCommand().getCommandName());
            out.println("</td>");

            out.println("<td>");
            out.println(help.getCommand().getFormat());
            out.println("</td>");

            out.println("<td>");
            out.println(String.format("<input form=\"upTranstation_%d\" type=\"text\" value=\"%s\" name=\"description\">",i, help.getDescription()));
            out.println("</td>");

            out.println("<td>");
            out.println(String.format("<input form=\"upTranstation_%d\" type=\"submit\" value=\"Update\" >",i));
            out.println("</td>");

            out.println("</tr>");
        }
    %>
</table>

</body>
</html>
--%>
