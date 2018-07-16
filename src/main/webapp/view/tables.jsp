<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="tables">
    <div id="tablesList"></div>
</div>

<script id="tablesTmpl" type="text/x-jquery-tmpl">
    <div class="row">
        {%each $data.couple %}
            <div class="col-md-6">
                <div id="table_{%= $value %}">
                        <div class="card text-center">
                          <div class="card-body">
                                <h5 class="card-title">{%= $value %}</h5>
                                <button type="button" class="col-md-4 btn btn-primary btn-sm" onclick='showRowsFromTable("{%= $value %}")'>
                                    Edit
                                </button>
                                <button type="button" class="col-md-3 btn btn-warning btn-sm" onclick=clearTable("{%= $value %}")>
                                    Clear
                                </button>
                                <button type="button text-center" class="col-md-4 btn btn-danger btn-sm" onclick='deleteTable("{%= $value %}")'>
                                    Delete
                                </button>
                          </div>
                        </div>
                </div>
            </div>
        {%/each%}
    </div>
    <br>
</script>

<script id="findTmpl" type="text/x-jquery-tmpl">
         <table class="table table-sm" id="boardsTable">
            <tr>
                {%each $data.header %}
                    <th>
                        <input  form="fields"  type="text" name="fields" value="{%= $value %}" hidden/>
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
                                <input class="form-control" form="row_{%= $num %}" type="text" name="valuesNew" value="{%= $value %}"/>
                            </td>
                    {%/each%}
                    <td>
                        <button type="button" class="btn btn-success btn-xs" onClick=updateRow("{%= $num %}")>
                            Save row
                        </button>
                    </td>
                    <td>

                        <input form="row_{%= $num %}"  type="text" name="table" value="{%= $data.tableName%}" hidden/>
                        <button type="button" class="btn btn-danger btn-xs" onClick='delRow("{%= $num %}")')>
                            Delete
                        </button>
                        <form id="row_{%= $num %}" action=""></form>
                    </td>
                </tr>
            {%/each%}
            <tr>
                {%each $data.header %}
                    <th>
                        <input class="form-control" form="newRow"  type="text" name="values"}"/>
                    </th>
                {%/each%}
                <th>
                    <button type="button" class="btn btn-success btn-xs" onClick='insertRow("{%= $data.tableName %}")'>
                            Add row
                        </button>
                </th>
                <th>
                    <input form="newRow"  type="text" name="table" value="{%= $data.tableName %}" hidden/>
                    <form id="newRow" action=""></form>
                </th>
            </tr>
        </table>
</script>
