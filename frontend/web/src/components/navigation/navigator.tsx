import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import * as Typo from '@/components/typography/Typography';

const NavigatorBox = styled.div`
  box-sizing: border-box;
  width: 100%;
  height: 7.5rem;
  padding: 2rem 2.65625rem;
  display: flex;
  top: 0;
  background-color: ${Color.SNOW};
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
const Title = styled.div`
  width: 11.125rem;
  height: 100%;
  align-items: center;
  justify-content: center;
  color: ${Color.BLUE100};
  font-size: 3rem;
  font-style: normal;
  font-weight: 700;
  display: flex;
`;
const Menu = styled.div`
  width: 100%;
  margin-right: 1.62rem;
  gap: 9.38rem;
  /* justify-content: space-between; */
  display: flex;
  flex-direction: row;
`;
export const TopNavigator = () => {
  return (
    <NavigatorBox>
      <NavigatorContent>
        <Title>SeoLo</Title>
        <Menu>
          <Typo.H3>LOTO 일지</Typo.H3>
          <Typo.H3>체크리스트 관리</Typo.H3>
          <Typo.H3>사업장 정보조회/수정</Typo.H3>
        </Menu>
      </NavigatorContent>
    </NavigatorBox>
  );
};
