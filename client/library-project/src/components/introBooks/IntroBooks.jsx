import React, { useState } from "react";

import styled from "styled-components";
import { Loading } from "../../styles/Loading";

import BookCarousel from "./BookCarousel";
import { useFetch } from "../../hooks/useFetch";

const IntroBooks = () => {
  const { response, error } = useFetch(process.env.REACT_APP_DB_HOST_MAIN);

  if (!response)
    return (
      <Loading>
        <img
          src="https://media.giphy.com/media/3oEjI6SIIHBdRxXI40/giphy.gif"
          alt=""
        />
      </Loading>
    );

  return (
    <IntroBooksWrapper>
      {response.data.map((el) => {
        return <BookCarousel key={el.categoryId} {...el} />;
      })}
    </IntroBooksWrapper>
  );
};

const IntroBooksWrapper = styled.div`
  width: 100%;
`;

export default IntroBooks;
