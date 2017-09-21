<%--
  Created by IntelliJ IDEA.
  User: efu
  Date: 21.09.2017
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    StringBuilder text = new StringBuilder();
    for (String item : (String[]) request.getAttribute("list")) {
        text.append(item);
        text.append("<p>");
    }
%>

Результат:<p>
<%=text%>

</body>
</html>
