var logoutModal = document.getElementById('logoutModal');
var logoutButton = document.getElementById('logoutButton');
var modalCancel = document.getElementById('modalCancel');


function openModal(modal){
    modal.style.display = "block";
}
function closeModal(modal){
    modal.style.display = "none";
}

logoutButton.onclick = function (){
    openModal(logoutModal);
}

modalCancel.onclick = function (){
    closeModal(logoutModal);
}
