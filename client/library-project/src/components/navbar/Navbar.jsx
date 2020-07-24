import React, { useContext } from "react";

import styled, { ThemeContext } from "styled-components";
import { Button } from "../../styles/Button";

const Navbar = () => {
  const themeContext = useContext(ThemeContext);

  return (
    <NavbarWrapper>
      <Logo></Logo>
      <Button
        fillColor={themeContext.colors.green_1}
        textColor={themeContext.colors.white}
      >
        Login
      </Button>
    </NavbarWrapper>
  );
};

const NavbarWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: ${({ theme }) => theme.paddings.xl};
  font-size: ${({ theme }) => theme.fontSizes.base};
  background-color: ${({ theme: { colors } }) => colors.gray_1};
`;

const Logo = styled.div`
  font-size: ${({ theme }) => theme.fontSizes.xl};
`;

export default Navbar;
