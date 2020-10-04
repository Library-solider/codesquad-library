function changeToCreateForm() {
    const searchResult = document.querySelector('.search-result-box');
    const createForm = document.querySelector('.create-form-box');
    searchResult.classList.add('search-result-box--disable');
    createForm.classList.add('create-form-box--enable');
}

function main() {
    const changeToCreateFormButton = document.querySelector('.action-button__input');
    changeToCreateFormButton.addEventListener('click', changeToCreateForm);
}

main();