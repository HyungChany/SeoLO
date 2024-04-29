import CheckList from '@/../assets/icons/CheckList.svg?react';
import ListModify from '@/../assets/icons/ListModify.svg?react';
import logoutIcon from '@/../assets/icons/Logout.png';
import Position from '@/../assets/icons/Position.svg?react';
import { Spacer } from '@/components/basic/Spacer.tsx';
import { Button } from '@/components/button/Button.tsx';
import Card from '@/components/card/Card.tsx';
import { Menu } from '@/components/menu/Menu.tsx';
import * as Typo from '@/components/typography/Typography.tsx';
import * as Color from '@/config/color/Color.ts';
import styled from 'styled-components';
import { useState } from 'react';

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
  min-height: 75vh;
  display: flex;
  justify-content: space-between;
`;

const LeftContainer = styled.div`
  width: 20%;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding-right: 1rem;
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
  flex-direction: column;
  flex-grow: 1;
  width: 100%;
`;
const RowContainer = styled.div`
  display: flex;
  flex-direction: row;
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
const PositionIcon = styled(Position)`
  width: 2rem;
  height: 2rem;
`;
const CheckListIcon = styled(CheckList)`
  width: 2rem;
  height: 2rem;
`;
const ListModifyIcon = styled(ListModify)`
  width: 2rem;
  height: 2rem;
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
  const [modifyMode, setModifyMode] = useState<boolean>(false);

  const changeModifyMode = () => {
    setModifyMode((prevMode) => !prevMode);
    console.log(modifyMode);
  };

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
            <SideMenuBox>
              <Menu
                onClick={() => console.log('클릭')}
                width={'100%'}
                $enterSize={1}
              >
                <PositionIcon />
                <Typo.Body1B color={Color.ONYX}>작업장 위치 선택</Typo.Body1B>
              </Menu>
              <Spacer space={'1.5rem'} />
              <Menu
                onClick={() => console.log('클릭')}
                width={'100%'}
                $enterSize={1}
              >
                <CheckListIcon />
                <Typo.Body1B color={Color.ONYX}>새 작업장 추가</Typo.Body1B>
              </Menu>
              <Spacer space={'1.5rem'} />
              <Menu onClick={changeModifyMode} width={'100%'} $enterSize={1}>
                <ListModifyIcon />
                <Typo.Body1B color={Color.ONYX}>작업장 편집</Typo.Body1B>
              </Menu>
              <Spacer space={'1.5rem'} />
              {modifyMode && (
                <RowContainer>
                  <Button
                    onClick={changeModifyMode}
                    width={3.5}
                    height={1}
                    $backgroundColor={Color.GRAY100}
                    $borderColor={Color.GRAY100}
                    $borderRadius={0.3}
                    $hoverBackgroundColor={Color.RED100}
                    $hoverBorderColor={Color.GRAY300}
                  >
                    <Typo.Detail0>취소</Typo.Detail0>
                  </Button>
                  <Spacer space={'1rem'} horizontal={true} />
                  <Button
                    onClick={changeModifyMode}
                    width={3.5}
                    height={1}
                    $backgroundColor={Color.GRAY100}
                    $borderColor={Color.GRAY100}
                    $borderRadius={0.3}
                    $hoverBackgroundColor={Color.GREEN400}
                    $hoverBorderColor={Color.GRAY300}
                  >
                    <Typo.Detail0>완료</Typo.Detail0>
                  </Button>
                </RowContainer>
              )}
            </SideMenuBox>
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
