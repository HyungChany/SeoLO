import styled from 'styled-components';
import * as Color from '@/config/color/Color';
interface CardProps {
  width: number;
  height: number;
}
const CardContainer = styled.div<CardProps>`
  width: ${(props) => props.width}rem;
  height: ${(props) => props.height}rem;
  background-color: ${Color.SNOW};
  display: flex;
  border-radius: 1.25rem;
  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.25);
`;
export const Card = () => {
  return <></>;
};
