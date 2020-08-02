import React, { useState } from "react";

import styled from "styled-components";

import BookCarousel from "./BookCarousel";
import { useFetch } from "../../hooks/useFetch";

const INIT_DATA_STRUCTURE = {
  data: [
    {
      categoryId: "",
      categoryTitle: "",
      bookCount: "",
      books: [{ id: "", imageUrl: "", title: "", author: "" }],
    },
  ],
};

const IntroBooks = () => {
  const [introBookList, setIntroBookList] = useState(INIT_DATA_STRUCTURE);

  const introBookLists = introBookList.data.map((el) => {
    return <BookCarousel key={el.categoryId} {...el} />;
  });

  useFetch(process.env.REACT_APP_DB_HOST_MAIN, setIntroBookList);

  return <IntroBooksWrapper>{introBookLists}</IntroBooksWrapper>;
};

const IntroBooksWrapper = styled.div`
  width: 100%;
`;

export default IntroBooks;
