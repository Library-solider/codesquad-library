import React from "react";
import styled from "styled-components";
import book from "../../assets/images/book-image.jpg";

const Search = () => {
  return (
    <SearchWrapper>
      <img src={book} alt="search background" />
      <SearchInner>
        <div className="title interval">CODE SQUAD</div>
        <div className="introduction interval">
          각 언어별 서적 및 다양한 종류의 서적이 준비돼 있습니다.
        </div>
        <div>
          <input type="text" placeholder="원하는 책의 제목을 검색해 보세요 !" />
        </div>
      </SearchInner>
    </SearchWrapper>
  );
};

const SearchWrapper = styled.div`
  position: relative;
  width: 100%;
  height: 600px;
  overflow: hidden;
  img {
    width: inherit;
    filter: blur(1.5px);
    -webkit-filter: blur(1.5px);
  }
`;

const SearchInner = styled.div`
  position: absolute;
  color: white;
  top: 35%;
  left: 50%;
  transform: translateX(-50%);

  input {
    width: 50vw;
    border-radius: 4px;
    padding: 12px;
  }

  .title {
    font-weight: 800;
    margin-bottom: 10px;
    font-size: ${({ theme }) => theme.fontSizes.titleSize};
  }
`;

export default Search;
