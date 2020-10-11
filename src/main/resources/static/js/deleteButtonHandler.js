function validateDeleteRequest(event, validationWord, deleteForm) {
    event.preventDefault();
    swal({
            title: "삭제 확인",
            text: "삭제를 진행 하려면 'delete' 를 정확히 입력 해주세요.",
            type: "input",
            showCancelButton: true,
            closeOnConfirm: false,
            animation: "slide-from-top",
            inputPlaceholder: "delete"
        },
        function(inputValue){
            if (inputValue === validationWord) {
                swal({title: "", text:"요청이 전송 되었습니다.", type: "info"}, function() {
                    deleteForm.submit();
                });
                return;
            }
            swal.showInputError("입력 값이 다릅니다. 다시 시도 해주세요!");
        });
}

function main() {
    const deleteForm = document.querySelector('.jsDeleteForm');
    const deleteButton = document.querySelector('.jsDeleteButton');
    const validationWord = 'delete';
    deleteButton.addEventListener('click', (event) =>  validateDeleteRequest(event, validationWord, deleteForm));
}

main();
