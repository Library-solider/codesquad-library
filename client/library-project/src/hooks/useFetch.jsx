import { useEffect } from "react";

export const useFetch = (url, updateData) => {
  const initialFetch = async () => {
    try {
      const response = await fetch(url);
      const initialData = await response.json();
      updateData(initialData);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    initialFetch();
  }, []);
};

export const useBookFetch = (requestUrl, updateData, searchQueries) => {
  const fetchBookList = async () => {
    try {
      const response = await fetch(requestUrl);
      const data = await response.json();

      updateData(data);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    window.scrollTo(0, 0);
    fetchBookList();
  }, [searchQueries.page]);
};
