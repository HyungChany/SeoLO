import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import * as Typo from '@/components/typography/Typography.tsx';
import { Link } from 'react-router-dom';

const NavigatorBox = styled.div`
  box-sizing: border-box;
  width: 100%;
  height: 7rem;
  padding: 2rem 2.65625rem;
  display: flex;
  top: 0;
  background-color: ${Color.SNOW};
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  /* position: fixed; */
`;
const NavigatorContent = styled.div`
  display: flex;
  width: 100%;
  /* width: calc(100% - 2 * 2.65625rem); */
  /* height: calc(100% - 2 * 2rem); */
  align-items: center;
  gap: 16.875rem;
`;
const Title = styled(Link)`
  width: 11.125rem;
  height: 100%;
  align-items: center;
  justify-content: center;
  color: ${Color.BLUE100};
  font-family: esamanru;
  font-size: 3rem;
  font-style: normal;
  font-weight: 700;
  display: flex;
  cursor: pointer;
  text-decoration: none;
`;
const Menu = styled.div`
  width: 100%;
  margin-right: 1.62rem;
  gap: 9.38rem;
  /* justify-content: space-between; */
  display: flex;
  flex-direction: row;
`;
const Navigation = () => {
  return (
    <NavigatorBox>
      <NavigatorContent>
        <Title to="/">SeoLo</Title>
        <Menu>
          <Link to="/1" style={{ textDecoration: 'none' }}>
            <Typo.H3>LOTO 일지</Typo.H3>
          </Link>
          <Link to="/2" style={{ textDecoration: 'none' }}>
            <Typo.H3>체크리스트 관리</Typo.H3>
          </Link>
          <Link to="/3" style={{ textDecoration: 'none' }}>
            <Typo.H3>사업장 정보조회/수정</Typo.H3>
          </Link>
        </Menu>
      </NavigatorContent>
    </NavigatorBox>
  );
};

export default Navigation;
