import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
interface CardProps {
  width: number | string;
  height: number | string;
  children: React.ReactNode;
  onClick?: () => void;
  justifyContent?: string;
  flexDirection?: string;
  alignItems?: string;
  gap?: number;
}
const CardContainer = styled.div<CardProps>`
  width: ${(props) =>
    typeof props.width === 'number' ? `${props.width}rem` : props.width};
  height: ${(props) =>
    typeof props.height === 'number' ? `${props.height}rem` : props.height};
  background-color: ${Color.SNOW};
  display: flex;
  border-radius: 1.25rem;
  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.25);
  justify-content: ${(props) => props.justifyContent};
  flex-direction: ${(props) => props.flexDirection};
  align-items: ${(props) => props.alignItems};
  gap: ${(props) => `${props.gap}rem`};
  padding: 0.5rem;
  box-sizing: border-box;
  cursor: ${(props) => (props.onClick ? 'pointer' : 'auto')};
`;

const Card = (props: CardProps) => {
  return (
    <CardContainer
      width={props.width}
      height={props.height}
      onClick={props.onClick}
      justifyContent={props.justifyContent}
      flexDirection={props.flexDirection}
      alignItems={props.alignItems}
      gap={props.gap}
    >
      {props.children}
    </CardContainer>
  );
};

export default Card;
