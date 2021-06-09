$( function(){
    console.log("autocomplete開始")
    let hostUrl = "http://localhost:8080/employee/autocomplete";
    let employees;

    $.ajax({
        url: hostUrl,
        type: "get",
        dataType: "json",
        async: true,
    }).done( function(data) {
        // ここでemployeesに従業員一覧を入れる
        console.log("データ受け取り完了")
    	console.dir(JSON.stringify(data))
        employees = data
        console.dir(employees)
        console.log(data)
        $("#searchLikeName").autocomplete({
        	source: employees
        });
    }).fail( function(XMLHttpRequest, testStatus, errorThrown) {
        alert("エラーが発生しました.");
        console.log('XMLHttpRequest: ' + XMLHttpRequest);
        console.log("testStatus: " + testStatus);
        console.log("errorThrown" + errorThrown);
    })
    
});