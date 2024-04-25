import * as Color from '@/config/color/Color.ts';
import * as Typo from '@/components/typography/Typography.tsx';
import styled from 'styled-components';
import Card from '@/components/card/Card.tsx';

const Background = styled.div`
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${Color.GRAY200};
`;
const Box = styled.div`
  width: 85rem;
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

const Line = styled.div`
  width: 100%;
  height: 0.1rem;
  position: relative;
  margin-top: 10%;
  background-color: black;
`;

const MenuName = styled.div`
  position: relative;
  margin-top: -5%;
  margin-left: 10%;
`;

const ColoredText = styled.span`
  font-family: NYJGothicB;
  color: ${Color.BLUE100};
  font-size: 2rem;
  background-color: ${Color.GRAY200};
`;

const RightBox = styled.div`
  width: 70%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
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
  height: 11rem;
  display: flex;
  justify-content: space-between;
`;
const Handle = () => {};

const MainPage = () => {
  return (
    <>
      <Background>
        <Box>
          <LeftBox>
            <Line />
            <MenuName>
              <ColoredText>Menu</ColoredText>
            </MenuName>
          </LeftBox>
          <RightBox>
            <Blueprint></Blueprint>
            <Cards>
              <Card width={11} height={11} onClick={Handle}>
                gdgdgd
              </Card>
              <Card width={11} height={11} onClick={Handle}>
                gdgdgd
              </Card>
              <Card width={11} height={11} onClick={Handle}>
                gdgdgd
              </Card>
              <Card width={11} height={11} onClick={Handle}>
                gdgdgd
              </Card>
              <Card width={11} height={11} onClick={Handle}>
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
