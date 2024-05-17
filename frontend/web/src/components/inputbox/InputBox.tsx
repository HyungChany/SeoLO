import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import { ChangeEvent, FocusEvent } from 'react';

interface InputBoxProps {
  width: number;
  height: number;
  value: string | number;
  placeholder?: string;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
  isPassword?: boolean;
  children?: React.ReactNode;
  onBlur?: (e: FocusEvent<HTMLInputElement>) => void;
  onKeyDown?: (e: React.KeyboardEvent<HTMLInputElement>) => void;
  disabled?: boolean;
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
  &:disabled {
    background-color: ${Color.GRAY200}; // 비활성화 시 배경색 변경 (옵션)
    cursor: not-allowed; // 비활성화 시 커서 변경 (옵션)
  }
`;

const InputBox = (props: InputBoxProps) => {
  const { isPassword, ...rest } = props;
  return (
    <>
      {isPassword ? (
        <InputBoxContainer type="password" {...rest} />
      ) : (
        <InputBoxContainer {...props}>{props.children}</InputBoxContainer>
      )}
    </>
  );
};

export default InputBox;
