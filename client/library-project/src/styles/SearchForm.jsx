import styled from "styled-components";

export const SearchForm = styled.div`
  display: flex;
  width: 50vw;
  position: relative;

  input {
    width: inherit;
    padding: ${({ theme: { paddings } }) => paddings.lg};
    border-top-left-radius: ${({ theme: { radius } }) => radius.xSmall};
    border-bottom-left-radius: ${({ theme: { radius } }) => radius.xSmall};
  }

  .search_form {
    display: flex;
    width: 50vw;
    margin-bottom: ${({ theme: { margins } }) => margins.base};
  }

  .search_result {
    position: absolute;
    z-index: 2;
    bottom: -100%;
    width: 100%;
    padding: ${({ theme: { paddings } }) => paddings.lg};
    border-radius: ${({ theme: { radius } }) => radius.xSmall};
    background-color: ${({ theme: { colors } }) => colors.white};
  }

  .search_btn {
    padding-right: ${({ theme: { paddings } }) => paddings.small};
    background-color: ${({ theme: { colors } }) => colors.white};
    border-top-right-radius: ${({ theme: { radius } }) => radius.xSmall};
    border-bottom-right-radius: ${({ theme: { radius } }) => radius.xSmall};

    svg {
      font-size: ${({ theme: { fontSizes } }) => fontSizes.xxxl};
    }
    :hover {
      color: ${({ theme: { colors } }) => colors.blue};
    }
  }

  @media ${({ theme: { device } }) => device.mobileL} {
    margin-top: ${({ theme: { margins } }) => margins.base};
    width: 100%;
    order: 2;
  }
`;
