import React from "react";
import styled from "styled-components";
import book from "../../assets/images/book-image.jpg";

const Search = () => {
  return (
    <SearchWrapper>
      <img src={book} alt="search background" />
      <SearchInner>
        <div>
          <div className="title interval">CODE SQUAD LIBEARY</div>
          <div className="introduction interval">
            각 언어별 서적 및 다양한 종류의 서적이 준비돼 있습니다.
          </div>
          <div>
            <input
              type="text"
              placeholder="원하는 책의 제목을 검색해 보세요 !"
            />
          </div>
        </div>
      </SearchInner>
    </SearchWrapper>
  );
};

const SearchWrapper = styled.div`
  position: relative;
  width: 100vw;
  height: 600px;
  overflow: hidden;
`;

const SearchInner = styled.div`
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

  input {
    width: 50vw;
    border-radius: 4px;
    padding: ${({ theme: { paddings } }) => paddings.lg};
  }

  img {
  }

  .title {
    font-weight: 800;
    margin-bottom: 10px;
    font-size: ${({ theme }) => theme.fontSizes.titleSize};
  }
`;

export default Search;
