$( function(){
    console.log("autocomplete開始")
    let hostUrl = "http://localhost:8080/employee/autocomplete";
    let employees = []
    $.ajax({
        url: hostUrl,
        type: "get",
        dataType: "json",
        async: true,
    }).done( function(data) {
        // ここでemployeesに従業員一覧を入れる
    }).fail( function(XMLHttpRequest, testStatus, errorThrown) {
        alert("エラーが発生しました.");
        console.log('XMLHttpRequest: ' + XMLHttpRequest);
        console.log("testStatus: " + testStatus);
        console.log("errorThrown" + errorThrown);
    })

    $("#searchLikeName").autocomplete({
        source: employees
    });
});