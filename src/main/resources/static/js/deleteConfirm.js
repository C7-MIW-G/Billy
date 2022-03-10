let deleteModal = document.getElementById('deleteModal');
let deleteButton = document.getElementById('deleteButton');
let cancelDelete = document.getElementById('cancelDelete');


function openModal(modal){
    modal.style.display = "block";
}
function closeModal(modal){
    modal.style.display = "none";
}
deleteButton.onclick = function () {
    if ((deleteButton.classList.contains('buttonIsDisabled')) === false) {
        openModal(deleteModal);
    }
}
cancelDelete.onclick = function (){
    closeModal(deleteModal);
}

window.onclick = function(event) {
    if (event.target === deleteModal) {
        deleteModal.style.display = "none";
    }
}

