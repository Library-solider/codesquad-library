function changeToCreateForm() {
    const searchResult = document.querySelector('.search-result-box');
    const bookForm = document.querySelector('.book-form-box');
    searchResult.classList.add('search-result-box--disable');
    bookForm.classList.add('book-form-box--enable');
}

function main() {
    const changeToBookFormButton = document.querySelector('.action-button__input');
    changeToBookFormButton.addEventListener('click', changeToCreateForm);
}

main();