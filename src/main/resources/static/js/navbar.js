




$(document).ready(function () {
    let url = window.location.href;
    let activeTab;

    if (url.indexOf("assortment") > -1) {
        activeTab = document.getElementById("productsTab");
    } else if (url.indexOf("users") > -1) {
        activeTab = document.getElementById("usersTab");
    } else if (url.indexOf("orderHistory") > -1) {
        activeTab = document.getElementById("orderHistoryTab");
    } else {
        activeTab = document.getElementById("orderTab");
    }
    activeTab.style.background = "#A0B4CE";
})