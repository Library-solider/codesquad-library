import React from "react";
import styled from "styled-components";
import { FaMobileAlt } from "react-icons/fa";
import { RiComputerLine } from "react-icons/ri";
import { FiCpu } from "react-icons/fi";
import { BsServer } from "react-icons/bs";
import { GiBookshelf } from "react-icons/gi";

const Category = () => {
  return (
    <CategoryWrapper>
      <CategoryInner>
        <CategoryLink href="/category/frontend">
          <RiComputerLine />
          <div>프론트엔드</div>
        </CategoryLink>

        <CategoryLink href="/category/backend">
          <BsServer />
          <div>백엔드</div>
        </CategoryLink>

        <CategoryLink href="/category/mobile">
          <FaMobileAlt />
          <div>모바일</div>
        </CategoryLink>

        <CategoryLink href="/category/computerscience">
          <FiCpu />
          <div>컴퓨터공학/IT</div>
        </CategoryLink>

        <CategoryLink href="/category/culturebook">
          <GiBookshelf />
          <div>교양서적</div>
        </CategoryLink>
      </CategoryInner>
    </CategoryWrapper>
  );
};

const CategoryWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-around;

  svg {
    width: 3rem;
    height: 3rem;
    color: ${({ theme: { colors } }) => colors.black};
    margin-bottom: ${({ theme: { margins } }) => margins.small};
  }
`;

const CategoryInner = styled.div`
  display: flex;
  justify-content: space-between;
  width: 70vw;
  overflow: auto;

  @media ${({ theme: { device } }) => device.mobileL} {
    width: 100vw;
  }
`;

const CategoryLink = styled.a`
  cursor: pointer;
  font-weight: bold;
  text-align: center;
  padding: 1.875rem;
  color: ${({ theme: { colors } }) => colors.black};

  svg {
    width: 3rem;
    height: 3rem;
    color: ${({ theme: { colors } }) => colors.black};
    margin-bottom: ${({ theme: { margins } }) => margins.small};
    transition: 0.3s;
  }

  :hover {
    svg {
      color: ${({ theme: { colors } }) => colors.blue};
    }
    color: ${({ theme: { colors } }) => colors.blue};
  }
`;

export default Category;
