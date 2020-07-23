import React from "react";
import { ThemeProvider } from "styled-components";
import { GlobalStyle } from "./styles/GlobalStyle";
import theme from "./styles/theme";

import Navbar from "./components/navbar/Navbar";
import Search from "./components/search/Search";
import IntroBooks from "./components/introBooks/IntroBooks";

const App = () => {
  return (
    <div>
      <ThemeProvider theme={theme}>
        <GlobalStyle />
        <Navbar />
        <Search />
        <IntroBooks />
      </ThemeProvider>
    </div>
  );
};

export default App;
