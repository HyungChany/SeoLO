import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import React from 'react';
interface CardProps {
  width: number;
  height: number;
  children: React.ReactNode;
  onClick: () => void;
}
const CardContainer = styled.div<CardProps>`
  width: ${(props) => props.width}rem;
  height: ${(props) => props.height}rem;
  background-color: ${Color.SNOW};
  display: flex;
  border-radius: 1.25rem;
  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.25);
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
