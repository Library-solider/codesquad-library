package kr.codesquad.library.global.error.exception.admin;


import kr.codesquad.library.global.error.exception.AdminException;
import kr.codesquad.library.global.error.ExceptionView;

public class DeleteEntityDeniedException extends AdminException {

    public DeleteEntityDeniedException() { super(ExceptionView.DELETE_ENTITY_DENITED); }
}
