import React, { useState, useEffect } from "react";
import { useMediaQuery } from "react-responsive";

export const useIsMobile = () => {
  const isMobileQueryResult = useMediaQuery({ maxWidth: "450px" });
  const [isMobile, setIsMobile] = useState(false);

  useEffect(() => {
    setIsMobile(isMobileQueryResult);
  }, [isMobileQueryResult]);

  return isMobile;
};
