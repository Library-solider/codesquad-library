import styled from "styled-components";

export const Button = styled.button`
  border-radius: 4px;
  height: 32px;
  font-size: 14px;
  line-height: 30px;
  padding: 0 ${({ theme }) => theme.paddings.lg};
  border: 1px solid transparent;

  color: ${(props) => (props.textColor ? props.textColor : "#000000")};

  background-color: ${(props) =>
    props.fillColor ? props.fillColor : "#FFFFFF"};

  :hover {
    transition: 0.3s;
    opacity: 0.8;
  }
`;
