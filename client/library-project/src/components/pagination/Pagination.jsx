import React from "react";
import { NavLink, useHistory } from "react-router-dom";

import {
  PaginationWrapper,
  PaginationInner,
  Pages,
  Page,
  MoveButton,
} from "./paginationStyle";
import { GoChevronLeft, GoChevronRight } from "react-icons/go";

import { calcUpdatePagination } from "../../utils/query";

const Pagination = ({ totalItem, itemPerPage, showPageCount, currentPage }) => {
  const location = useHistory().location;

  const totalPage = Math.ceil(totalItem / itemPerPage);
  const totalPagination = Math.ceil(totalPage / showPageCount);
  const currentPaginationPosition = Math.ceil(currentPage / showPageCount);

  const isShowingPreviousButton = currentPaginationPosition > 1;
  const isShowingNextButton = currentPaginationPosition < totalPagination;

  return (
    <PaginationWrapper>
      <PaginationInner>
        {isShowingPreviousButton && (
          <MoveButton>
            <NavLink
              to={calcUpdatePagination(
                location,
                (currentPaginationPosition - 2) * showPageCount + 1
              )}
            >
              <GoChevronLeft />
            </NavLink>
          </MoveButton>
        )}
        <Pages>
          {Array.from({ length: showPageCount }).map((_, idx) => {
            const moveToPage =
              (currentPaginationPosition - 1) * showPageCount + (idx + 1);

            if (totalPage < moveToPage) {
              return null;
            }

            return (
              <Page isActive={currentPage === moveToPage}>
                <NavLink to={calcUpdatePagination(location, moveToPage)}>
                  {moveToPage}
                </NavLink>
              </Page>
            );
          })}
        </Pages>
        {isShowingNextButton && (
          <MoveButton>
            <NavLink
              to={calcUpdatePagination(
                location,
                currentPaginationPosition * showPageCount + 1
              )}
            >
              <GoChevronRight />
            </NavLink>
          </MoveButton>
        )}
      </PaginationInner>
    </PaginationWrapper>
  );
};

export default Pagination;
