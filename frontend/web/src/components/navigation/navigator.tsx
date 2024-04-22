import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import * as Typo from '@/components/typography/Typography';

const NavigatorBox = styled.div`
  box-sizing: border-box;
  width: 100%;
  height: 7.5rem;
  padding: 2rem 2.65625rem;
  align-items: center;
  justify-content: center;
  display: flex;
  top: 0;
  background-color: ${Color.SNOW};
  /* position: fixed; */
`;
const NavigatorContent = styled.div`
  display: flex;
  /* width: 50%; */
  width: calc(100% - 2 * 2.65625rem);
  align-items: center;
  gap: 16.875rem;
`;
const Title = styled.div`
  width: 11.125rem;
  height: 3.5rem;
  color: ${Color.BLUE100};
  font-size: 3rem;
  font-style: normal;
  font-weight: 700;
  display: flex;
`;
export const TopNavigator = () => {
  return (
    <NavigatorBox>
      <NavigatorContent>
        <Title>SeoLo</Title>
      </NavigatorContent>
    </NavigatorBox>
  );
};
