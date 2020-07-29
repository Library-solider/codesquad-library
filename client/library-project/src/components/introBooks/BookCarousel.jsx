import React from "react";
import { CarouselWrapper } from "../../styles/CarouselWrapper";

import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

const CAROUSEL_SETTINGS = {
  dots: true,
  infinite: true,
  speed: 500,
  slidesToShow: 4,
  slidesToScroll: 1,
  responsive: [
    {
      breakpoint: 769,
      settings: {
        slidesToShow: 3,
        slidesToScroll: 1,
        infinite: true,
        dots: true,
      },
    },
    {
      breakpoint: 450,
      settings: {
        slidesToShow: 1,
        slidesToScroll: 1,
        infinite: true,
        dots: true,
      },
    },
  ],
};

const BookCarousel = (props) => {
  const { carouselTitle, bookList } = props;

  const carouselItems = bookList.map((el) => {
    return (
      <div className="carousel_item">
        <img src={el.bookImage} alt="book cover" />
        <div className="book_title">{el.bookTitle}</div>
        <div className="writer">{el.writer}</div>
      </div>
    );
  });

  return (
    <CarouselWrapper>
      <div className="inner">
        <div className="title">{carouselTitle}</div>
        <Slider {...CAROUSEL_SETTINGS}>{carouselItems}</Slider>
      </div>
    </CarouselWrapper>
  );
};

export default BookCarousel;
