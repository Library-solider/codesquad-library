import React from "react";
import { CarouselWrapper } from "../../styles/CarouselWrapper";

import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import BookItem from "../bookItem/BookItem";

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
  const { categoryTitle, books } = props;

  const carouselItems = books.map((el) => {
    return (
      <BookItem
        key={el.id}
        image={el.imageUrl}
        title={el.title}
        author={el.author}
      />
    );
  });

  return (
    <CarouselWrapper>
      <div className="inner">
        <div className="title">{categoryTitle}</div>
        <Slider {...CAROUSEL_SETTINGS}>{carouselItems}</Slider>
      </div>
    </CarouselWrapper>
  );
};

export default BookCarousel;
