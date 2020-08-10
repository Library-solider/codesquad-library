import React, { useContext } from "react";
import { useHistory } from "react-router-dom";
import { useIsMobile } from "../../hooks/useMediaQuery";
import querystring from "query-string";

import { ThemeContext } from "styled-components";
import { Loading } from "../../styles/Loading";
import { SearchBookWrapper, SearchBookInner } from "./searchBookStyle";

import BookItem from "../bookItem/BookItem";
import Pagination from "../pagination/Pagination";
import Category from "../category/Category";

import {
  PER_PAGE,
  SHOW_PAGE_COUNT_DESKTOP,
  SHOW_PAGE_COUNT_MOBILE,
  MIN_PAGINATION,
} from "../../constants/searchBook";

import { createRequestUrl } from "../../utils/url";
import { useFetch } from "../../hooks/useFetch";

const SearchBook = () => {
  // hook
  const history = useHistory();

  // Query String
  const requestUrl = createRequestUrl(history.location);
  const parsedSearchQueries = querystring.parse(history.location.search);
  const currentPage = parsedSearchQueries.page
    ? parsedSearchQueries.page
    : MIN_PAGINATION;

  // State

  const { response, error } = useFetch(requestUrl, parsedSearchQueries.page);

  // Style
  const themeContext = useContext(ThemeContext);
  const { colors } = themeContext;

  const isMobile = useIsMobile()
    ? SHOW_PAGE_COUNT_MOBILE
    : SHOW_PAGE_COUNT_DESKTOP;

  if (!response)
    return (
      <Loading>
        <img
          src="https://media.giphy.com/media/3oEjI6SIIHBdRxXI40/giphy.gif"
          alt=""
        />
      </Loading>
    );
  if (error) return <div>{error.message}</div>;

  const { bookCount, books, categoryId } = response.data;
  window.scrollTo(0, 0);

  return (
    <>
      <Category
        fillColor={colors.gray_1}
        fontColor={colors.white}
        isActive={categoryId && categoryId}
      />
      <SearchBookWrapper>
        <SearchBookInner>
          {books.map((el) => (
            <BookItem image={el.imageUrl} title={el.title} author={el.author} />
          ))}
        </SearchBookInner>
      </SearchBookWrapper>
      <Pagination
        totalItem={bookCount}
        itemPerPage={PER_PAGE}
        showPageCount={isMobile}
        currentPage={Math.max(currentPage, 1)}
      />
    </>
  );
};

export default SearchBook;
