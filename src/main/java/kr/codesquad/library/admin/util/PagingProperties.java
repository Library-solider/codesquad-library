package kr.codesquad.library.admin.util;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

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
        this.pageGroupSize = 10;
        this.totalPageGroups = calculateTotalPageGroups(totalPages, pageGroupSize);
        this.currentPageGroup = calculateCurrentPageGroup(currentPage, pageGroupSize);
        this.startPageOfPageGroup = calculateStartPageOfPageGroup(currentPageGroup, pageGroupSize);
        this.endPageOfPageGroup = calculateEndPageOfPageGroup(startPageOfPageGroup, pageGroupSize, totalPages);
        this.endPageOfPreviousPageGroup = calculateEndPageOfPreviousPageGroup(currentPageGroup, pageGroupSize);
        this.startPageOfNextPageGroup = calculateStartPageOfNextPageGroup(currentPageGroup, pageGroupSize, totalPages);
    }

    private int calculateTotalPageGroups(int totalPages, int pageGroupSize) {
        return (int) (Math.ceil((double) totalPages / pageGroupSize));
    }

    private int calculateCurrentPageGroup(int currentPage, int pageGroupSize) {
        return (int) (Math.ceil((double) currentPage / pageGroupSize));
    }

    private int calculateStartPageOfPageGroup(int currentPageGroup, int pageGroupSize) {
        int startPageOfPageGroup = (currentPageGroup - 1) * pageGroupSize + 1;
        if (startPageOfPageGroup > totalPages) { startPageOfPageGroup = totalPages; }
        return startPageOfPageGroup;
    }

    private int calculateEndPageOfPageGroup(int startPageOfPageGroup, int pageGroupSize, int totalPages) {
        int endPageOfPageGroup = startPageOfPageGroup + pageGroupSize - 1;
        if (endPageOfPageGroup > totalPages) { endPageOfPageGroup = totalPages; }
        return endPageOfPageGroup;
    }

    private int calculateEndPageOfPreviousPageGroup(int currentPageGroup, int pageGroupSize) {
        int endPageOfPreviousPageGroup = (currentPageGroup * pageGroupSize) - pageGroupSize;
        if (endPageOfPreviousPageGroup < 1) { endPageOfPreviousPageGroup = 1; }
        return endPageOfPreviousPageGroup;
    }

    private int calculateStartPageOfNextPageGroup(int currentPageGroup, int pageGroupSize, int totalPages) {
        int startPageOfNextPageGroup = (currentPageGroup * pageGroupSize) + 1;
        if (startPageOfNextPageGroup > totalPages) { startPageOfNextPageGroup = totalPages; }
        return startPageOfNextPageGroup;
    }

    public static PagingProperties from(Page entityWithPaging) {
        return new PagingProperties(entityWithPaging.getSize(), entityWithPaging.getNumber() + 1,
                                    entityWithPaging.getTotalPages());
    }
}
