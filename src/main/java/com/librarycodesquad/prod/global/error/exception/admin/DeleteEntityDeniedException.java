package com.librarycodesquad.prod.global.error.exception.admin;


import com.librarycodesquad.prod.global.error.ExceptionView;
import com.librarycodesquad.prod.global.error.exception.AdminException;

public class DeleteEntityDeniedException extends AdminException {

    public DeleteEntityDeniedException() { super(ExceptionView.DELETE_ENTITY_DENITED); }
}
