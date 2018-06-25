<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Connect</title>
</head>
<body>
<%
    String ErrorLogin = (String)request.getAttribute("ErrorLogin");
    if( ErrorLogin != null) {
%><%=ErrorLogin%><%
    }
%>
<form action="login" method="post">
    <table>
        <tr>
            <td>Login</td>
            <td><input type="text" name="login" value="admin"/></td>
        </tr>
        <td>Пароль</td>
        <td><input type="password" name="password" value="7561"/></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Signin"/></td>
        </tr>
    </table>
</form>
</body>
</html>
