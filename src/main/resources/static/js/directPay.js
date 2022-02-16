var directPayModal = document.getElementById('directPayModal');
var directPayButton = document.getElementById('directPayButton');
var modalCancelPayment = document.getElementById('modalCancelPayment');

function openModal(modal){
    modal.style.display = "block";
}
function closeModal(modal){
    modal.style.display = "none";
}

directPayButton.onclick = function (){
    openModal(directPayModal);
}

modalCancelPayment.onclick = function (){
    closeModal(directPayModal);
}
