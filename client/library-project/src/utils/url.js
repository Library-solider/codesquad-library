export const createRequestUrl = (location) =>
  process.env.REACT_APP_DB_HOST + location.pathname + location.search;
