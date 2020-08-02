import React, { useState, useContext } from "react";
import { ThemeContext } from "styled-components";
import { BookInfomationWrapper } from "./detailStyle";
import sample_6 from "../../assets/images/sample-6.jpg";

import { Button } from "../../styles/Button";
import Modal from "../modal/Modal";

const BookInfomation = ({
  image,
  title,
  author,
  publisher,
  publicationDate,
}) => {
  const themeContext = useContext(ThemeContext);
  const [rentalClosed, setRentalClosed] = useState(false);

  const onClickRentalBtn = () => setRentalClosed(!rentalClosed);

  return (
    <BookInfomationWrapper>
      <div className="book_image rental">
        <img src={sample_6} alt="" />
        <Button
          onClick={onClickRentalBtn}
          fillColor={themeContext.colors.green_1}
          textColor={themeContext.colors.white}
        >
          대여하기
        </Button>
        {rentalClosed && <Modal onCloseModal={onClickRentalBtn} />}
      </div>
      <div className="book_detail_info">
        <h3 className="title">어딘가 상상도 못 할 곳에, 수많은 순록 때가</h3>
        <div className="author">저자:오건영 저</div>
        <div className="publisher">출판:퍼블리싱출판사</div>
        <div className="publicationDate">출판일:1995년 11월 15일</div>
      </div>
    </BookInfomationWrapper>
  );
};

export default BookInfomation;
