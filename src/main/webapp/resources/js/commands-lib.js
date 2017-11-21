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

