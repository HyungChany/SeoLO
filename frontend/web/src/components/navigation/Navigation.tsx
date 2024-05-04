import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import * as Typo from '@/components/typography/Typography.tsx';
import { Link } from 'react-router-dom';

const NavigatorBox = styled.div`
  box-sizing: border-box;
  width: 100%;
  height: 6rem;
  padding: 3rem;
  display: flex;
  justify-content: center;
  background-color: ${Color.SNOW};
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  overflow-x: hidden;
  z-index: 100;
`;
const NavigatorContent = styled.div`
  display: flex;
  width: 100%;
  /* width: calc(100% - 2 * 2.65625rem); */
  /* height: calc(100% - 2 * 2rem); */
  align-items: center;
  justify-content: space-between;
`;
const Title = styled(Link)`
  width: 11.125rem;
  height: 100%;
  align-items: center;
  justify-content: center;
  color: ${Color.BLUE100};
  margin-right: 3rem;
  font-family: esamanru;
  font-size: 3rem;
  font-style: normal;
  font-weight: 700;
  display: flex;
  cursor: pointer;
  text-decoration: none;
`;
const Menu = styled.div`
  display: flex;
  gap: 3rem;
`;

const MenuLink = styled(Link)`
  text-decoration: none;
`;

const Navigation = () => {
  return (
    <NavigatorBox>
      <NavigatorContent>
        <Title to="/">SeoLo</Title>
        <Menu>
          <MenuLink to="/1">
            <Typo.H3>LOTO 일지</Typo.H3>
          </MenuLink>
          <MenuLink to="/2">
            <Typo.H3>LOTO 현황</Typo.H3>
          </MenuLink>
          <MenuLink to="/checklist">
            <Typo.H3>체크리스트 관리</Typo.H3>
          </MenuLink>
          <MenuLink to="/information">
            <Typo.H3>작업장 정보</Typo.H3>
          </MenuLink>
          <MenuLink to="/1">
            <Typo.H3>임직원 정보</Typo.H3>
          </MenuLink>
        </Menu>
      </NavigatorContent>
    </NavigatorBox>
  );
};

export default Navigation;
