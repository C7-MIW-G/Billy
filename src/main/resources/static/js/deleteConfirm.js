var deleteModal = document.getElementById('deleteModal');
var deleteButton = document.getElementById('deleteButton');
var modalCancel = document.getElementById('cancelDelete');


function openModal(modal){
    modal.style.display = "block";
}
function closeModal(modal){
    modal.style.display = "none";
}

deleteButton.onclick = function (){
    openModal(deleteModal);
}

modalCancel.onclick = function (){
    closeModal(deleteModal);
}