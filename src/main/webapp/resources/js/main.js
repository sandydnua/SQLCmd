function init(){
    hideAll();
}
function show(namePart, tableName) {
    $('#menu').hide();
    switch (namePart) {
        case 'createtable':
            hideAll();
            $('#createtable').show();
            break;
        default:
            $('#menu').show();
    }
}
function hideAll(){
    $('#tables').hide();
    $('#createtable').hide();
    $('#find').hide();
}

function showTables() {
    hideAll();
    $.get("tables").done( function (tables) {
        tables.splice(0,1);
        $('#tablesList').empty();
        $('#tablesTmpl').tmpl(tables).appendTo('#tablesList');
        $('#tables').show();
    });
}

function disconnect() {
    $.post('disconnect');
}
function delRow(index) {
    changeRow(index, 'delete');
}

function updateRow(index) {
    changeRow(index, 'update');
}

function changeRow(index, mode) {
    tableName = $("input[name='table']").val();
    var data = $('#row_' + index).serialize();
    data +='&' + $('#fields').serialize();
    $.post(mode, data).done(function () {
        showRowsFromTable(tableName);
    });
}

function showRowsFromTable(tableName) {
    hideAll();
    $.get("find", {table: tableName}, function (data) {
        $('#find').empty();
        var header = data[0];
        data.splice(0,1);
        table =  {
                header: header,
                tableName: tableName,
                body: data
                };
        $('#findTmpl').tmpl(table).appendTo('#find');
        $('#find').show();
    });
}


function clearTable(tableName) {
    var data = $('#clear').serialize();
    $.post('clear',{data:data}).done(showRowsFromTable(tableName));
}
function insertRow(tableName) {
    var data = $('#newRow').serialize();
    data += '&' + $("#fields").serialize();

    $.post('insert',data).done( function(){
        showRowsFromTable(tableName);
    });
}

function createTable() {
    var tableName = $("input[name='tableName']").val();
    var dataPOST = 'table=' + tableName;
    $("input[name='fields']").each(function(){
        dataPOST += '&' + 'fields=' + $(this).val();
    });
    $.post('createTable',dataPOST).done( function () {
        showTables();
    });
};
function addField() {
    $('#fieldsList').append("<input type='text' name='fields'/><br>");
}
function clearFieldsList() {
    $('#fieldsList').empty();
    addField();
}
/*function deleteRow() {
    $(this).empty();
};*/
function deleteTable(tableName) {
    $.post('dropTable',{table:tableName}).done(function(){
        showTables();
    });
};