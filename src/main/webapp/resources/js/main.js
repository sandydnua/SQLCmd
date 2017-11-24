function init(){
    hideAll();
}
function show(namePart, tableName) {
    $('#menu').hide();
    switch (namePart) {
        case 'tables':
            hideAll();
            $('#tablesList').empty();
            showTables();
            $('#tables').show();
            break;
        case 'find':
            hideAll();
            $('#find').empty();
            showRowsFromTable(tableName);
            $('#find').show();
            break;
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
    $.getJSON("tables", function (tables) {
        tables.splice(0,1);
        $('#tablesTmpl').tmpl(tables).appendTo('#tablesList');
    });
}

function disconnect() {
    $.post('disconnect');
}
function deleteRow(index) {
    tableName = $("input[name='table']").val();
    var data = $('#delete' + index).serialize();
    $.ajax({
        url:'delete',
        data:data,
        method:'POST',
        dataType:'json',
        async:false
    }).done(show('find', tableName));
}

function updateRow(index) {
    tableName = $("input[name='table']").val();
    var data = $('#update' + index).serialize();
    $.ajax({
        url:'update',
        data:data,
        method:'POST',
        dataType:'json',
        async:false
    }).done(show('find', tableName));
}

function showRowsFromTable(tableName) {
    $.get("find", {table: tableName}, function (data) {
        table = $('#find');
        var row = '<table id="find">';
        for(var i = 0; i < data.length; i++) {
                if( i == 0 ) {
                    row += '<tr>';
                    for(var j = 0; j < data[i].length; j++) {
                        row += '<th>' + data[i][j] + '</th>';
                    }
                    row += '<th></th><th></th>';
                } else {
                    row += '<tr>';
                    for(var j = 0; j < data[i].length; j++) {
                        row += '<td>';
                        row += '<input form="update'+ i +'" type="text" name="valuesNew" value="'+ data[i][j] +'"/>';
                        row += '<input form="update'+ i +'" type="text" name="values" value="'+ data[i][j] +'" hidden/>';
                        row += '<input form="update'+ i +'" type="text" name="fields" value="'+ data[0][j] +'" hidden/>';
                        row += '<input form="delete'+ i +'" type="text" name="values" value="'+ data[i][j] +'" hidden/>';
                        row += '<input form="delete'+ i +'" type="text" name="fields" value="'+ data[0][j] +'" hidden/>';
                        row += '</td>';
                    }
                    row += '<td>';
                    row += '<input  form="delete'+ i +'" type="text" name="table" value="'+ tableName +'" hidden/>';
                    row += '<input  form="delete'+ i +'" type="submit" value="Delete"/>';
                    row += '</td>';

                    row += '<td>';
                    row += '<input  form="update'+ i +'" type="text" name="table" value="' + tableName + '" hidden/>';
                    row += '<input  form="update'+ i +'" type="submit" value="Update"/>';
                    row += '</td>';
                }
            row += '<form id="update'+ i +'" action="javascript:void(null);" method="post" onsubmit="updateRow(' + i + ')"></form>';
            row += '<form id="delete'+ i +'" action="javascript:void(null);" method="post" onsubmit="deleteRow(' + i + ')"></form>';
            row += '</tr>';
        }

        row += '<tr><form id="insert" method="post" action="javascript:void(null);" onsubmit="insertRow(\'' + tableName + '\')">';
        for (var i = 0; i < data[0].length; i++) {
            row += '<td>';
            row += '<input form="insert" type="text" name="values"/>';
            row += '<input form="insert" type="text" name="fields" value=' + data[0][i]+ ' hidden/>';
            row += '</td>';
        }
        row += '<input form="insert" type="text" name="table" value=' + tableName + ' hidden/>';
        row += '<td><button form="insert" type="submit">Insert</button></td>';
        row += '<td></td>';
        row += '</form></tr>';
        row += '</table>';
        row += '<br>';

        row += '<form id="clear" method="post" action="javascript:void(null);" onsubmit="clearTable(\'' + tableName + '\')">';
        row += '<input form="clear" type="text" name="table" value=' + tableName + ' hidden/>';
        row += '<button form="clear" type="submit">Очистить таблицу!</button>';

        table.append(row);
    });
}

function clearTable(tableName) {
    var data = $('#clear').serialize();
    $.ajax({
        url:'clear',
        data:data,
        method:'POST',
        dataType:'json',
        async:false
    }).done(show('find', tableName));
}
function insertRow(tableName) {
    var data = $('#insert').serialize();
    $.ajax({
        url:'insert',
        data:data,
        method:'POST',
        dataType:'json',
        async:false
    }).done(show('find', tableName));
}

function createTable() {
    var tableName = $("input[name='tableName']").val();
    var dataPOST = 'table=' + tableName + '&';
    $("input[name='fields']").each(function(){
        dataPOST += 'fields=' + $(this).val() + '&';
    });
    $.ajax({
        url:'createTable',
        data:dataPOST,
        method:'POST',
        dataType:'json',
        async:false
    }).done(show('tables'));

}