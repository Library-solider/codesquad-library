import React, { useContext } from "react";

import { ThemeContext } from "styled-components";

import Introduce from "../components/introduce/Introduce";
import Category from "../components/category/Category";
import IntroBooks from "../components/introBooks/IntroBooks";

const IntroPage = () => {
  const themeContext = useContext(ThemeContext);
  const { colors } = themeContext;
  return (
    <>
      <Introduce />
      <Category
        fillColor={colors.white}
        fontColor={colors.black}
        isActive={false}
      />
      <IntroBooks />
    </>
  );
};

export default IntroPage;
