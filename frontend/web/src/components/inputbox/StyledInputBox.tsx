import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import { ChangeEvent, FocusEvent, FormEvent, useEffect } from 'react';

interface InputBoxProps {
  width: number;
  height: number;
  value: string;
  placeholder?: string;
  onChange: (e: ChangeEvent<HTMLTextAreaElement>) => void;
  isPassword?: boolean;
  children?: React.ReactNode;
  onBlur?: (e: FocusEvent<HTMLTextAreaElement>) => void;
  min?: string;
  max?: string;
  maxLength?: number;
}
const ValueCnt = styled.div`
  width: 100%;
  justify-content: flex-end;
  display: flex;
  margin-left: auto;
`;
const ColoredSpan = styled.span`
  text-align: right;
  font-size: 0.75rem;
  color: ${Color.GRAY500};
  font-weight: bold;
`;

const TextAreaBox = styled.div`
  display: flex;
  flex-direction: column;
  width: auto;
  gap: 0.3rem;
`;
const InputBoxContainer = styled.textarea<InputBoxProps>`
  width: ${(props) => props.width}rem;
  height: ${(props) => props.height}rem;
  box-sizing: border-box;
  border: 1px solid ${Color.GRAY400};
  border-radius: 8px;
  /* padding-left: 0.7rem; */
  resize: none;
  font-size: 1rem;
  box-sizing: border-box;

  &::placeholder {
    color: ${Color.GRAY400};
  }
`;

const StyledInputBox = ({ onChange, ...props }: InputBoxProps) => {
  function maxLengthCheck(event: FormEvent<HTMLTextAreaElement>) {
    const input = event.currentTarget; // 현재 이벤트가 발생한 input 요소
    if (input.maxLength > 0 && input.value.length > input.maxLength) {
      input.value = input.value.slice(0, input.maxLength);
      onChange({
        ...event,
        target: event.currentTarget,
      } as ChangeEvent<HTMLTextAreaElement>);
    }
  }
  useEffect(() => {
    if (props && props.maxLength) {
      if (props.value?.length > props.maxLength) {
        const newValue = props.value.slice(0, props.maxLength);
        onChange({
          target: { value: newValue } as HTMLTextAreaElement,
        } as ChangeEvent<HTMLTextAreaElement>);
      }
    }
  }, [props]);
  return (
    <TextAreaBox>
      <InputBoxContainer
        {...props}
        onChange={onChange}
        onInput={maxLengthCheck as React.FormEventHandler<HTMLTextAreaElement>}
      >
        {props.children}
      </InputBoxContainer>
      {props.maxLength && (
        <ValueCnt>
          <ColoredSpan>
            {props.value.length}/{props.maxLength}
          </ColoredSpan>
        </ValueCnt>
      )}
    </TextAreaBox>
  );
};

export default StyledInputBox;
