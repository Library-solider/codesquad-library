import { useEffect, useState } from "react";

export const useFetch = (url, dependence) => {
  const [error, setError] = useState(false);
  const [response, setResponse] = useState(null);

  useEffect(() => {
    const initialFetch = async () => {
      try {
        const response = await fetch(url);
        const initialData = await response.json();
        setResponse(initialData);
      } catch (error) {
        setError(error);
      }
    };
    initialFetch();
  }, [dependence && dependence]);

  return { response, error };
};
