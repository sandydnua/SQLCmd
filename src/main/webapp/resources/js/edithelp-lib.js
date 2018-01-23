function initHelpEdit() {
    languages();
    commands();
}
function languages() {
    $.getJSON("languages").done(function (data) {
        var currentLanguage = $('input[name=radio]:checked').val();
        $("#languages").empty();
        $("#current-languages").empty();

        $('#currentLanguagesTmpl').tmpl(data).appendTo('#current-languages');
        var lang = new Array();
        lang[0] = data;
        if (currentLanguage === undefined ) {
            var currLangRadio = $('input:first[name="radio"]').prop('checked', true);
            currentLanguage = currLangRadio.val();
        } else {
            $('input[value="' + currentLanguage + '"]').prop('checked', true);
        }
        selectTranstationForEdit(currentLanguage);
        $('#languagesTmpl').tmpl(lang).appendTo('#languages');
    });
};

function insertLanguage() {
    var shortName = $("input[name='shortName']").val();
    var language = $("input[name='language']").val();
    $.post("insertLanguage", {language: language, shortName: shortName}).done(function () {
        initHelpEdit();
    });
};

var deleteLanguage = function (id) {
    $.post("deleteLanguage", {id:id}).done(function () {
        initHelpEdit();
    });
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

    $.post("insertCommand", {command: commandName, format: format}).done(function () {
        initHelpEdit();
    });

};

var deleteCommand = function (id) {
    $.post("deleteCommand", {id:id}).done(function () {
        initHelpEdit();
    });
};
