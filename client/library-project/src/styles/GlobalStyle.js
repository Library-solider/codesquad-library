import { createGlobalStyle } from "styled-components";
import reset from "styled-reset";

export const GlobalStyle = createGlobalStyle`
${reset}

* {
    box-sizing: border-box;
    font-size: 10px;
  }

html, body {
 font-size : 1.6rem;
}

`;
