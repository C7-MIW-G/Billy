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
        url: "/o-api/addProduct/" + id,
        cache: false,
        timeout: 600000,

        success: function (resultData){

            fillOrderList(resultData);
            fillTotalPrice(resultData);

            fillAccountOrderList(resultData);

            console.log("SUCCESS : ", resultData);
            // $("#assortmentPage").removeClass('disabledPage');
        },

        error: function (e){

            console.log("ERROR: ", e)
            // $("#assortmentPage").removeClass('disabledPage');
        }
    });
}

function fire_ajax_removeProduct(id){

    // $("#assortmentPage").addClass('disabledPage');

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/o-api/removeProduct/" + id,
        cache: false,
        timeout: 600000,

        success: function (resultData){

            fillOrderList(resultData);
            fillTotalPrice(resultData);

            fillAccountOrderList(resultData);

            console.log("SUCCESS : ", resultData);
            // $("#assortmentPage").removeClass('disabledPage');
        },

        error: function (e){

            console.log("ERROR: ", e)
            // $("#assortmentPage").removeClass('disabledPage');
        }
    });
}

function fire_ajax_getUsers(){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/o-api/getUsers",
        cache: false,
        timeout: 600000,

        success: function (resultData){

            fillUserListAccountPay(resultData);

            console.log("SUCCESS : ", resultData);
            // $("#assortmentPage").removeClass('disabledPage');
        },

        error: function (e){

            console.log("ERROR: ", e)
            // $("#assortmentPage").removeClass('disabledPage');
        }
    });
}


//productList of Category
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


        let outer = document.createElement('div');
        outer.setAttribute('class', 'grid-product-parent');


        let desiredFunction = "fire_ajax_addProduct([[" + product.productId + "]])";
        let aRef = document.createElement('a');
        aRef.setAttribute('onclick', desiredFunction);


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


//orderlist
function fillOrderList(data) {
    let new_listBody = document.createElement('ul');

    data.receiptList.forEach(receiptProduct => {

        let outer = fillProductOfOrder(receiptProduct);

        let desiredFunction = "fire_ajax_removeProduct([[" + receiptProduct.product.productId + "]])";
        let aRef = document.createElement('a');
        aRef.setAttribute('onclick', desiredFunction);

        let item = document.createElement('li');

        aRef.appendChild(outer);

        item.appendChild(aRef);

        new_listBody.appendChild(item);
    });

    new_listBody.setAttribute('class', 'unordered-list');

    let old_listBody = document.getElementById("receiptProductList").firstChild;
    document.getElementById("receiptProductList").replaceChild(new_listBody, old_listBody);
}
function fillAccountOrderList(data){
    let new_listBody = document.createElement('ul');

    data.receiptList.forEach(receiptProduct => {

        let outer = fillProductOfOrder(receiptProduct);

        let item = document.createElement('li');

        item.appendChild(outer);

        new_listBody.appendChild(item);
    });

    new_listBody.setAttribute('class', 'unordered-list');

    let old_listBody = document.getElementById("accountPayReceipt").firstChild;
    document.getElementById("accountPayReceipt").replaceChild(new_listBody, old_listBody);
}

function fillProductOfOrder(receiptProduct){
    let subPrice = document.createTextNode(receiptProduct.priceDisplay);

    let priceDiv = document.createElement('div');
    priceDiv.setAttribute('class', 'productPrice');


    let eSign = document.createTextNode("€");

    let euroDiv = document.createElement('div');
    euroDiv.setAttribute('class', 'euro-sign');


    let pName = document.createTextNode(receiptProduct.product.productName);

    let nameDiv = document.createElement('div');
    nameDiv.setAttribute('class', 'productName');


    let productDiv = document.createElement('div');
    productDiv.setAttribute('class', 'grid-product-parent');


    let pAmount = document.createTextNode(receiptProduct.amount);

    let amountDiv = document.createElement('div');
    amountDiv.setAttribute('class', 'productAmount');


    let outer = document.createElement('div');
    outer.setAttribute('class', 'grid-orderList-parent');

    nameDiv.appendChild(pName);
    euroDiv.appendChild(eSign);
    priceDiv.appendChild(subPrice);

    productDiv.appendChild(nameDiv);
    productDiv.appendChild(euroDiv);
    productDiv.appendChild(priceDiv);

    amountDiv.appendChild(pAmount);

    outer.appendChild(amountDiv);
    outer.appendChild(productDiv);

    return outer;
}

