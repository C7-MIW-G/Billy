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

var elems = document.getElementsByClassName('confirm-account-pay');
var confirmIt = function (e) {
    if (!confirm('Please confirm')) e.preventDefault();
};
for (var i = 0, l = elems.length; i < l; i++) {
    elems[i].addEventListener('click', confirmIt, false);
}