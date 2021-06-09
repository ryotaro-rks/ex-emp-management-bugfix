$( function(){
    let hostUrl = "http://localhost:8080/employee/autocomplete";

    $.ajax({
        url: hostUrl,
        type: "get",
        dataType: "json",
        async: true,
    }).done( function(data) {
        let employees = data
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