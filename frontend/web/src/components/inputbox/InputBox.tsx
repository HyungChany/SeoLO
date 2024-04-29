import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import { ChangeEvent } from 'react';

interface InputBoxProps {
  width: number;
  height: number;
  value: string | number;
  placeholder: string;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
}

const InputBoxContainer = styled.input<InputBoxProps>`
  width: ${(props) => props.width}rem;
  height: ${(props) => props.height}rem;
  box-sizing: border-box;
  border: 1px solid ${Color.GRAY200};
  border-radius: 8px;
  padding-left: 0.69rem;
`;

const InputBox = (props: InputBoxProps) => {
  return (
    <InputBoxContainer
      width={props.width}
      height={props.height}
      placeholder={props.placeholder}
      value={props.value}
      onChange={props.onChange}
    ></InputBoxContainer>
  );
};

export default InputBox;
