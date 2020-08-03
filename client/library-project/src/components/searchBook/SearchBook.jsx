import React, { useState, useContext } from "react";
import { useHistory } from "react-router-dom";
import { useIsMobile } from "../../hooks/useMediaQuery";
import querystring from "query-string";

import { ThemeContext } from "styled-components";
import { SearchBookWrapper, SearchBookInner } from "./searchBookStyle";

import BookItem from "../bookItem/BookItem";
import Pagination from "../pagination/Pagination";
import Category from "../category/Category";

import {
  PER_PAGE,
  SHOW_PAGE_COUNT_DESKTOP,
  SHOW_PAGE_COUNT_MOBILE,
} from "../../utils/search";

import { createRequestUrl } from "../../utils/url";
import { useBookFetch } from "../../hooks/useFetch";

const DEFULT_CURRENT_PAGE = 1;
const INIT_DATA_STRUCTURE = {
  data: {
    bookCount: 1,
    books: [
      {
        id: "",
        imageUrl: "",
        title: "",
        author: "",
      },
    ],
  },
};

const SearchBook = () => {
  // hook
  const history = useHistory();

  // State
  const [booksData, setBooksData] = useState(INIT_DATA_STRUCTURE);
  const { bookCount, books, categoryId } = booksData.data;

  // Query String
  const parsedSearchQueries = querystring.parse(history.location.search);
  const requestUrl = createRequestUrl(history.location);
  const currentPage = parsedSearchQueries.page
    ? parsedSearchQueries.page
    : DEFULT_CURRENT_PAGE;

  // Style
  const themeContext = useContext(ThemeContext);
  const { colors } = themeContext;

  const bookList = books.map((el) => {
    return <BookItem image={el.imageUrl} title={el.title} author={el.author} />;
  });

  useBookFetch(requestUrl, setBooksData, parsedSearchQueries);

  return (
    <>
      <Category
        fillColor={colors.gray_1}
        fontColor={colors.white}
        isActive={categoryId && categoryId}
      />
      <SearchBookWrapper>
        <SearchBookInner>{bookList}</SearchBookInner>
      </SearchBookWrapper>
      <Pagination
        totalItem={bookCount}
        itemPerPage={PER_PAGE}
        showPageCount={
          useIsMobile() ? SHOW_PAGE_COUNT_MOBILE : SHOW_PAGE_COUNT_DESKTOP
        }
        currentPage={Math.max(currentPage, 1)}
      />
    </>
  );
};

export default SearchBook;
