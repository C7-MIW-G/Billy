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

function fire_ajax_getProducts(id){

    // let id = $("#choiceBox").val();
    // $("#choiceBox").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/a-api/getProducts/" + id,
        cache: false,
        timeout: 600000,

        success: function (resultData){

            let json = "<h4>Ajax Response</h4>"
                + JSON.stringify(resultData, null, 4);
            $('#feedback').html(json);

            fillProductList(resultData)

            console.log("SUCCESS : ", resultData);
            $("#choiceBox").prop("disabled", false);
        },

        error: function (e){
            let json = "<h4>Ajax Response</h4><pre>"
                + e.status + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR: ", e)
            $("#choiceBox").prop("disabled", false);
        }
    });
}

function fillProductList(data){
    let new_listBody = document.createElement('ul-products');

    data.productList.forEach(product => {

        let pName = document.createTextNode(product.name);

        let inner = document.createElement('div');
        inner.setAttribute('class', 'productName fontBold');

        let item = document.createElement('li');
        item.setAttribute('value', product.id);

        inner.appendChild(pName);
        item.appendChild(inner);
        new_listBody.appendChild(item);
    });

    let old_listBody = document.getElementById("productList").firstChild;

    new_listBody.setAttribute('class', 'unordered-list');

    document.getElementById("productList").replaceChild(new_listBody, old_listBody);
}