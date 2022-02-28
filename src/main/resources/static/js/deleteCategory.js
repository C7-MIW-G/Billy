let disabledButton = document.getElementById("deleteButton");

window.addEventListener("load", disableLink);

function disableLink(){
    if (disabledButton.classList.contains('buttonIsDisabled')){
        disabledButton.removeAttribute('href');
    }
}