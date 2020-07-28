import React, { useContext } from "react";

import styled, { ThemeContext } from "styled-components";
import codesquad_logo from "../../assets/images/codesquad-logo.png";
import Search from "../search/Search";
import { Button } from "../../styles/Button";

const Navbar = () => {
  const themeContext = useContext(ThemeContext);

  return (
    <NavbarWrapper>
      <Logo>
        <img src={codesquad_logo} alt="codesquad logo" />
      </Logo>
      <Search />

      <LoginButton
        fillColor={themeContext.colors.green_1}
        textColor={themeContext.colors.white}
      >
        Login
      </LoginButton>
    </NavbarWrapper>
  );
};

const NavbarWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;

  padding: ${({ theme }) => theme.paddings.xl};
  font-size: ${({ theme }) => theme.fontSizes.base};
  background-color: ${({ theme: { colors } }) => colors.gray_1};
`;

const LoginButton = styled(Button)`
  order: 1;
`;

const Logo = styled.div`
  img {
    width: 4.5rem;
    height: 3.5rem;
  }
  font-size: ${({ theme }) => theme.fontSizes.xl};
`;

export default Navbar;
