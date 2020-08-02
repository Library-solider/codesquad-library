const calcRem = (size) => `${size / 16}rem`;

const fontSizes = {
  small: calcRem(14),
  base: calcRem(16),
  lg: calcRem(18),
  xl: calcRem(20),
  xxl: calcRem(22),
  xxxl: calcRem(24),
  h3: calcRem(30),
  titleSize: calcRem(50),
};

const paddings = {
  xs: calcRem(4),
  small: calcRem(8),
  base: calcRem(10),
  lg: calcRem(12),
  xl: calcRem(14),
  xxl: calcRem(16),
  xxxl: calcRem(18),
};

const margins = {
  small: calcRem(8),
  base: calcRem(10),
  lg: calcRem(12),
  xl: calcRem(14),
  xxl: calcRem(16),
  xxxl: calcRem(18),
};

const lineHeight = {
  small: calcRem(24),
  base: calcRem(30),
  lg: calcRem(36),
  xl: calcRem(42),
  xxl: calcRem(48),
  xxxl: calcRem(54),
};

const radius = {
  xSmall: calcRem(4),
  small: calcRem(6),
  base: calcRem(8),
  lg: calcRem(10),
  xl: calcRem(12),
  xxl: calcRem(14),
};

const interval = {
  base: calcRem(50),
  lg: calcRem(100),
  xl: calcRem(150),
  xxl: calcRem(200),
};

const verticalInterval = {
  base: `${calcRem(10)} 0 ${calcRem(10)} 0`,
  lg: `${calcRem(12)} 0 ${calcRem(12)} 0`,
  xl: `${calcRem(14)} 0 ${calcRem(14)} 0`,
  xxl: `${calcRem(16)} 0 ${calcRem(16)} 0`,
  xxxl: `${calcRem(18)} 0 ${calcRem(18)} 0`,
};

const horizontalInterval = {
  base: `0 ${calcRem(10)} 0 ${calcRem(10)}`,
  lg: `0 ${calcRem(12)} 0 ${calcRem(12)}`,
  xl: `0 ${calcRem(14)} 0 ${calcRem(14)}`,
  xxl: `0 ${calcRem(16)} 0 ${calcRem(16)}`,
  xxxl: `0 ${calcRem(18)} 0 ${calcRem(18)}`,
};

const deviceSizes = {
  mobileS: "320px",
  mobileM: "375px",
  mobileL: "450px",
  tablet: "768px",
  tabletL: "1024px",
};

const colors = {
  black: "#000000",
  white: "#FFFFFF",
  gray_1: "#222222",
  gray_2: "#767676",
  green_1: "#3cb46e",
  blue: "#0366d6",
  blue_2: "rgb(31, 140, 230)",
};

const device = {
  mobileS: `only screen and (max-width: ${deviceSizes.mobileS})`,
  mobileM: `only screen and (max-width: ${deviceSizes.mobileM})`,
  mobileL: `only screen and (max-width: ${deviceSizes.mobileL})`,
  tablet: `only screen and (max-width: ${deviceSizes.tablet})`,
  tabletL: `only screen and (max-width: ${deviceSizes.tabletL})`,
};

const gradient = {
  gradient_1:
    "linear-gradient(180deg,rgba(0,0,0,.38) 0,rgba(0,0,0,.38) 3.5%,rgba(0,0,0,.379) 7%,rgba(0,0,0,.377) 10.35%,rgba(0,0,0,.375) 13.85%,rgba(0,0,0,.372) 17.35%,rgba(0,0,0,.369) 20.85%,rgba(0,0,0,.366) 24.35%,rgba(0,0,0,.364) 27.85%,rgba(0,0,0,.361) 31.35%,rgba(0,0,0,.358) 34.85%,rgba(0,0,0,.355) 38.35%,rgba(0,0,0,.353) 41.85%,rgba(0,0,0,.351) 45.35%,rgba(0,0,0,.35) 48.85%,rgba(0,0,0,.353) 52.35%,rgba(0,0,0,.36) 55.85%,rgba(0,0,0,.371) 59.35%,rgba(0,0,0,.385) 62.85%,rgba(0,0,0,.402) 66.35%,rgba(0,0,0,.42) 69.85%,rgba(0,0,0,.44) 73.35%,rgba(0,0,0,.46) 76.85%,rgba(0,0,0,.48) 80.35%,rgba(0,0,0,.498) 83.85%,rgba(0,0,0,.515) 87.35%,rgba(0,0,0,.529) 90.85%,rgba(0,0,0,.54) 94.35%,rgba(0,0,0,.547) 97.85%,rgba(0,0,0,.55));",
};

const boxShadow = {
  boxShadow_1: "0 2px 5px 1px rgba(0, 0, 0, 0.2)",
};

const theme = {
  fontSizes,
  colors,
  deviceSizes,
  device,
  paddings,
  margins,
  interval,
  verticalInterval,
  horizontalInterval,
  lineHeight,
  gradient,
  radius,
  boxShadow,
};

export default theme;
