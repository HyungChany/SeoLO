import styled from 'styled-components';
import React from 'react';
import { Divider } from '@/components/basic/Divider.tsx';
import * as Color from '@/config/color/Color.ts';
import EnterIcon from '@/../assets/icons/Enter.svg?react';

// 타입 정의
interface MenuProps {
  onClick: () => void;
  width: number;
  $enterSize: number;
  children: React.ReactNode;
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

/**
 * 네비게이션 메뉴 또는 다른 목적으로 사용될 수 있는 메뉴 컴포넌트입니다. 이 컴포넌트는 자식 요소와
 * 함께 사용자 정의 크기의 Enter 아이콘을 포함합니다. 메뉴는 클릭 가능하며, 호버 효과를 포함하고 있습니다.
 *
 * @param props 컴포넌트 설정을 위한 프로퍼티:
 * - `onClick`: 버튼 클릭 시 호출되는 함수입니다.
 * - `children`: 메뉴 내부에 표시될 내용입니다.
 * - `width`: 컨테이너의 너비를 설정합니다.
 * - `$enterSize`: Enter 아이콘의 크기를 설정합니다.
 *
 * ### 사용 예시
 * ```jsx
 * <Menu onClick={handleClick} width={300} $enterSize={24}>
 *   <span>Menu Item 1</span>
 *   <span>Menu Item 2</span>
 * </Menu>
 * ```
 *
 * @returns JSX.Element - 스타일이 적용된 메뉴 요소를 반환합니다.
 */

// 컴포넌트 정의
export const Menu: React.FC<MenuProps> = ({
  onClick,
  width,
  $enterSize,
  children,
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
