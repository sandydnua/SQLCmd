<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ajax</title>
    <style>
        td#find  {
            border-collapse: collapse;
        }
        td, th {
            vertical-align: top;
            border: 0px;
            padding: 0px;
        }
        th {
            text-align: center;
            padding-left: 10px;
            background-color: rgba(212, 226, 226, 0.23);
            color: rgba(116, 116, 116, 0.72);
        }
    </style>
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

    <div id="mainDiv" >
        <div id="menu">
            <p><button  onclick="showTables()">Таблицы</button></p>
        </div>
        <%@ include file="tablesajax.jsp"%>
        <%@ include file="createtableajax.jsp"%>
        <div id="find"></div>
    </div>
    <p>
    <form action="disconnect" method="post">
        <input type="submit" value="Disconnect">
    </form>
    <a href="help">Справка</a><br>
    <a href="" onclick="hideAll()">Главное меню</a>

</body>

</html>
