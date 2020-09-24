const accountIds = [];
const ajaxRequest = new XMLHttpRequest();
const accountCheckBoxes = document.querySelectorAll('.jsAccountCheckBox');
const authorizationButton = document.querySelector('.jsAuthorizationButton');

function confirm() {
    swal({
        title: "권한 승인",
        text: "권한을 수정 할까요?",
        showCancelButton : true,
        confirmButtonText : "예",
        cancelButtonText : "아니오",
        closeOnConfirm : false,
        closeOnCancel : true
    }, function() {
        authorize();
        swal('', '완료 되었습니다.', 'success');
        // location.reload();
    });
}

function authorize() {
    accountIds.length = 0;

    accountCheckBoxes.forEach(checkBox => {
        if (checkBox.checked) {
            const checkBoxWrapNode = checkBox.parentElement;
            const accountIdNode = checkBoxWrapNode.parentElement.querySelector('.jsAccountId');
            accountIds.push(accountIdNode.textContent);
        }
    });

    ajaxRequest.open('POST', 'http://localhost:8080/admin/accounts/role');
    ajaxRequest.setRequestHeader('Content-Type', 'application/json');
    ajaxRequest.send(JSON.stringify(accountIds));
    ajaxRequest.onreadystatechange = function() {
        if (ajaxRequest.readyState === ajaxRequest.DONE) {
            accountCheckBoxes.forEach(checkBox => {
                if (checkBox.checked) {
                    const checkBoxWrapNode = checkBox.parentElement;
                    const checkedData = checkBoxWrapNode.parentElement;
                    checkedData.remove();
                }
            });
        }
    }
}

function main() {
    authorizationButton.addEventListener('click', confirm);
}

main();