const boxProperties = {
    edit: {
        selector: '.jsEditBox'
    },
    new: {
        selector: '.jsNewBox'
    }
}

const iconProperties = {
    edit: {
        selector: '.jsEditIcon',
        disable: 'item-edit__icon--disable jsEditIcon',
        enable: 'fas fa-edit item-edit__icon--enable jsEditIcon'
    },
    new: {
        selector: '.jsNewIcon',
        disable: 'item-new__icon--disable jsNewIcon',
        enable: 'item-new__icon--enable jsNewIcon'
    }
};

const formProperties = {
    edit: {
        selector: '.jsEditForm',
        disable: 'item-new--disable jsEditForm',
        enable: 'item-new--enable jsEditForm'
    },
    new: {
        selector: '.jsNewForm',
        disable: 'item-new--disable jsNewForm',
        enable: 'item-new--enable jsNewForm'
    }
};

const cancelButtonProperties = {
    new: {
        selector: '.jsNewCancelButton'
    },
    edit: {
        selector: '.jsEditCancelButton'
    }
}

function showEditForm(event) {
    const editBox = event.target.closest(boxProperties.edit.selector);
    const editIcon = editBox.querySelector(iconProperties.edit.selector);
    const editForm = editBox.querySelector(formProperties.edit.selector);
    editIcon.className = iconProperties.edit.disable;
    editForm.className = formProperties.edit.enable;
}

function hideEditForm(event) {
    const editBox = event.target.closest(boxProperties.edit.selector);
    const editIcon = editBox.querySelector(iconProperties.edit.selector);
    const editForm = editBox.querySelector(formProperties.edit.selector);
    editIcon.className = iconProperties.edit.enable;
    editForm.className = formProperties.edit.disable;
}

function showNewForm(event) {
    const newBox = event.target.closest(boxProperties.new.selector);
    const newIcon = newBox.querySelector(iconProperties.new.selector);
    const newForm = newBox.querySelector(formProperties.new.selector);
    newIcon.className = iconProperties.new.disable;
    newForm.className = formProperties.new.enable;
}

function hideNewForm(event) {
    const newBox = event.target.closest(boxProperties.new.selector);
    const newIcon = newBox.querySelector(iconProperties.new.selector);
    const newForm = newBox.querySelector(formProperties.new.selector);
    newIcon.className = iconProperties.new.enable;
    newForm.className = formProperties.new.disable;
}

function main() {
    const editIcons = document.querySelectorAll(iconProperties.edit.selector);
    const editCancelButtons = document.querySelectorAll(cancelButtonProperties.edit.selector);
    const newCancelButton = document.querySelector(cancelButtonProperties.new.selector);
    const newIcon = document.querySelector(iconProperties.new.selector);

    editIcons.forEach(editIcon => editIcon.addEventListener('click', (event) => showEditForm(event)));
    editCancelButtons.forEach(cancelButton => cancelButton.addEventListener('click', (event) => hideEditForm(event)));
    newIcon.addEventListener('click', (event) => showNewForm(event));
    newCancelButton.addEventListener('click', (event) => hideNewForm(event));
}

main();
