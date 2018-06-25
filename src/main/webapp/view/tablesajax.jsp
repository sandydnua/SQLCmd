<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">

</script>
<div id="tables">
    <div id="tablesList">
    </div>
    <p><button  onclick="show('createtable')">Создать таблицу</button></p>
</div>




<script id="tablesTmpl" type="text/x-jquery-tmpl">
        <div id="table_{%= $data %}">
            <span class="tableItem">
                {%= $data %}
            </span>
            <img src="<c:url value="/resources/img/edit.png" />" onClick='showRowsFromTable("{%= $data %}")'>
            <img src="<c:url value="/resources/img/delete.jpg" />"     onClick='deleteTable("{%= $data %}")'>
        </div>
        <br>
</script>

<script id="findTmpl" type="text/x-jquery-tmpl">
         <table border="1" id="boardsTable">
            <tr>
                {%each $data.header %}
                    <th>
                        <input form="fields"  type="text" name="fields" value="{%= $value %}" hidden/>
                        <form id="fields" action=""></form>
                        {%= $value %}
                    </th>
                {%/each%}
                <th></th>
                <th></th>
            </tr>
            {%each($num, $row) $data.body %}
                 <tr>
                    {%each $row %}
                            <td>
                                <input form="row_{%= $num %}"  type="text" name="values" value="{%= $value %}" hidden/>
                                <input form="row_{%= $num %}" type="text" name="valuesNew" value="{%= $value %}"/>
                            </td>
                    {%/each%}
                    <td>
                        <img src="<c:url value="/resources/img/update_row.png" />" onClick='updateRow("{%= $num %}")'>
                    </td>
                    <td>

                        <input form="row_{%= $num %}"  type="text" name="table" value="{%= $data.tableName%}" hidden/>
                        <img src="<c:url value="/resources/img/delete_row.png" />" onClick='delRow("{%= $num %}")'>
                        <form id="row_{%= $num %}" action=""></form>
                    </td>
                </tr>
            {%/each%}
            <tr>
                {%each $data.header %}
                    <th>
                        <input form="newRow"  type="text" name="values"}"/>


                    </th>
                {%/each%}
                <th>
                    <img src="<c:url value="/resources/img/insert_row.png" />" onClick='insertRow("{%= $data.tableName %}")'>
                </th>
                <th>
                    <input form="newRow"  type="text" name="table" value="{%= $data.tableName %}" hidden/>
                    <form id="newRow" action=""></form>
                </th>
            </tr>
        </table>
        <br><button onclick=clearTable("{%= $data.tableName %}")>Очистить таблицу</button>

</script>
