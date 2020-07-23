import React from "react";

import styled, { withTheme } from "styled-components";
import { Button } from "../../styles/Button";

const Navbar = (props) => {
  const { theme } = props;

  return (
    <NavbarWrapper>
      <Logo></Logo>
      <Button fillColor={theme.colors.green_1} textColor={theme.colors.white}>
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
`;

const Logo = styled.div`
  font-size: ${({ theme }) => theme.fontSizes.xl};
`;

export default withTheme(Navbar);
