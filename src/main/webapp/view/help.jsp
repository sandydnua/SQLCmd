<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Help</title>
    <link href='resources/css/bootstrap.css' rel='stylesheet' type='text/css'>
    <link href='resources/css/bootstrap-grid.css' rel='stylesheet' type='text/css'>
    <style href="resources/css/bootstrap.css"></style>
    <style href="resources/css/bootstrap-grid.css"></style>
    <script src="resources/js/jquery.js"></script>
    <script src="resources/js/jquery.tmpl.js"></script>
    <script type="text/javascript">
        $(window).on('load', function(){
            $.getJSON("languages").done(function (data) {
                selectLanguage(data[0].id, data[0].language);
                $('#currentLanguagesTmpl').tmpl(data).appendTo('#current-languages');
            });
        });
    </script>
    <script type="text/javascript">
        function selectLanguage(idLanguage, language) {
            $.getJSON("getHelpTranslations" , {language: idLanguage}).done( function ( data) {
                var table = $("#helpTable").empty();
                var translations = new Array();
                translations[0] = data;
                $('#helpTranslationsTmpl').tmpl(translations).appendTo(table);
            });
        }
    </script>
    <script id="currentLanguagesTmpl" type="text/x-jquery-tmpl">
        <label>
            <div class="input-group-text">
                <input onclick=selectLanguage('{%= $data.id %}','{%= $data.language %}') type="radio" name="radio">{%= $data.language %}
            </div>
        </label>
    </script>
    <script id="helpTranslationsTmpl" type="text/x-jquery-tmpl">
         <table class="table">
            <thead>
                <tr>
                    <th style="width: 100px">Command</th>
                    <th style="width: 200px">Format</th>
                    <th style="width: 600px">Discription</th>
                </tr>
            <thead>
            {%each $data%}
                <tr>
                    <td>{%= $value.command.commandName %}</td>
                    <td>{%= $value.command.format %}</td>
                    <td>{%= $value.description%}</td>
                </tr>
            {%/each%}
        </table>
    </script>
</head>
<body>
    <div class="row" style="height: 30px">
    </div>
    <div class="row">
        <div class="col-md-1"></div>
        <div class="col-md-1">
            <div>
                <a class="btn btn-outline-info btn-md btn-block" href="edithelp">Edit Help</a>
            </div>
            <br>
            <div>
                <a class="btn btn-outline-info btn-md btn-block" href="index">Main Page</a>
            </div>
        </div>
        <div class="col-md-1"></div>
        <div class="col-md-1">
            <div><h6>Select Language</h6></div>
            <div id="current-languages"></div>
        </div>
            <div id="help" class="col-md-7" >
                <div id="helpTable"></div>
            </div>
        </div>
    </div>
</body>
</html>
