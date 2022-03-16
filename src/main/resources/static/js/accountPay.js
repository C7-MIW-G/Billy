var accountPayModal = document.getElementById('accountPayModal');
var accountPayButton = document.getElementById('accountPayButton');
var modalCancelAccountPayment = document.getElementById('modalCancelAccountPayment');

let confirmAccountPayModal = document.getElementById('confirm-account-pay-modal');
let cancelButton = document.getElementById('cancelButton');

function openModal(modal){
    modal.style.display = "block";
}
function closeModal(modal){
    modal.style.display = "none";
}

accountPayButton.onclick = function (){
    fire_ajax_getUsers();
    openModal(accountPayModal);
}

modalCancelAccountPayment.onclick = function (){
    closeModal(accountPayModal);
}


let cancelAccountPay = function (hyperlink) {
    hyperlink.preventDefault();
    closeModal(confirmAccountPayModal);
}

cancelButton.addEventListener('click', cancelAccountPay, false);

window.onclick = function(event) {
    if (event.target === accountPayModal) {
        accountPayModal.style.display = "none";
    }
}

