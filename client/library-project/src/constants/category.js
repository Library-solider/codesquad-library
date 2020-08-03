import { FaMobileAlt } from "react-icons/fa";
import { RiComputerLine } from "react-icons/ri";
import { FiCpu } from "react-icons/fi";
import { BsServer } from "react-icons/bs";
import { GiBookshelf } from "react-icons/gi";

export const CATEGORY_LIST_DATA = [
  {
    CATEGORY_ID: 1,
    CATEGORY_TITLE: "프론트엔드",
    HREF: "/category/1",
    ICON: RiComputerLine,
  },
  {
    CATEGORY_ID: 2,
    CATEGORY_TITLE: "교양",
    HREF: "/category/2",
    ICON: GiBookshelf,
  },
  {
    CATEGORY_ID: 3,
    CATEGORY_TITLE: "벡엔드",
    HREF: "/category/3",
    ICON: BsServer,
  },
  {
    CATEGORY_ID: 4,
    CATEGORY_TITLE: "모바일",
    HREF: "/category/4",
    ICON: FaMobileAlt,
  },
  {
    CATEGORY_ID: 5,
    CATEGORY_TITLE: "컴퓨터공학/IT",
    HREF: "/category/5",
    ICON: FiCpu,
  },
];
