import styled from "styled-components";

export const SearchBookWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  margin-top: ${({ theme: { margins } }) => margins.xxxl};
`;

export const SearchBookInner = styled.div`
  width: 70vw;
  display: flex;
  flex-wrap: wrap;

  img {
    width: 100%;
  }

  .book_item {
    width: 20%;
  }

  @media ${({ theme: { device } }) => device.tabletL} {
    .book_item {
      width: 33.3333%;
    }
  }

  @media ${({ theme: { device } }) => device.mobileL} {
    .book_item {
      width: 100%;
    }
  }
`;
