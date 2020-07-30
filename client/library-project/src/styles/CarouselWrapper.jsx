import styled from "styled-components";

export const CarouselWrapper = styled.div`
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
`;
