
function languages() {
    $.getJSON("languages").done(function (data) {
        $("#languages").empty();
        $("#current-languages").empty();

        $('#currentLanguagesTmpl').tmpl(data).appendTo('#current-languages');
        var lang = new Array();
        lang[0] = data;

        $('#languagesTmpl').tmpl(lang).appendTo('#languages');
    });
};

function insertLanguage() {
    var shortName = $("input[name='shortName']").val();
    var language = $("input[name='language']").val();
    setTimeout(languages, 30);
    $.post("insertLanguage", {language: language, shortName: shortName});
};

var deleteLanguage = function (id) {
    setTimeout(languages, 50);
    $.post("deleteLanguage", {id:id});
};

var checkLanguage = function ( idLanguage ) {
    $.getJSON("getHelpTranslations" , {language: idLanguage}).done( function ( data) {

        var table = $("#translations");
        table.empty();
        var htmlTable = "<table  border='1'>";
        htmlTable  += "<tr><th>Commad</th><th>Format</th><th>Description</th></tr>";
        for(var i = 0; i < data.length; i++) {
            htmlTable  += "<tr>";
            var item = JSON.parse(JSON.stringify(data[i]));
            htmlTable  += "<td>" + item.command.commandName + "</td>";
            htmlTable  += "<td>" + item.command.format + "</td>";
            htmlTable  += "<td>" + item.description + "</td>";
            htmlTable  += "</tr>";
        }
        htmlTable  += "</table>";
        table.append(htmlTable);
    });
};

var selectTranstationForEdit = function ( idLanguage ) {
    $.getJSON("getHelpTranslations" , {language: idLanguage}).done( function ( data) {

        var table = $("#translations-for-edit");
        table.empty();
        var htmlTable = "<table  border='1'>";
        htmlTable  += "<tr><th>Commad</th><th>Format</th><th>Description</th></tr>";
        for(var i = 0; i < data.length; i++) {
            htmlTable += "<tr>";
            var item = JSON.parse(JSON.stringify(data[i]));
            htmlTable  += "<td>" + item.command.commandName + "</td>";
            htmlTable  += "<td>" + item.command.format + "</td>";
            htmlTable  += "<td><input type='text' name='translation-" + item.id + "' value='" + item.description +
                                                "'/> <button onclick='saveTranslation(" + item.id + ")'>Save</button></td>";
            htmlTable  += "</tr>";
        }
        htmlTable  += "</table>";
        table.append(htmlTable);
    });
};

function saveTranslation(id) {
    var description = $("input[name='translation-" + id + "']").val();
    $.post("updateHelpTranslation", {id:id, description:description});
};