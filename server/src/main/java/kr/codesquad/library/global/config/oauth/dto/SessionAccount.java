package kr.codesquad.library.global.config.oauth.dto;

import kr.codesquad.library.domain.account.Account;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionAccount implements Serializable {

    private final String name;
    private final String email;

    public SessionAccount(Account account) {
        this.name = account.getName();
        this.email = account.getEmail();
    }
}
