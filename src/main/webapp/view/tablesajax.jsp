<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    var deleteTable = function(tableName) {
        $.ajax({
            url:'dropTable',
            async:false,
            type:'POST',
            data:{table:tableName}
        }).done(show('tables'));
    }
</script>
<div id="tables">
    <div id="tablesList">
    </div>
    <p><button  onclick="show('createtable')">Создать таблицу</button></p>
</div>

<script id="tablesTmpl" type="text/x-jquery-tmpl">
        <table>
        <tr>
            <td width='100px'>
                {%= $data%}
            </td>
            <td width='100px'>
                <button onclick="show('find', '{%= $data%}')">Посмотреть</button>
            </td>
            <td>
                <button onclick='deleteTable("{%= $data%}")' >Удалить</button>
            </td>
        </tr>
        </table>
</script>
