import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import React from 'react';

interface CardProps {
  width: number | string;
  height: number | string;
  children: React.ReactNode;
  onClick: () => void;
}
const CardContainer = styled.div<CardProps>`
  width: ${(props) =>
    typeof props.width === 'number' ? `${props.width}rem` : props.width};
  height: ${(props) =>
    typeof props.height === 'number' ? `${props.height}rem` : props.height};
  background-color: ${Color.SNOW};
  display: flex;
  border-radius: 1.25rem;
  box-shadow: 0 4px 10px 0 rgba(0, 0, 0, 0.25);
`;

const Card = (props: CardProps) => {
  return (
    <CardContainer
      width={props.width}
      height={props.height}
      onClick={props.onClick}
    >
      {props.children}
    </CardContainer>
  );
};

export default Card;
