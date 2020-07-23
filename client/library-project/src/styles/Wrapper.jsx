import styled from "styled-components";

export const Wrapper = styled.div`
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: center;

  margin-top: ${({ theme }) => theme.interval.xl};
  margin-bottom: ${({ theme }) => theme.interval.xl};

  .inner {
    width: 70vw;
  }

  .title {
    font-weight: bold;
    margin: ${({ theme: { margins } }) => margins.small};
    font-size: ${({ theme: { fontSizes } }) => fontSizes.xxxl};
  }

  .carousel_item {
    padding: ${({ theme: { paddings } }) => paddings.small};
    .book_title {
      padding: ${({ theme: { paddings } }) => paddings.small};
      font-weight: bold;
      text-align: center;
    }
    img {
      width: inherit;
    }
  }
`;
