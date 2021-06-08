$( function(){
    $("#searchLikeName").autocomplete({
        source: function(req, resp){
            $.ajax({
                type: "http://localhost:8080/employee/autocomplete",
                url: "url",
                data: {
                    keyword: req.term
                },
                dataType: "json",
                success: function (employees) {
                    resp(employees)
                }
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