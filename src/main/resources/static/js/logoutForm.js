let logoutModal = document.getElementById('logoutModal');
let logoutButton = document.getElementById('logoutButton');
let modalCancel = document.getElementById('modalCancel');


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
