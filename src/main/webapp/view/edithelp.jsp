<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Help</title>
    <link href='resources/css/bootstrap.css' rel='stylesheet' type='text/css'>
    <link href='resources/css/bootstrap-grid.css' rel='stylesheet' type='text/css'>
    <style href="resources/css/bootstrap.css"></style>
    <style href="resources/css/bootstrap-grid.css"></style>

    <script src="resources/js/jquery.js"></script>
    <script src="resources/js/edithelp-lib.js"></script>
    <script src="resources/js/jquery.tmpl.js"></script>
    <script type="text/javascript">
        $(window).on('load', function(){
            initHelpEdit();
        });
    </script>
    <script id="currentLanguagesTmpl" type="text/x-jquery-tmpl">
        <%--<label>
             <input onclick=selectTranstationForEdit('{%= $data.id %}') value="{%= $data.id %}" id="currentLanguage" type="radio" name="radio"/>
            {%= $data.language %}
        </label>
        <br>--%>
        <label>
            <div class="input-group-text">
                <input unchecked onclick=selectTranstationForEdit('{%= $data.id %}') id="currentLanguage" type="radio" name="radio">{%= $data.language %}
            </div>
        </label><br>
    </script>
    <script id="languagesTmpl" type="text/x-jquery-tmpl">
         <table class="table-hover" id="languagesTable">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Language</th>
                    <th>Short name</th>
                    <th></th>
                </tr>
            </thead>
            {%each $data%}
                <tr>
                    {%each $value%}
                        <td>{%= $value %}</td>
                    {%/each%}
                    <td><button class="btn btn-danger" style="width: 100px" onclick='deleteLanguage("{%= $value.id %}")'>Delete</button></td>
                </tr>
            {%/each%}
            <tr>
                <td>
                    <form id='insertLanguage' action='insertLanguage' method='post'>
                </td>
                <td>
                    <input  class="form-control" type='text' name='language'/>
                </td>
                <td>
                    <input  class="form-control" type='text' name='shortName'/>
                </td>
                <td>
                    <button  class="btn btn-success"  style="width: 100px" onclick='insertLanguage()'>
                        Append
                    </button>
                    </form>
                </td>
            </tr>
        </table>
    </script>
 <script id="commandsTmpl" type="text/x-jquery-tmpl">
         <table class="table-hover">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Command</th>
                    <th>Format</th>
                    <th></th>
                </tr>
            </thead>
            {%each $data%}
                <tr>
                    {%each $value%}
                        <td>{%= $value %}</td>
                    {%/each%}
                    <td><button   class="btn btn-danger" style="width: 100px" onclick='deleteCommand("{%= $value.id %}")'>Delete</button></td>
                </tr>
            {%/each%}
            <tr>
                <td><form id='insertLanguage' action='insertCommand' method='post'></td>
                <td><input style="width: 100px" class="form-control" type='text' name='command'/></td>
                <td><input  style="width: 350px" class="form-control" type='text' name='format'/></td>
                <td><button  class="btn btn-success" style="width: 100px" onclick='insertCommand()'>Append</button></form></td>
            </tr>
        </table>
    </script>
    <script id="getHelpTranslationsTmpl" type="text/x-jquery-tmpl">
         <table  class="table table-hover">
            <thead>
                <tr>
                    <th>Command</th>
                    <th>Format</th>
                    <th colspan='2'>Discription</th>
                </tr>
            </thead>
            {%each $data%}
                <tr>
                    <td>{%= $value.command.commandName %}</td>
                    <td>{%= $value.command.format %}</td>
                    <td><input class="form-control" style="width: 300px" type='text' name='translation-{%= $value.id%}' value='{%= $value.description%}'/></td>
                    <td><button class="btn btn-success" style="width: 100px" onclick='saveTranslation({%= $value.id%})'>Save</button></td>
                </tr>
            {%/each%}
        </table>
    </script>

</head>
<body>
        <div class="container">
            <div class="row" style="height: 30px">

            </div>
            <br>
            <div class="row">
                <div class="col-md-5">
                    <h4>Supported lengueges</h4>
                    <div id='languages'></div>
                </div>
                <div class="col-md-1">
                </div>
                <div class="col-md-4">
                    <h4>Commands</h4>
                    <div id='commands'></div>
                </div>
            </div>
            <br>
            <div class="row text-center">
                <div class="col-md-3 text-left">
                    <h4>Select Language</h4>
                </div>
                <div id="current-languages"></div>
            </div>
            <div class="row text-center">
                <div class="col-md-10">
                    <div id="translations-for-edit"></div>
                </div>
            </div>


            <br>
            <a href="logout">Exit from edit mode</a>
            <br>
            <a href="help">View Help</a>
            <br>
            <a href="index">Main Page</a>

    </div>
</body>
</html>
