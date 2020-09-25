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

function findCheckedAccountIds(accountCheckBoxes) {
    const checkedAccountIds = [];
    accountCheckBoxes.forEach(checkBox => {
        if (checkBox.checked) {
            const checkBoxWrapNode = checkBox.parentElement;
            const accountIdNode = checkBoxWrapNode.parentElement.querySelector('.jsAccountId');
            checkedAccountIds.push(accountIdNode.textContent);
        }
    });
    return checkedAccountIds;
}

function removeAuthorizedAccountFromDataTable(accountCheckBoxes) {
    accountCheckBoxes.forEach(checkBox => {
        if (checkBox.checked) {
            const checkBoxWrapNode = checkBox.parentElement;
            const checkedData = checkBoxWrapNode.parentElement;
            checkedData.remove();
        }
    });
}

function postAjaxRequest(checkedAccountIds, accountCheckBoxes) {
    const ajaxRequest = new XMLHttpRequest();
    ajaxRequest.open('POST', 'http://localhost:8080/admin/accounts/role');
    ajaxRequest.setRequestHeader('Content-Type', 'application/json');
    ajaxRequest.send(JSON.stringify(checkedAccountIds));
    ajaxRequest.onreadystatechange = function() {
        if (ajaxRequest.readyState !== ajaxRequest.DONE) { return; }
        if (ajaxRequest.status === 200) {
            removeAuthorizedAccountFromDataTable(accountCheckBoxes);
            return;
        }
        swal('요청 실패', '요청을 다시 시도 해주세요.', 'error');
    }
}

function authorize() {
    const accountCheckBoxes = document.querySelectorAll('.jsAccountCheckBox');
    const checkedAccountIds = findCheckedAccountIds(accountCheckBoxes);
    postAjaxRequest(checkedAccountIds, accountCheckBoxes);
}

function main() {
    const authorizationButton = document.querySelector('.jsAuthorizationButton');
    authorizationButton.addEventListener('click', confirm);
}

main();