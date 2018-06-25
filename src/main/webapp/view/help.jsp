<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Help</title>
    <script src="resources/js/jquery.js"></script>
    <script src="resources/js/jquery.tmpl.js"></script>
    <script type="text/javascript">
        $(window).on('load', function(){
            $.getJSON("languages").done(function (data) {
                selectLanguage(data[0].id, data[0].language);
                $("#current-languages").empty();
                $('#currentLanguagesTmpl').tmpl(data).appendTo('#current-languages');
            });
        });
    </script>
    <script type="text/javascript">
        function selectLanguage(idLanguage, language) {
            $.getJSON("getHelpTranslations" , {language: idLanguage}).done( function ( data) {
                $('#currentLanguage').empty().append('Выбранный язык:' + language);
                var table = $("#helpTable").empty();
                var translations = new Array();
                translations[0] = data;
                $('#helpTranslationsTmpl').tmpl(translations).appendTo(table);
            });
        }
    </script>
    <script id="currentLanguagesTmpl" type="text/x-jquery-tmpl">
        <p><label>
                <input onclick=selectLanguage('{%= $data.id %}','{%= $data.language %}') type="radio" name="radio">{%= $data.language %}
        </label></p>
    </script>
    <script id="helpTranslationsTmpl" type="text/x-jquery-tmpl">
         <table border="1">
            <tr>
                <th>Команда</th>
                <th>Формат</th>
                <th>Описание</th>
            </tr>
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
    <div id="current-languages"></div>
    <br>
    <div id="help">
        <div id="currentLanguage"></div>
        <div id="helpTable"></div>
    </div>
    <br>
    <a href="edithelp">Редактировать справку</a>
    <br>
    <a href="index">Главное меню</a>

</body>
</html>
