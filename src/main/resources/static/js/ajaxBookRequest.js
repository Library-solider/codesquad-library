function confirm(event) {
    swal({
        title: "도서 이동",
        text: "도서를 이동 할까요?",
        showCancelButton : true,
        confirmButtonText : "예",
        cancelButtonText : "아니오",
        closeOnConfirm : false,
        closeOnCancel : true
    }, function(confirm) {
        if (confirm) {
            event.preventDefault();
            console.log('######');
            moveBooks();
            swal('', '완료 되었습니다.', 'success', function() {
                location.reload();
                return;
            });
        }
    });
}

function validateSelectedValue(selectedValue) {
    if (selectedValue === '') {
        selectedValue = null;
        return selectedValue;
    }
    return selectedValue;
}

function createBookRequest(bookCheckBoxes) {
    const bookRequest = {};
    const selectedCategory = document.querySelector('.jsSelectedCategory');
    const selectedLocation = document.querySelector('.jsSelectedLocation');
    const categoryId = selectedCategory.options[selectedCategory.selectedIndex].value;
    const bookcaseId = selectedLocation.options[selectedLocation.selectedIndex].value;
    bookRequest.categoryId = validateSelectedValue(categoryId);
    bookRequest.bookcaseId = validateSelectedValue(bookcaseId);
    bookRequest.bookIds = [];
    bookCheckBoxes.forEach(checkBox => {
        if (checkBox.checked) {
            const checkBoxWrapNode = checkBox.parentElement;
            const bookIdNode = checkBoxWrapNode.parentElement.querySelector('.jsDataId');
            bookRequest.bookIds.push(bookIdNode.textContent);
        }
    });
    console.log(bookRequest);
    return bookRequest;
}

function postAjaxRequest(bookRequest) {
    const ajaxRequest = new XMLHttpRequest();
    ajaxRequest.open('PATCH', '/admin/books/test');
    ajaxRequest.setRequestHeader('Content-Type', 'application/json');
    ajaxRequest.send(JSON.stringify(bookRequest));
    ajaxRequest.onreadystatechange = function() {
        if (ajaxRequest.readyState !== ajaxRequest.DONE) { return; }
        if (ajaxRequest.status === 200) { return; }
        swal('요청 실패', '요청을 다시 시도 해주세요.', 'error');
    }
}

function moveBooks() {
    const bookCheckBoxes = document.querySelectorAll('.jsNormalCheckBox');
    const bookRequest = createBookRequest(bookCheckBoxes);
    postAjaxRequest(bookRequest);
}

function main() {
    const bookEditButton = document.querySelector('.jsEditButton');
    bookEditButton.addEventListener('click', confirm);
}

main();
