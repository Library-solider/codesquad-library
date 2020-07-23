import React from "react";
import { Wrapper } from "../../styles/Wrapper";
import sample_6 from "../../assets/images/sample-6.jpg";

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
      breakpoint: 768,
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

const BookCarousel = ({ carouselTitle, bookList }) => {
  return (
    <Wrapper>
      <div className="inner">
        <div className="title">IOS 관련 추천 서적</div>
        <Slider {...CAROUSEL_SETTINGS}>
          <div className="carousel_item">
            <img src={sample_6} alt="" />
            <div className="book_title">지금의 비행산업은 어떻게 형성됐나</div>
          </div>
          <div className="carousel_item">
            <img src={sample_6} alt="" />
            <div className="book_title">추악한 시대에도 아름다움을 본 당신</div>
          </div>
          <div className="carousel_item">
            <img src={sample_6} alt="" />
            <div className="book_title">피가 아닌 마음을 훔친 첫사랑</div>
          </div>
          <div className="carousel_item">
            <img src={sample_6} alt="" />
            <div className="book_title">사연은 다르지만 같은 곳의 우리</div>
          </div>
        </Slider>
      </div>
    </Wrapper>
  );
};

export default BookCarousel;