//total price
function fillTotalPrice(data) {
    let totalPrice = data.totalOrderPrice;
    fillReceiptTotalPrice(totalPrice);
    fillAccountPayTotalPrice(totalPrice);
    fillDirectPayTotalPrice(totalPrice);
}
function setupTotalPrice(price){
    let new_totalPrice = document.createElement('span');
    new_totalPrice.setAttribute('class', 'productPrice');

    let tPrice = document.createTextNode(price);
    new_totalPrice.appendChild(tPrice);

    return new_totalPrice;
}
function fillReceiptTotalPrice(tPrice) {
    let new_totalPrice = setupTotalPrice(tPrice);
    new_totalPrice.setAttribute('id', 'totalPrice');
    document.getElementById("totalPrice").replaceWith(new_totalPrice);
}
function fillAccountPayTotalPrice(tPrice){
    let new_totalPrice = setupTotalPrice(tPrice);
    new_totalPrice.setAttribute('id', 'accountTotalPrice');
    document.getElementById("accountTotalPrice").replaceWith(new_totalPrice);

}
function fillDirectPayTotalPrice(tPrice){
    let new_totalPrice = setupTotalPrice("Total Price € " + tPrice);
    new_totalPrice.setAttribute('id', 'directTotalPrice');
    document.getElementById("directTotalPrice").replaceWith(new_totalPrice);
}


//users
function fillUserListAccountPay(data) {
    let new_listBody = document.createElement('ul');

    data.userList.forEach(user => {

        let euroSign = document.createTextNode('€');
        let euroSpan = document.createElement('span');

        let balanceAmount = document.createTextNode(user.accountBalance.toFixed(2));
        let amountSpan = document.createElement('span');
        amountSpan.setAttribute('class', 'flex-user-accountBalance-first-child');

        let balanceDiv = document.createElement('div');
        balanceDiv.setAttribute('class', 'flex-user-accountBalance grid-user-child-right');

        let restrictionSpan = document.createElement('div');
        user.restrictions.forEach(restriction => {
            let icon = document.createElement('span');
            icon.setAttribute('class', restriction);
            restrictionSpan.appendChild(icon);
        })

        let uName = document.createTextNode(user.displayName);
        let nameSpan = document.createElement('span');
        nameSpan.setAttribute('class', 'grid-user-child-left');

        let outer = document.createElement('div');
        outer.setAttribute('class', 'grid-user-account-pay');

        let aRef = document.createElement('a');

        let item = document.createElement('li');


        if(user.canBuy){
            aRef.setAttribute('class', 'confirm-account-pay');
        } else {
            aRef.setAttribute('class', 'isDisabled');
            restrictionSpan.setAttribute('class', 'grid-user-child-left ageSign');
        }



        nameSpan.appendChild(uName);
        outer.appendChild(nameSpan);

        outer.appendChild(restrictionSpan);
        outer.appendChild(balanceDiv);

        amountSpan.appendChild(balanceAmount);
        balanceDiv.appendChild(amountSpan);

        euroSpan.appendChild(euroSign);
        balanceDiv.appendChild(euroSpan);

        aRef.appendChild(outer);
        item.appendChild(aRef);
        new_listBody.appendChild(item);
    });

    new_listBody.setAttribute('class', 'unordered-list');

    let old_listBody = document.getElementById("userList").lastChild;
    document.getElementById("userList").replaceChild(new_listBody, old_listBody);
}


// for displaying category list
$('.category-list').on('click', 'li', function(){
    $('.category-list-item').not(this).css('background', 'transparent');
    $(this).css('background','#b0c4de');
});