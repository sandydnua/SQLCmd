<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    Главная страница.<br>
    <p>Активные подключения:
    <%
        String database = (String)request.getSession(true).getAttribute("database");
        if(database == null) {
            database = "Нет активного подключения";
        }
        out.println(database);
    %>
    <br>
    <p>
    <a href="connect">Connect</a><br>
    <form action="disconnect" method="post">
        <input type="submit" value="Disconnect"/>
    </form>
    <a href="tables">Tables</a><br>
    <a href="help">Help</a>
</body>
</html>
