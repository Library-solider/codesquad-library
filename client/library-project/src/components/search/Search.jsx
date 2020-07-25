import React from "react";
import styled from "styled-components";
import { SearchForm } from "../../styles/SearchForm";
import book from "../../assets/images/book-image.jpg";
import { FiSearch } from "react-icons/fi";

const MAIN_TITLE = "📖CODESQUAD";
const INTRODUCTION = "각 언어별 서적 및 다양한 종류의 서적이 준비돼 있습니다.";

const Search = () => {
  return (
    <SearchWrapper>
      <img src={book} alt="search background" />
      <SearchInner>
        <div>
          <div className="title interval">{MAIN_TITLE}</div>
          <div className="introduction interval">{INTRODUCTION}</div>
          <SearchForm>
            <input
              type="text"
              placeholder="원하는 책의 제목을 검색해 보세요 !"
            />
            <button className="search_btn">
              <FiSearch />
            </button>
            <div className="search_result"></div>
          </SearchForm>
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

  .title {
    font-weight: 800;
    margin-bottom: ${({ theme: { margins } }) => margins.base};
    font-size: ${({ theme }) => theme.fontSizes.titleSize};
  }
`;

export default Search;
