<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href='resources/css/bootstrap.css' rel='stylesheet' type='text/css'>
    <link href='resources/css/bootstrap-grid.css' rel='stylesheet' type='text/css'>
    <style href="resources/css/bootstrap.css"></style>
    <style href="resources/css/bootstrap-grid.css"></style>
    <title>Ajax</title>
    <style type="text/css">

    </style>
    <script src="resources/js/jquery.js"></script>
    <script src="resources/js/jquery.tmpl.js"></script>
    <script src="resources/js/main.js"></script>

    <script type="text/javascript">
        $(window).on('load', function(){
            showTables();
        });
    </script>

</head>
<body>
    <div id="mainContainer" class="container-fluid" >
        <div style="height: 30px" class="row">
        </div>
        <div class="row">
            <div class="col-md-2 row"></div>
            <div class="col-md-1"  style="background-color: #FFFFFF">
                    <div class="row">
                        <button type="button" class="btn btn-outline-info btn-lg btn-block" onclick="showTables()">
                            Show Tables
                        </button>
                    </div>
                    <p>
                    <div class="row">
                        <button type="button" class="btn btn-outline-info btn-lg btn-block" onclick="show('createtable')">
                            Create Table
                        </button>
                    </div>
                    <p>
                    <div class="row">
                        <a href="help" class="btn btn-outline-info btn-lg btn-block">
                            Cool Help
                        </a>
                    </div>
                    <p>
                    <div class="row">
                        <a href="edithelp" class="btn btn-outline-info btn-lg btn-block">
                            Edit Cool Help
                        </a>
                    </div>
                    <p>
                    <div class="row">
                        <button type="button" class="btn btn-outline-danger btn-lg btn-block" onclick="disconnect()">
                            Disconnect
                        </button>
                    </div>
            </div>
            <div class="col-md-1 row"></div>
            <div class="col-md-4">
                <div id="tables">
                    <%@ include file="tables.jsp"%>
                </div>
                <%@ include file="createtableajax.jsp"%>
                <%@ page contentType="text/html;charset=UTF-8" language="java" %>
            </div>
        </div>
        <div style="height: 20px" class="row">
        </div>
        <div class="row">
            <div class="col-md-3"></div>
            <div >
                <div id="find"></div>
            </div>
        </div>
        <br>
    </div>
</body>

</html>
