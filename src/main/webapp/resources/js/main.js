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
function clearTable(tableName) {
    $.post('clear',{table: tableName}).done(showRowsFromTable(tableName));
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
    $.ajax({
        type: 'get',
        url: "find",
        data: {table: tableName},
        success: function (data) {
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
        },
        statusCode: {
            404: function(request, status, error) {
               alert("Table Not Found");
            }
        }
    });
}
function insertRow(tableName) {
    var data = $('#newRow').serialize();
    data += '&' + $("#fields").serialize();

    $.ajax({
        type: 'post',
        url: 'insert',
        data: data,
        success: function () {
            showRowsFromTable(tableName);
        },
        statusCode:{
            500: function () {
                alert("Error! Not inserted!");
            }
        }
    });
}

function createTable() {
    var tableName = $("input[name='tableName']").val();
    var dataPOST = 'table=' + tableName;
    $("input[name='fields']").each(function(){
        dataPOST += '&' + 'fields=' + $(this).val();
    });
    $.ajax({
        type: 'post',
        url: 'createTable',
        data: dataPOST,
        success: function () {
            showTables();
        },
        statusCode:{
            500: function () {
                alert("Error! Not created!");
            }
        }
    });
};
function addField() {
    $('#fieldsList').append("<input type='text' name='fields'/><br>");
}
function clearFieldsList() {
    $('#fieldsList').empty();
    addField();
}

function deleteTable(tableName) {
    $.post('dropTable',{table:tableName}).done(function(){
        showTables();
    });
};