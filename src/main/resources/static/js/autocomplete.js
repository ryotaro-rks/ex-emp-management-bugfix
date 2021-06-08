$( function(){
    console.log("autocomplete開始")
    $("#searchLikeName").autocomplete({
        source: function(req, resp){
            $.ajax({
                type: "post",
                url: "http://localhost:8080/employee/autocomplete",
                data: {
                    keyword: req.term
                },
                dataType: "json",
                success: function (employees) {
                    resp(employees)
                },
                async: true,
            }).done( function(data) {
                console.dir(JSON.stringify(data))
            }).fail( function(XMLHttpRequest, testStatus, errorThrown) {
                alert("エラーが発生しました.");
                console.log('XMLHttpRequest: ' + XMLHttpRequest);
                console.log("testStatus: " + testStatus);
                console.log("errorThrown" + errorThrown);
            });
        }
    });
});