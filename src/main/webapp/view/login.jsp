<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Connect</title>
        <link href='resources/css/bootstrap.css' rel='stylesheet' type='text/css'>
        <link href='resources/css/bootstrap-grid.css' rel='stylesheet' type='text/css'>
        <style href="resources/css/bootstrap.css"></style>
        <style href="resources/css/bootstrap-grid.css"></style>
    </head>
    <body>
    <%
        String ErrorLogin = (String)request.getAttribute("ErrorLogin");
        if( ErrorLogin != null) {
    %>
        <div class="alert alert-danger text-center" role="alert">
            <h3><%=ErrorLogin%></h3>
        </div>
    <%
        }
    %>

        <div class="container">
            <div class="row" style="height: 100px"></div>
            <div class="row ">
                <div class="col-md">
                    <form action="login" class="form-horizontal" method="post" role="form">
                        <div class="form-row  justify-content-md-center">
                            <div class="form-group col-md-3">
                                <label >Login</label>
                                <input class="form-control" type="text" name="login" value="admin"/>
                            </div>
                        </div>
                        <div class="form-row   justify-content-md-center">
                            <div class="form-group col-md-3">
                                <label>Password</label>
                                <input class="form-control" type="password" name="password" value="7561"/>
                            </div>
                        </div>

                        <div class="form-row  justify-content-md-center">
                            <div class="form-group col-md-3">
                                <input class="btn btn-primary" type="submit" value="Connect"/>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
