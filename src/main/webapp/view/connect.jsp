<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href='resources/css/bootstrap.css' rel='stylesheet' type='text/css'>
    <link href='resources/css/bootstrap-grid.css' rel='stylesheet' type='text/css'>
    <style href="resources/css/bootstrap.css"></style>
    <style href="resources/css/bootstrap-grid.css"></style>
    <script src="resources/js/jquery.js"></script>
    <script src="resources/js/main.js"></script>
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
        <form id="dataForConnect" class="form-horizontal" role="form">
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label>Address</label>
                    <input form="dataForConnect" class="form-control" type="text" name="address" value="localhost"/>
                </div>
                <div class="form-group col-md-6">
                    <label>Port</label>
                    <input form="dataForConnect" class="form-control col-md-5" type="text" name="port" value="27017"/>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label>Name Database</label>
                    <input form="dataForConnect" class="form-control" type="text" name="dbName" value="sqlcmd"/>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group col-md-6">
                    <label>Login</label>
                    <input form="dataForConnect" class="form-control" type="text" name="login" value="admin"/>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label >Password</label>
                    <td><input form="dataForConnect" class="form-control" type="password" type="text" name="password" value="7561"/></td>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group col-md-6">
                    <button  type="button" class="btn btn-primary" onclick="connect()">Connect</button>
                </div>
             </div>

            <div class="form-row">
                <div class="form-group col-md-6">
                    <a href="help" >
                    <span class="badge badge-info">
                        Cool Help
                    </span>
                    </a>
                </div>
             </div>

        </form>
    </div>
</body>
</html>
