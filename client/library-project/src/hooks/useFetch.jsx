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
