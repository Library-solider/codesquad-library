import React from "react";
import styled from "styled-components";
import book from "../../assets/images/book-image.jpg";

const MAIN_TITLE = "CODESQUAD LIBRARY";
const INTRODUCTION = "각 언어별 서적 및 다양한 종류의 서적이 준비돼 있습니다.";

const Introduce = () => {
  return (
    <IntroduceWrapper>
      <img src={book} alt="search background" />
      <IntroduceInner>
        <div>
          <div className="title interval">{MAIN_TITLE}</div>
          <div className="introduction interval">{INTRODUCTION}</div>
        </div>
      </IntroduceInner>
    </IntroduceWrapper>
  );
};

const IntroduceWrapper = styled.div`
  position: relative;
  width: 100vw;
  height: 500px;
  overflow: hidden;
  text-align: center;

  @media ${({ theme: { device } }) => device.mobileL} {
    height: 300px;
    font-size: 14px;
  }
`;

const IntroduceInner = styled.div`
  position: absolute;
  width: inherit;
  height: inherit;
  display: flex;
  justify-content: center;
  align-items: center;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  color: ${({ theme: { colors } }) => colors.white};
  background: ${({ theme: { gradient } }) => gradient.gradient_1};

  .title {
    font-style: italic;
    font-weight: 800;
    margin-bottom: ${({ theme: { margins } }) => margins.base};
    font-size: ${({ theme }) => theme.fontSizes.titleSize};
  }
`;

export default Introduce;
