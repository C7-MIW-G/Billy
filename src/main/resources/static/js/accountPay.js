var accountPayModal = document.getElementById('accountPayModal');
var accountPayButton = document.getElementById('accountPayButton');
var modalCancelAccountPayment = document.getElementById('modalCancelAccountPayment');

let confirmationElements = document.getElementsByClassName('confirm-account-pay');
let confirmAccountPayModal = document.getElementById('confirm-account-pay-modal');
let cancelButton = document.getElementById('cancelButton');
let confirmButton = document.getElementById('confirmButton');

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

let confirmIt = function (hyperlink) {

    openModal(confirmAccountPayModal);
};

for (let i = 0; i < confirmationElements.length; i++) {
    confirmationElements[i].addEventListener('click', confirmIt, false);
}

let cancelAccountPay = function (hyperlink) {
    hyperlink.preventDefault();
    closeModal(confirmAccountPayModal);
}

let confirmAccountPay = function (hyperlink) {

}

cancelButton.addEventListener('click', cancelAccountPay, false);
confirmButton.addEventListener('click', confirmAccountPay, false);

window.onclick = function(event) {
    if (event.target === accountPayModal) {
        accountPayModal.style.display = "none";
    }
}

// $('.confirm-account-pay').on('click', function(hyperlink) {
//     return confirm('Are you sure?' + hyperlink);
// })
