package com.librarycodesquad.admin.domain.book.response;

import com.librarycodesquad.admin.common.PagingProperties;
import com.librarycodesquad.admin.domain.book.RentalBookSummary;
import lombok.Getter;

import java.util.List;

@Getter
public class RentalBookAdminResponse {

    private final List<RentalBookSummary> rentalBookSummaries;
    private final PagingProperties pagingProperties;

    private RentalBookAdminResponse(List<RentalBookSummary> rentalBookSummaries, PagingProperties pagingProperties) {
        this.rentalBookSummaries = rentalBookSummaries;
        this.pagingProperties = pagingProperties;
    }

    public static RentalBookAdminResponse of(List<RentalBookSummary> rentalBookSummaries, PagingProperties pagingProperties) {
        return new RentalBookAdminResponse(rentalBookSummaries, pagingProperties);
    }
}
