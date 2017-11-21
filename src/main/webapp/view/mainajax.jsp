<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ajax</title>
    <script src="resources/js/jquery.js"></script>
    <script src="resources/js/jquery.tmpl.js"></script>
    <script src="resources/js/main.js"></script>

    <script type="text/javascript">
        $(window).on('load', function(){
            init();
        });
    </script>

</head>
<body>
<p> Main Page</p>
<%--
<%
    String currentPage = (String) session.getAttribute("currentPage");
    out.println(currentPage);

%>--%>
    <div id="mainDiv">
        <div id="menu">
            <p><button  onclick="show('tables')">Таблицы</button></p>

        </div>
        <%@ include file="tablesajax.jsp"%>
        <%@ include file="createtableajax.jsp"%>
        <%@ include file="findajax.jsp"%>
    </div>
    <p>
    <form action="disconnect" method="post">
        <input type="submit" value="Disconnect">
    </form>
    <a href="edithelp">Справка</a><br>
    <a href="" onclick="hideAll()">Главное меню</a>

</body>

</html>
