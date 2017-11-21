$(window).on('load', function() {
    $.get("testajax", function (data) {
        var container = $("#testDiv");
        var elements = data.object;
        for (var i = 0; i < data.length; i++) {
            var element = data[i];
            container.append("<p>" + element + "</p>");
        }
    });
});