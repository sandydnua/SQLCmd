<%--
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test</title>
    <script src="resources/js/jquery.js"></script>

    <script type="text/javascript">
        $(window).on('load', function(){
           alert('load');
           var d = $('#mainDiv');
            d.hide();
            alert('load');
            d.show();
            alert('load');
            d.empty();
            var str = '<p>!</p>';

           d.append(str);
        });
    </script>

</head>
<body>
    <p> Main Page</p>

    <div id="mainDiv">
        <p>!!!</p>
    </div>

</body>
</html>
--%>
