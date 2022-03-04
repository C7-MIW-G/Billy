$(document).ready(function () {

    $("#search-product-form").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_getProducts();
    });
});

$(function () {
    let header = $("#header").val();
    let token = $("#token").val();
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

function fire_ajax_getProducts(){

    let searchData = {};
    searchData["id"] = $("#choiceBox").val();

    $("#choiceBox").prop("disabled", true);

    let header = $("#header").val();
    let token = $("#token").val();
    console.log("HEADER: ", header);
    console.log("TOKEN: ", token);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/a-api/getProducts",
        data: JSON.stringify(searchData),
        dataType: 'json',
        cache: false,
        timeout: 600000,

        success: function (resultData){

            let json = "<h4>Ajax Response</h4>"
                + JSON.stringify(resultData, null, 4);
            $('#feedback').html(json);

            console.log("GET: ", searchData);
            console.log("SUCCESS : ", resultData);

            $("#choiceBox").prop("disabled", false);
        },

        error: function (e){
            let json = "<h4>Ajax Response</h4><pre>"
                + e.status + "</pre>";
            $('#feedback').html(json);

            console.log("GET: ", searchData);
            console.log("ERROR: ", e)
            $("#choiceBox").prop("disabled", false);
        }
    });
}