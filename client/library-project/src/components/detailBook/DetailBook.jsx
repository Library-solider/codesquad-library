import React from "react";
import { DetailWrapper, DetailInner } from "./detailStyle";

import BookInfomation from "./BookInfomation";
import BookDescription from "./BookDescription";

const DetailBook = () => {
  return (
    <DetailWrapper>
      <DetailInner>
        <BookInfomation />
        <BookDescription />
      </DetailInner>
    </DetailWrapper>
  );
};

export default DetailBook;
