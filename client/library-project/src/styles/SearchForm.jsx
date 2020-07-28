import styled from "styled-components";

export const SearchForm = styled.div`
  display: flex;
  width: 50vw;

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
