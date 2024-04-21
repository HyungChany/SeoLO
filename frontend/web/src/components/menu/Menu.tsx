import styled from 'styled-components';
import React from 'react';
import { Divider } from '@/components/basic/Divider.tsx';
import * as Color from '@/config/color/Color.ts';
import EnterIcon from '@/../assets/icons/Enter.svg?react';

// 타입 정의
interface MenuProps {
  onClick: () => void;
  children: React.ReactNode;
  width: number;
  $enterSize: number;
}

// 스타일 정의
const Container = styled.div<Pick<MenuProps, 'width'>>`
  width: ${(props) => props.width}px;
`;

const MenuContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  cursor: pointer;
`;

const Enter = styled(EnterIcon)<Pick<MenuProps, '$enterSize'>>`
  width: ${(props) => props.$enterSize}px;
  height: ${(props) => props.$enterSize}px;
`;

// 컴포넌트 정의
export const Menu: React.FC<MenuProps> = ({
  onClick,
  children,
  width,
  $enterSize,
}) => {
  return (
    <Container onClick={onClick} width={width}>
      <MenuContainer>
        {children}
        <div style={{ flexGrow: 1 }} />
        <Enter $enterSize={$enterSize} />
      </MenuContainer>
      <Divider borderColor={Color.GRAY300} marginHorizontal={0} />
    </Container>
  );
};
