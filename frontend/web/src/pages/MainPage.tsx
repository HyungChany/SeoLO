import * as Color from '@/config/color/Color.ts';
import * as Typo from '@/components/typography/Typography.tsx';
import styled from 'styled-components';
import Card from '@/components/card/Card.tsx';
import logoutIcon from '@/../assets/icons/Logout.png';
import { Spacer } from '@/components/basic/Spacer.tsx';

const Background = styled.div`
  box-sizing: border-box;
  min-width: 100%;
  min-height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${Color.GRAY200};
`;
const MainContainer = styled.div`
  width: 95%;
  height: 90%;
  display: flex;
  justify-content: space-between;
  padding-top: 1rem;
  padding-bottom: 1rem;
`;

const LeftContainer = styled.div`
  width: 20%;
  height: 100%;
  display: flex;
  flex-direction: column;
`;

const HeaderContainer = styled.div`
  position: relative;
  text-align: end;
`;

const Line = styled.div`
  width: 100%;
  height: 0.1rem;
  position: relative;
  margin-top: -1%;
  background-color: black;
`;

const BannerContainer = styled.div`
  position: relative;
  margin-top: -5%;
  margin-left: 10%;
`;

const Banner = styled.span`
  font-family: NYJGothicB;
  color: ${Color.BLUE100};
  font-size: 2rem;
  background-color: ${Color.GRAY200};
`;

const SideMenuBox = styled.div`
  display: flex;
  flex-grow: 1;
`;

const LogoutBtn = styled.button`
  display: flex;
  width: 12rem;
  font-family: NYJGothicB;
  font-size: 1.5rem;
  justify-content: space-evenly;
  align-items: center;
  background-color: ${Color.GRAY100};
  border-radius: 0.625rem;
  border: 2px solid rgba(0, 0, 0, 0.2);
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.25);
  margin: 0 auto;
`;

const LogoutIcon = styled.img`
  width: 50px;
`;

const RightContainer = styled.div`
  width: 78%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;
const Cards = styled.div`
  width: 100%;
  height: 20%;
  display: flex;
  justify-content: space-between;
`;
const InnerContainer = styled.div`
  padding: 1rem;
  display: flex;
  flex-direction: column;
`;

const Handle = () => {};

const MainPage = () => {
  return (
    <>
      <Background>
        <MainContainer>
          <LeftContainer>
            <HeaderContainer>
              <Typo.H3 color={Color.BLACK}>1공장 조립 라인</Typo.H3>
            </HeaderContainer>
            <Line />
            <BannerContainer>
              <Banner>Menu</Banner>
            </BannerContainer>
            <SideMenuBox></SideMenuBox>
            <LogoutBtn onClick={() => console.log('클릭')}>
              <LogoutIcon src={logoutIcon} />
              로그아웃
            </LogoutBtn>
          </LeftContainer>
          <RightContainer>
            <Card
              width={'100%'}
              height={'45vh'}
              onClick={() => console.log('클릭')}
            >
              도면
            </Card>
            <Spacer space={'2rem'} />
            <Cards>
              <Card width={'14vw'} height={'14vw'} onClick={Handle}>
                <InnerContainer>
                  <Typo.H3>등록 장비</Typo.H3>
                </InnerContainer>
              </Card>
              <Card width={'14vw'} height={'14vw'} onClick={Handle}>
                <InnerContainer>
                  <Typo.H3>등록 LOTO</Typo.H3>
                </InnerContainer>
              </Card>
              <Card width={'14vw'} height={'14vw'} onClick={Handle}>
                <InnerContainer>
                  <Typo.H3>
                    오늘의 <Typo.H1 color={Color.RED100}>LOTO</Typo.H1>
                  </Typo.H3>
                  <Typo.H3>사용현황</Typo.H3>
                </InnerContainer>
              </Card>
              <Card width={'14vw'} height={'14vw'} onClick={Handle}>
                <InnerContainer>
                  <Typo.H3>
                    이번주 <Typo.H1 color={Color.RED100}>LOTO</Typo.H1>
                  </Typo.H3>
                  <Typo.H3>사용현황</Typo.H3>
                </InnerContainer>
              </Card>
              <Card width={'14vw'} height={'14vw'} onClick={Handle}>
                <InnerContainer>
                  <Typo.H3>
                    이번 달 <Typo.H1 color={Color.RED100}>재해</Typo.H1>
                  </Typo.H3>
                  <Typo.H3>발생현황</Typo.H3>
                </InnerContainer>
              </Card>
            </Cards>
          </RightContainer>
        </MainContainer>
      </Background>
    </>
  );
};

export default MainPage;
