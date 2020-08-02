import { createGlobalStyle, css } from "styled-components";
import reset from "styled-reset";

const font = css`
  @font-face {
    font-family: "RIDIBatang";
    src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_twelve@1.0/RIDIBatang.woff")
      format("woff");
    font-weight: normal;
    font-style: normal;
  }
`;

export const GlobalStyle = createGlobalStyle`
  ${reset};
  ${font};

  * {
    box-sizing: border-box;
    font-size: 16px;
  }

  body {
    height: 100%;    
  }

  button {
    background: inherit;
    border: none;
    box-shadow: none;
    border-radius: 0;
    padding: 0;
    overflow: visible;
    cursor: pointer;
    outline : none;
  }

  input {
    border : none;

    :focus  {
     outline : none;
    }
  }

  a {
    color: ${({ theme: { colors } }) => colors.white};
    text-decoration: none;
    outline: none
    }

  .interval {
    margin-bottom : 1.2rem;
  }
`;
