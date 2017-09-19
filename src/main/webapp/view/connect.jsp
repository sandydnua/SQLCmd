<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Connect</title>
</head>
<body>
    <%
        String ErrorConnect = (String)request.getAttribute("ErrorConnect");
        if( ErrorConnect != null) {
            %><%=ErrorConnect%><%
        }
    %>
    <form action="connect" method="post">
        <table>
            <tr>
                <td>Адрес</td>
                <td><input type="text" name="addres" value="localhost"/></td>
            </tr>
            <tr>
                <td>Порт</td>
                <td><input type="text" name="port" value="5432"/></td>
            </tr>
            <tr>
                <td>Имя БД</td>
                <td><input type="text" name="dbName" value="postgres"/></td>
            </tr>
            <tr>
                <td>Логин</td>
                <td><input type="text" name="login" value="postgres"/></td>
            </tr>
            <tr>
                <td>Пароль</td>
                <td><input type="password" name="pass" value="7561"/></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="Connect"/></td>
            </tr>
        </table>
    </form>
</body>
</html>
