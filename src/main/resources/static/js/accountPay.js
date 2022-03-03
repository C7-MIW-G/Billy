var accountPayModal = document.getElementById('accountPayModal');
var accountPayButton = document.getElementById('accountPayButton');
var modalCancelAccountPayment = document.getElementById('modalCancelAccountPayment');

function openModal(modal){
    modal.style.display = "block";
}
function closeModal(modal){
    modal.style.display = "none";
}

accountPayButton.onclick = function (){
    openModal(accountPayModal);
}

modalCancelAccountPayment.onclick = function (){
    closeModal(accountPayModal);
}

var confirmationElements = document.getElementsByClassName('confirm-account-pay');

var confirmIt = function (hyperlink) {
    if (!confirm('Please confirm')) hyperlink.preventDefault();
};
for (var i = 0; i < confirmationElements.length; i++) {
    confirmationElements[i].addEventListener('click', confirmIt, false);
}