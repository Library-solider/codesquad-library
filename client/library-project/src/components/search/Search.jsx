import React from "react";
import { SearchForm } from "../../styles/SearchForm";
import { FiSearch } from "react-icons/fi";

const Search = () => {
  return (
    <SearchForm>
      <input type="text" placeholder="원하는 책의 제목을 검색해 보세요 !" />
      <button className="search_btn">
        <FiSearch />
      </button>
      {/* <div className="search_result"></div> */}
    </SearchForm>
  );
};

export default Search;
