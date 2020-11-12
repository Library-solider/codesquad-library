package com.librarycodesquad.admin.domain.account.response;

import com.librarycodesquad.admin.common.PagingProperties;
import com.librarycodesquad.admin.domain.account.AccountSummary;
import lombok.Getter;

import java.util.List;

@Getter
public class AccountDataResponse {

    private final List<AccountSummary> accountSummaries;
    private final PagingProperties pagingProperties;

    private AccountDataResponse(List<AccountSummary> accountSummaries, PagingProperties pagingProperties) {
        this.accountSummaries = accountSummaries;
        this.pagingProperties = pagingProperties;
    }

    public static AccountDataResponse of(List<AccountSummary> accountSummaries, PagingProperties pagingProperties) {
        return new AccountDataResponse(accountSummaries, pagingProperties);
    }

}
