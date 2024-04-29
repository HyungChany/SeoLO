import * as Color from '@/config/color/Color.ts';
import * as Typo from '@/components/typography/Typography.tsx';
import styled from 'styled-components';
import Card from '@/components/card/Card.tsx';
import logoutIcon from '@/../assets/icons/Logout.png';

const Background = styled.div`
  box-sizing: border-box;
  min-width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${Color.GRAY200};
`;
const Box = styled.div`
  min-width: 85rem;
  height: 34rem;
  display: flex;
  justify-content: space-between;
`;

const LeftBox = styled.div`
  width: 28%;
  height: 100%;
  display: flex;
  flex-direction: column;
`;

const MainName = styled.div`
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

const MenuBox = styled.div`
  position: relative;
  margin-top: -5%;
  margin-left: 10%;
`;

const Menu = styled.span`
  font-family: NYJGothicB;
  color: ${Color.BLUE100};
  font-size: 2rem;
  background-color: ${Color.GRAY200};
`;

const SideMenuBox = styled.div`
  display: flex;
  flex-grow: 1;
`;

const LogoutBtn = styled.div`
  display: flex;
  width: 12rem;
  font-family: NYJGothicB;
  font-size: 1.5rem;
  justify-content: space-evenly;
  align-items: center;
  background-color: ${Color.GRAY100};
  border-radius: 0.625rem;
  border: 2px solid rgba(0, 0, 0, 0.2);
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  margin-top: auto;
  margin: 0 auto;
`;

const LogoutIcon = styled.img`
  width: 50px;
`;

const RightBox = styled.div`
  width: 70%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  /* background-color: aqua; */
`;
const Blueprint = styled.div`
  width: 100%;
  height: 65%;
  border-radius: 0.625rem;
  box-shadow: 0px 5px 5px 1px rgba(0, 0, 0, 0.2);
  background-color: #ffffff;
`;
const Cards = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  /* background-color: blueviolet; */
`;
const Handle = () => {};

const MainPage = () => {
  return (
    <>
      <Background>
        <Box>
          <LeftBox>
            <MainName>
              <Typo.H3 color={Color.BLACK}>1공장 조립 라인</Typo.H3>
            </MainName>
            <Line />
            <MenuBox>
              <Menu>Menu</Menu>
            </MenuBox>
            <SideMenuBox></SideMenuBox>
            <LogoutBtn>
              <LogoutIcon src={logoutIcon} />
              로그아웃
            </LogoutBtn>
          </LeftBox>
          <RightBox>
            <Blueprint></Blueprint>
            <Cards>
              <Card width={170} height={170} onClick={Handle}>
                gdg
              </Card>
              <Card width={170} height={170} onClick={Handle}>
                gdgdgd
              </Card>
              <Card width={170} height={170} onClick={Handle}>
                gdgdgd
              </Card>
              <Card width={170} height={170} onClick={Handle}>
                gdgdgd
              </Card>
              <Card width={170} height={170} onClick={Handle}>
                gdgdgd
              </Card>
            </Cards>
          </RightBox>
        </Box>
      </Background>
    </>
  );
};

export default MainPage;
