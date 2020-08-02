import styled from "styled-components";

export const PaginationWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;

  margin-top: 100px;
  margin-bottom: 100px;

  svg {
    font-size: 30px;
  }
`;

export const PaginationInner = styled.div`
  width: 70%;
  display: flex;
  justify-content: center;

  a {
    display: flex;
    align-items: center;
    justify-content: center;
    height: inherit;
    width: inherit;
  }

  li,
  button {
    font-weight: bold;
    width: 38px;
    height: 38px;
  }
`;

export const Pages = styled.ul`
  display: flex;
  border: 1px solid ${({ theme: { colors } }) => colors.gray_2};

  li:first-child {
    border-top-left-radius: 3px;
    border-bottom-left-radius: 3px;
  }
  li:last-child {
    border-top-right-radius: 3px;
    border-bottom-right-radius: 3px;
    border-right: none;
  }
`;

export const Page = styled.li`
  border-right: 1px solid ${({ theme: { colors } }) => colors.gray_2};

  a {
    background-color: ${(props) => props.isActive && props.theme.colors.blue_2};
    color: ${(props) =>
      props.isActive ? props.theme.colors.white : props.theme.colors.gray_2};
  }

  :hover {
    opacity: 0.5;
  }
`;

export const MoveButton = styled.button`
  border-radius: 3px;
  margin: 0 3px 0 3px;

  path {
    color: ${({ theme: { colors } }) => colors.black};
  }
`;
