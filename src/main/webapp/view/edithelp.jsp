<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Help</title>
    <style type="text/css">
        @import url("resources/css/edithelp.css");

    </style>


    <script src="resources/js/jquery.js"></script>
    <script src="resources/js/edithelp-lib.js"></script>
    <script src="resources/js/jquery.tmpl.js"></script>
    <script type="text/javascript">
        $(window).on('load', function(){
            initHelpEdit();
        });
    </script>
    <script id="currentLanguagesTmpl" type="text/x-jquery-tmpl">
        <label>
             <input onclick=selectTranstationForEdit('{%= $data.id %}') value="{%= $data.id %}" id="currentLanguage" type="radio" name="radio"/>
            {%= $data.language %}
        </label>
        <br>
    </script>
    <script id="languagesTmpl" type="text/x-jquery-tmpl">
         <table border="1" id="languagesTable">
            <tr>
                <th>Id</th>
                <th>Язык</th>
                <th>Обозначние</th>
                <th></th>
            </tr>
            {%each $data%}
                <tr>
                    {%each $value%}
                        <td>{%= $value %}</td>
                    {%/each%}
                    <td><button  onclick='deleteLanguage("{%= $value.id %}")'>Удалить</button></td>
                </tr>
            {%/each%}
            <tr>
                <td><form id='insertLanguage' action='insertLanguage' method='post'></td>
                <td><input  type='text' name='language'/></td>
                <td><input  type='text' name='shortName'/></td>
                <td><button  onclick='insertLanguage()'>Добавить</button></form></td>
            </tr>
        </table>
    </script>
 <script id="commandsTmpl" type="text/x-jquery-tmpl">
         <table border="1">
            <tr>
                <th>Id</th>
                <th>Команда</th>
                <th>Формат</th>
                <th></th>
            </tr>
            {%each $data%}
                <tr>
                    {%each $value%}
                        <td>{%= $value %}</td>
                    {%/each%}
                    <td><button  onclick='deleteCommand("{%= $value.id %}")'>Удалить</button></td>
                </tr>
            {%/each%}
            <tr>
                <td><form id='insertLanguage' action='insertCommand' method='post'></td>
                <td><input  type='text' name='command'/></td>
                <td><input  type='text' name='format'/></td>
                <td><button  onclick='insertCommand()'>Добавить</button></form></td>
            </tr>
        </table>
    </script>
    <script id="getHelpTranslationsTmpl" type="text/x-jquery-tmpl">
         <table border="1">
            <tr>
                <th>Команда</th>
                <th>Формат</th>
                <th colspan='2'>Описание</th>
            </tr>
            {%each $data%}
                <tr>
                    <td>{%= $value.command.commandName %}</td>
                    <td>{%= $value.command.format %}</td>
                    <td><input type='text' name='translation-{%= $value.id%}' value='{%= $value.description%}'/></td>
                    <td><button onclick='saveTranslation({%= $value.id%})'>Save</button></td>
                </tr>
            {%/each%}
        </table>
    </script>

</head>
<body>
    <div id="centerMainDivEditHelp">
        <div>
        <table border="0">
                <tr>
                    <td style="vertical-align: top">
                        <p>Языки</p>
                        <div id='languages'></div></td>
                    <td style="vertical-align: top">
                        <p>Команды</p>
                        <div id='commands'></div></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <br><p>Выбери язык</p>
                        <div id="current-languages"></div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div id="translations-for-edit"></div>
                    </td>
                </tr>
            </table>
            <br>
            <a href="logout">Выйти из режима редактирования</a>
            <br>
            <a href="help">Смотреть справку</a>
            <br>
            <a href="index">Главное меню</a>
        </div>
    </div>
</body>
</html>
