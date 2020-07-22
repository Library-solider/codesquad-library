import styled from "styled-components";

export const Wrapper = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;

  margin-top: ${({ theme }) => theme.interval.xl};
  margin-bottom: ${({ theme }) => theme.interval.xl};

  .inner {
    width: 70%;
  }

  .title {
    font-weight: bold;
    font-size: ${({ theme: { fontSizes } }) => fontSizes.xxxl};
  }

  .test_carousel {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr;

    img {
      padding: 5px;
    }
  }
`;
