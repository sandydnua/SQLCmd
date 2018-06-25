<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="createtable">

    <p>Имя новой таблицы: <input type="text" name="tableName"/></p>
    <p>Поля:</p>
    <div id="fieldsList">
        <input type='text' name='fields'/>&nbsp
    </div><p>
    <button onclick="addField()">Добавить поле</button>
    <button onclick="clearFieldsList()">Удалить лишние поля</button>
    <button onclick="createTable()">Создать таблицу</button>
</div>
