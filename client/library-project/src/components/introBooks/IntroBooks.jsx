import React from "react";

import styled from "styled-components";

import { introBooks } from "../../mock/mock";
import BookCarousel from "./BookCarousel";

const IntroBooks = () => {
  const { iosBooks, frontEndBooks, backEndBooks, cultureBooks } = introBooks;

  return (
    <IntroBooksWrapper>
      <BookCarousel {...iosBooks} />
      <BookCarousel {...frontEndBooks} />
      <BookCarousel {...backEndBooks} />
      <BookCarousel {...cultureBooks} />
    </IntroBooksWrapper>
  );
};

const IntroBooksWrapper = styled.div`
  width: 100%;
`;

export default IntroBooks;
