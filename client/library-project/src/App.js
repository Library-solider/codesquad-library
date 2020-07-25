import React from "react";
import { ThemeProvider } from "styled-components";
import { GlobalStyle } from "./styles/GlobalStyle";
import theme from "./styles/theme";

import Navbar from "./components/navbar/Navbar";
import Search from "./components/search/Search";
import IntroBooks from "./components/introBooks/IntroBooks";
import CopyRight from "./components/copyRight/CopyRight";
import Category from "./components/category/Category";

const App = () => {
  return (
    <div>
      <ThemeProvider theme={theme}>
        <GlobalStyle />
        <Navbar />
        <Search />
        <Category />
        <IntroBooks />
        <CopyRight />
      </ThemeProvider>
    </div>
  );
};

export default App;
