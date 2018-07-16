function connect() {
    var data = $('#dataForConnect').serialize();
    $.ajax({
        type: 'post',
        url: "connect",
        data: data,
        success: function (data) {
            document.location.href = '/sqlcmd/';
        },
        statusCode: {
            500: function(request, status, error) {
                alert(extractMessageFromResponse(data));
                document.location.href = '/sqlcmd/';
            }
        }
    });
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
        for(i=0;i<tables.length;i=i+2){
            var arr = [];
            arr[0] = tables[i];
            if(i<tables.length-1){
                arr[1] = tables[i+1];
            }
            var couple = { couple : arr};
            $('#tablesTmpl').tmpl(couple).appendTo('#tablesList');
        }
        $('#tables').show();
    });
}

function clearTable(tableName) {
    $.post('clear',{table: tableName}).done(showRowsFromTable(tableName));
    showTables();
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

function disconnect() {
    $.ajax({
        type: 'post',
        url: "disconnect",
        success: function (data) {
            document.location.href = '/sqlcmd/';
        },
        statusCode: {
            404: function(request, status, error) {
                alert("Disconnect Error!!!");
            }
        }
    });

}

function showRowsFromTable(tableName) {
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
                alert(extractMessageFromResponse(data));
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
        url: 'create',
        data: dataPOST,
        success: function (data) {
            showTables();
        },
        statusCode:{
            500: function (data) {
                alert(extractMessageFromResponse(data));
            }
        }
    });
};

function extractMessageFromResponse(data) {
    var json = jQuery.parseJSON(JSON.stringify(data));
    var msg = jQuery.parseJSON(json["responseText"]);
    return msg[0][0];
}
function addFirstField() {
    $('#fieldsList').append("<input type='text' name='fields'/><br>");
}
function addField() {
    var id = Math.floor(Math.random() );
    $('#fieldsList').append("<div id='newField_" + id + "'>" +
                                "<input class='form-control' type='text' name='fields'/>&nbsp" +
                                    "<button type='button' class='btn btn-danger btn-sm' onClick='deleteField(" +id+ ")'>Delete Field</button>" +
                            "</div><br>");
}
function deleteField(id) {
    $('#newField_' + id).remove();
}

function deleteTable(tableName) {
    $.post('drop',{table:tableName}).done(function(){
        showTables();
    });
};