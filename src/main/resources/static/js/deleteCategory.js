let deleteButton = document.getElementById("deleteButton");
let deleteModal = document.getElementById("deleteModal");
let modalCancel = document.getElementById("modalCancel");

function openModal(modal){
    modal.style.display = "block";
}
function closeModal(modal){
    modal.style.display = "none";
}
deleteButton.onclick = function (){
    if ((deleteButton.classList.contains('buttonIsDisabled')) === false){
        openModal(deleteModal);
    }
}
modalCancel.onclick = function (){
    closeModal(deleteModal);
}

