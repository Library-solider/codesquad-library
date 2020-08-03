import querystring from "query-string";

export const calcUpdateSearchResult = () => {};

export const calcUpdatePagination = (location, page) => {
  const parsedSearchQueries = querystring.parse(location.search);
  parsedSearchQueries.page = page;

  return location.pathname + `?${querystring.stringify(parsedSearchQueries)}`;
};
