
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

var selectTranstationForEdit = function ( idLanguage ) {
    $.getJSON("getHelpTranslations" , {language: idLanguage}).done( function ( data) {

        var table = $("#translations-for-edit").empty();
        var translations = new Array();
        translations[0] = data;
        $('#getHelpTranslationsTmpl').tmpl(translations).appendTo(table);
    });
};

function saveTranslation(id) {
    var description = $("input[name='translation-" + id + "']").val();
    $.post("updateHelpTranslation", {id:id, description:description});
};
function commands() {
    $.getJSON("commands").done(function (data) {
        $('#commands').empty();
        var commands = new Array();
        commands[0] = data;
        $('#commandsTmpl').tmpl(commands).appendTo('#commands');
    });
};

function insertCommand() {
    var commandName = $("input[name='command']").val();
    var format = $("input[name='format']").val();

    setTimeout(commands, 50);
    $.post("insertCommand", {command: commandName, format: format});

};

var deleteCommand = function (id) {
    setTimeout(commands, 50);
    $.post("deleteCommand", {id:id});
};
