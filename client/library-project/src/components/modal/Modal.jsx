import React, { useContext } from "react";
import ReactDOM from "react-dom";
import styled, { ThemeContext } from "styled-components";
import { Button } from "../../styles/Button";
import { IoMdCloseCircle } from "react-icons/io";

const Modal = ({ onCloseModal }) => {
  const themeContext = useContext(ThemeContext);

  return ReactDOM.createPortal(
    <>
      <DimmedLayer />
      <ModalWrapper>
        <header>
          <div className="title">대여 주의사항</div>
          <IoMdCloseCircle onClick={onCloseModal} />
        </header>
        <ul>
          <li>1.인당 최대 2권까지 대여 가능합니다.</li>
          <li>2.최대 대여 일수는 14일 입니다.</li>
          <li>3.14일 이상의 대여는 반납 후 재대여로 가능합니다.</li>
          <li>4.대여에 대한 파손/분실에 대해서는 개인의 책임이 있습니다.</li>
          <li>5.반납은 다음 사람을 위해서 원래 위치에 반납 부탁드립니다.</li>
        </ul>
        <footer>
          <select name="" id="">
            <option value="">대여 일수</option>
          </select>
          <RentalButton
            fillColor={themeContext.colors.green_1}
            textColor={themeContext.colors.white}
          >
            대여 완료
          </RentalButton>
        </footer>
      </ModalWrapper>
    </>,
    document.getElementById("root")
  );
};

const DimmedLayer = styled.div`
  position: fixed;
  width: 100%;
  height: 100%;
  opacity: 0.5;
  top: 0;
  left: 0;
  z-index: 3;
  background-color: ${({ theme: { colors } }) => colors.black};
`;

const ModalWrapper = styled.div`
  z-index: 4;
  position: fixed;
  box-sizing: border-box;
  border-radius: 4px;
  width: 30%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: ${({ theme: { colors } }) => colors.white};
  padding: ${({ theme: { paddings } }) => paddings.xl};
  box-shadow: ${({ theme: { boxShadow } }) => boxShadow.boxShadow_1};
  header {
    display: flex;
    justify-content: space-between;
    padding-bottom: ${({ theme: { paddings } }) => paddings.base};
    border-bottom: 2px solid ${({ theme: { colors } }) => colors.gray_2};
    .title {
      font-weight: bold;
      font-size: ${({ theme: { fontSizes } }) => fontSizes.xxxl};
    }
    svg {
      cursor: pointer;
      font-size: ${({ theme: { fontSizes } }) => fontSizes.xxxl};
    }
  }

  ul {
    padding: ${({ theme: { verticalInterval } }) => verticalInterval.base};
    margin-bottom: 32px;
    li {
      padding: ${({ theme: { verticalInterval } }) => verticalInterval.base};
    }
  }

  footer {
    position: fixed;
    padding: ${({ theme: { paddings } }) => paddings.base};
    display: flex;
    justify-content: space-around;
    width: 100%;
    bottom: 0;
    left: 0;
    button {
      width: 50%;
    }
    select {
      font-weight: bold;
      height: 32px;
      border-radius: 4px;
      width: 45%;
    }
  }

  @media ${({ theme: { device } }) => device.tabletL} {
    width: 60%;
  }

  @media ${({ theme: { device } }) => device.mobileL} {
    width: 90%;
    height: 70%;
  }
`;

const RentalButton = styled(Button)`
  width: 45%;
`;

export default Modal;
