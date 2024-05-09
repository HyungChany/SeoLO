import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import { ChangeEvent } from 'react';

interface InputBoxProps {
  width: number;
  height: number;
  value: string | number;
  placeholder?: string;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
  isPassword?: boolean;
}

const InputBoxContainer = styled.input<InputBoxProps>`
  width: ${(props) => props.width}rem;
  height: ${(props) => props.height}rem;
  box-sizing: border-box;
  border: 1px solid ${Color.GRAY400};
  border-radius: 8px;
  padding-left: 0.7rem;
  font-size: 1.1rem;

  &::placeholder {
    color: ${Color.GRAY400};
  }
`;

const InputBox = (props: InputBoxProps) => {
  const { isPassword, ...rest } = props;
  return (
    <>
      {isPassword ? (
        <InputBoxContainer type="password" {...rest} />
      ) : (
        <InputBoxContainer {...props} />
      )}
    </>
  );
};

export default InputBox;
