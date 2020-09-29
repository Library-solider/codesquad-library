function addStylesOnData(event) {
    const data = event.currentTarget.parentNode;
    const dataNodes = data.querySelectorAll('td');
    dataNodes.forEach(dataNode => dataNode.classList.add('data-table__row--hover'));
}

function removeStylesFromData(event) {
    const data = event.currentTarget.parentNode;
    const dataNodes = data.querySelectorAll('td');
    dataNodes.forEach(dataNode => dataNode.classList.remove('data-table__row--hover'));
}

function getDataId(event) {
    const data = event.currentTarget.parentNode;
    const dataId = data.querySelector('.jsDataId').innerHTML;
    console.log(dataId);
    window.location.href = `/admin/users/${dataId}`;
}

function main() {
    const dataList = document.querySelectorAll('.jsData');
    dataList.forEach(data => {
        data.addEventListener('mouseover', addStylesOnData);
        data.addEventListener('mouseout', removeStylesFromData);
        data.addEventListener('click', getDataId)
    });
}

main();