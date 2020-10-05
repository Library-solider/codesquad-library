package kr.codesquad.library.admin.common;

import lombok.Getter;
import org.springframework.data.domain.Page;

import static kr.codesquad.library.admin.common.ConstantsCoveringMagicNumber.PAGE_GROUP_SIZE;

@Getter
public class PagingProperties {

    private final int pageSize;
    private final int currentPage;
    private final int totalPages;
    private final int pageGroupSize;
    private final int totalPageGroups;
    private final int currentPageGroup;
    private final int startPageOfPageGroup;
    private final int endPageOfPageGroup;
    private final int endPageOfPreviousPageGroup;
    private final int startPageOfNextPageGroup;

    private PagingProperties(int pageSize, int currentPage, int totalPages) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.pageGroupSize = PAGE_GROUP_SIZE;
        this.totalPageGroups = calculateTotalPageGroups();
        this.currentPageGroup = calculateCurrentPageGroup();
        this.startPageOfPageGroup = calculateStartPageOfPageGroup();
        this.endPageOfPageGroup = calculateEndPageOfPageGroup();
        this.endPageOfPreviousPageGroup = calculateEndPageOfPreviousPageGroup();
        this.startPageOfNextPageGroup = calculateStartPageOfNextPageGroup();
    }

    private int calculateTotalPageGroups() {
        return (int) (Math.ceil((double) totalPages / pageGroupSize));
    }

    private int calculateCurrentPageGroup() {
        return (int) (Math.ceil((double) currentPage / pageGroupSize));
    }

    private int calculateStartPageOfPageGroup() {
        int startPageOfPageGroup = (currentPageGroup - 1) * pageGroupSize + 1;
        if (startPageOfPageGroup > totalPages) { startPageOfPageGroup = totalPages; }
        return startPageOfPageGroup;
    }

    private int calculateEndPageOfPageGroup() {
        int endPageOfPageGroup = startPageOfPageGroup + pageGroupSize - 1;
        if (endPageOfPageGroup > totalPages) { endPageOfPageGroup = totalPages; }
        return endPageOfPageGroup;
    }

    private int calculateEndPageOfPreviousPageGroup() {
        int endPageOfPreviousPageGroup = (currentPageGroup * pageGroupSize) - pageGroupSize;
        if (endPageOfPreviousPageGroup < 1) { endPageOfPreviousPageGroup = 1; }
        return endPageOfPreviousPageGroup;
    }

    private int calculateStartPageOfNextPageGroup() {
        int startPageOfNextPageGroup = (currentPageGroup * pageGroupSize) + 1;
        if (startPageOfNextPageGroup > totalPages) { startPageOfNextPageGroup = totalPages; }
        return startPageOfNextPageGroup;
    }

    public static PagingProperties from(Page entityWithPaging) {
        return new PagingProperties(entityWithPaging.getSize(), entityWithPaging.getNumber() + 1,
                                    entityWithPaging.getTotalPages());
    }
}
