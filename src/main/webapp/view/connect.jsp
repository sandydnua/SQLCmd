<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        #mainDiv {
            width: 100%;
            height: 100%;
            position: fixed;
            top: 0;
            left: 0;
            display: flex;
            align-items: center;
            align-content: center;
            justify-content: center;
            overflow: auto;
        }
        .block {
            background: #FFFFFF;
        }
    </style>
    <title>Connect</title>
</head>
<body>
    <% // TODO тут надо сделать красивее :)
        String ErrorConnect = (String)request.getAttribute("ErrorConnect");
        if( ErrorConnect != null) {
            %><%=ErrorConnect%><%
        }
    %>
    <div id="mainDiv">
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
    </div>
</body>
</html>
