import styled, { css } from 'styled-components';
import React from 'react';
import * as Color from '@/config/color/Color.ts';

type DataItem = {
  content: string;
  event: () => void;
  active: boolean;
};

interface CustomRadioButtonProps {
  data: DataItem[];
  width?: number;
}

interface StyledButtonProps {
  active: boolean;
}

const StyledDiv = styled.div<CustomRadioButtonProps>`
  display: flex;
  flex-direction: row;
  width: ${(props) =>
    props.width
      ? (props.width * 100) / 100
      : 100}vw; // 화면 너비에 대한 백분율로 수정
  height: 30vh; // 높이를 vh 단위로 변경
  justify-content: space-around;
  align-items: center;
  gap: 7vw; // column-gap 대신 gap 사용
  padding-left: 7vw;
  padding-right: 7vw;
`;

const StyledButton = styled.button<StyledButtonProps>`
  flex: 1;
  height: 30vh;
  border: 1px solid ${Color.GRAY400};
  border-radius: 10vw; // 반응형 웹 디자인을 위해 vw 사용
  justify-content: center;
  align-items: center;
  display: flex;
  ${(props) =>
    props.active &&
    css`
      background-color: ${Color.GREEN400};
      border-color: ${Color.GREEN400};
    `}
`;

const CustomRadioButton = (props: CustomRadioButtonProps) => {
  return (
    <StyledDiv width={props.width} data={props.data}>
      {props.data.map(({ content, event, active }, index) => (
        <StyledButton key={index} active={active} onClick={event}>
          <span color={active ? Color.WHITE : Color.BLACK}>{content}</span>
        </StyledButton>
      ))}
    </StyledDiv>
  );
};

export default CustomRadioButton;
