<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="createtable">
    <div class="row">
        <div class="col-md-1"></div>
        <div class="col-md-6">
            <label>Имя новой таблицы: <input type="text" name="tableName"/></label><br>
            <p>Поля:</p>
            <div id="fieldsList">
                <div>
                    <input class="form-control" type='text' name='fields'/>
                </div><br>
            </div>
            <button onclick="addField()">Добавить поле</button>
            <button onclick="createTable()">Создать таблицу</button>
        </div>
    </div>
</div>
