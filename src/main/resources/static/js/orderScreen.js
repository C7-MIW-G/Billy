$(function () {
    let header = $("#header").val();
    let token = $("#token").val();
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

function fire_ajax_getProducts(id){

    // $("#assortmentPage").addClass('disabledPage');

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/a-api/getProducts/" + id,
        cache: false,
        timeout: 600000,

        success: function (resultData){

            fillProductList(resultData)

            console.log("SUCCESS : ", resultData);
            // $("#assortmentPage").removeClass('disabledPage');
        },

        error: function (e){

            console.log("ERROR: ", e)
            // $("#assortmentPage").removeClass('disabledPage');
        }
    });
}

function fire_ajax_addProduct(id){

    // $("#assortmentPage").addClass('disabledPage');

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/a-api/addProduct/" + id,
        cache: false,
        timeout: 600000,

        success: function (resultData){

            //TODO fillOrderList(resultData)

            console.log("SUCCESS : ", resultData);
            // $("#assortmentPage").removeClass('disabledPage');
        },

        error: function (e){

            console.log("ERROR: ", e)
            // $("#assortmentPage").removeClass('disabledPage');
        }
    });
}


function fillProductList(data){
    let new_listBody = document.createElement('ul');

    data.productList.forEach(product => {

        let pName = document.createTextNode(product.productName);

        let nameDiv = document.createElement('div');
        nameDiv.setAttribute('class', 'productName');


        let eSign = document.createTextNode("€")

        let euroDiv = document.createElement('div');
        euroDiv.setAttribute('class', 'euro-sign');


        let pPrice = document.createTextNode(product.productPrice.toFixed(2));

        let priceDiv = document.createElement('div');
        priceDiv.setAttribute('class', 'productPrice');


        let desiredLink = "/orders/add/" + product.productId;
        let aRef = document.createElement('a');
        aRef.setAttribute('href', desiredLink)

        let outer = document.createElement('div');
        outer.setAttribute('class', 'grid-product-parent');

        let item = document.createElement('li');

        nameDiv.appendChild(pName);
        euroDiv.appendChild(eSign);
        priceDiv.appendChild(pPrice);

        outer.appendChild(nameDiv);
        outer.appendChild(euroDiv);
        outer.appendChild(priceDiv)

        aRef.appendChild(outer);

        item.appendChild(aRef);

        new_listBody.appendChild(item);
    });

    let old_listBody = document.getElementById("productList").firstChild;

    new_listBody.setAttribute('class', 'unordered-list');

    document.getElementById("productList").replaceChild(new_listBody, old_listBody);
}


function fillOrderList(data) {
    let new_listBody = document.createElement('ul');

    data.receiptList.forEach(receiptProduct => {

        //TODO

    });

    let old_listBody = document.getElementById("receiptProductList").firstChild;

    new_listBody.setAttribute('class', 'unordered-list');

    document.getElementById("receiptProductList").replaceChild(new_listBody, old_listBody);
}

