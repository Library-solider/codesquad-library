import React from "react";
import styled from "styled-components";

const BookItem = ({ image, title, author }) => {
  return (
    <BookItemWrapper className="book_item">
      <img src={image} alt="book cover" />
      <div className="book_title">{title}</div>
      <div className="author">{author}</div>
    </BookItemWrapper>
  );
};

const BookItemWrapper = styled.div`
  width: 100%;
  padding: ${({ theme: { paddings } }) => paddings.small};
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: flex-end;

  .book_title {
    width: 100%;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
    padding: ${({ theme: { paddings } }) => paddings.small};
    font-weight: bold;
    text-align: center;
  }

  .author {
    text-align: center;
    color: ${({ theme: { colors } }) => colors.gray_2};
  }

  img {
    transition: 0.3s;
    width: 100%;
    box-shadow: ${({ theme: { boxShadow } }) => boxShadow.boxShadow_1};
    cursor: pointer;
    :hover {
      opacity: 0.7;
    }
  }
`;

export default BookItem;
