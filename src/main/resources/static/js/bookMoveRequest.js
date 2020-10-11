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
            moveBooks();
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
    ajaxRequest.open('POST', '/admin/books/groups');
    ajaxRequest.setRequestHeader('Content-Type', 'application/json');
    ajaxRequest.send(JSON.stringify(bookRequest));
    ajaxRequest.onreadystatechange = function() {
        if (ajaxRequest.readyState !== ajaxRequest.DONE) { return; }
        if (ajaxRequest.status === 200) {
            swal({title: "요청 성공", text: "도서가 이동 되었습니다.", type: "success"}, function() {
                location.reload();
            });
            return;
        }
        swal('요청 실패', '요청을 다시 시도 해주세요.', 'error');
    }
}

function moveBooks() {
    const bookCheckBoxes = document.querySelectorAll('.jsNormalCheckBox');
    console.log(bookCheckBoxes);
    const bookRequest = createBookRequest(bookCheckBoxes);
    if (bookRequest.bookIds.length === 0) {
        swal('요청 실패', '도서를 선택한 뒤 다시 요청 해주세요.', 'error');
        return;
    }
    postAjaxRequest(bookRequest);
}

function main() {
    const bookEditButton = document.querySelector('.jsEditButton');
    bookEditButton.addEventListener('click', confirm);
}

main();
