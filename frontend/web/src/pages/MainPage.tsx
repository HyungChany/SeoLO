import CheckList from '@/../assets/icons/CheckList.svg?react';
import ListModify from '@/../assets/icons/ListModify.svg?react';
import logoutIcon from '@/../assets/icons/Logout.png';
import Position from '@/../assets/icons/Position.svg?react';
import { Logout } from '@/apis/Login.ts';
import { Spacer } from '@/components/basic/Spacer.tsx';
import { Button } from '@/components/button/Button.tsx';
import Card from '@/components/card/Card.tsx';
import { Leaflet } from '@/components/leaflet/Leafet.tsx';
import { Menu } from '@/components/menu/Menu.tsx';
import * as Typo from '@/components/typography/Typography.tsx';
import * as Color from '@/config/color/Color.ts';
import 'leaflet/dist/leaflet.css';
import { useState } from 'react';
import { MapContainer } from 'react-leaflet';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

const Background = styled.div`
  box-sizing: border-box;
  width: 100%;
  min-height: 100%;
  display: flex;
  align-items: center;
  background-color: ${Color.GRAY200};
  overflow-x: auto;
  position: relative;
`;

const MainContainer = styled.div`
  box-sizing: content-box;
  min-width: 1280px;
  height: 34rem;
  display: flex;
  justify-content: space-between;
  margin: 0 auto;
`;

const LeftContainer = styled.div`
  width: 23%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

const HeaderContainer = styled.div`
  position: relative;
  text-align: end;
  margin: 7% 1% 3%;
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
  margin: -6% 0% 5% 7%;
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
  cursor: pointer;
`;

const LogoutIcon = styled.img`
  width: 50px;
`;

const PositionIcon = styled(Position)`
  width: 2.5rem;
  margin-right: 5%;
`;

const CheckListIcon = styled(CheckList)`
  width: 2.5rem;
  margin-right: 5%;
`;

const ListModifyIcon = styled(ListModify)`
  width: 2.5rem;
  margin-right: 5%;
`;

const RightContainer = styled.div`
  width: 75%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

const Cards = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;

const CardDrawing = styled.div`
  width: 100%;
  height: 65%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${Color.SNOW};
  border-radius: 1.25rem;
  box-shadow: 0 4px 10px 0 rgba(0, 0, 0, 0.25);
  overflow: hidden;
  transition: background-color 0.3s;

  &:hover {
    background-color: ${Color.GRAY100};
    cursor: pointer;
  }

  &:active {
    background-color: ${Color.GRAY200};
  }
`;

const InnerContainer = styled.div`
  flex-direction: column;
`;

const Handle = () => {};

const MainPage = () => {
  const [modifyMode, setModifyMode] = useState<boolean>(false);
  const [imageFile, setImageFile] = useState<string | null>(null);
  const navigate = useNavigate();

  // 작업장 편집모드 활성화, 비활성화
  const changeModifyMode = () => {
    setModifyMode((prevMode) => !prevMode);
    console.log(modifyMode);
  };

  // 작업장 추가
  const handleImageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files ? event.target.files[0] : null;
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        setImageFile(e.target?.result as string);
      };
      reader.readAsDataURL(file);
    }
  };

  // 로그아웃
  const handleLogout = () => {
    const fetchLogout = async () => {
      try {
        await Logout();
        navigate('/login');
      } catch (e) {
        console.log(e);
      }
    };
    fetchLogout();
  };

  return (
    <>
      <Background>
        <MainContainer>
          <LeftContainer>
            <HeaderContainer>
              <Typo.H4 color={Color.BLACK}>1공장 조립 라인</Typo.H4>
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
              <Spacer space={'1rem'} />
              <input
                type="file"
                onChange={handleImageChange}
                style={{ display: 'none' }}
                id="fileInput"
              />
              <label htmlFor="fileInput">
                <Menu
                  width={'100%'}
                  $enterSize={1}
                  onClick={() => console.log()}
                >
                  <CheckListIcon />
                  <Typo.Body1B color={Color.ONYX}>새 작업장 추가</Typo.Body1B>
                </Menu>
              </label>
              <Spacer space={'1rem'} />
              <Menu onClick={changeModifyMode} width={'100%'} $enterSize={1}>
                <ListModifyIcon />
                <Typo.Body1B color={Color.ONYX}>작업장 편집</Typo.Body1B>
              </Menu>
              <Spacer space={'1rem'} />
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
            <LogoutBtn onClick={handleLogout}>
              <LogoutIcon src={logoutIcon} />
              로그아웃
            </LogoutBtn>
          </LeftContainer>
          <RightContainer>
            {imageFile ? (
              <CardDrawing>
                <MapContainer
                  center={[0, 0]}
                  zoom={1}
                  scrollWheelZoom={true}
                  style={{ height: '100%', width: '100%', overflow: 'hidden' }}
                  attributionControl={false}
                >
                  {imageFile && (
                    <Leaflet imageFile={imageFile} modifyMode={modifyMode} />
                  )}
                </MapContainer>
              </CardDrawing>
            ) : (
              <CardDrawing onClick={() => console.log('클릭')}>
                <input
                  type="file"
                  onChange={handleImageChange}
                  style={{ display: 'none' }}
                  id="fileInput"
                />
                <label htmlFor="fileInput">
                  <Typo.Body0B color={Color.GRAY400}>
                    저장된 도면이 없습니다. 도면을 추가하세요.
                  </Typo.Body0B>
                  <Spacer space={'1rem'} />
                  <Typo.Body0B color={Color.GRAY400}>
                    권장 이미지 비율은 다음과 같습니다. 가로 5: 세로 3
                  </Typo.Body0B>
                </label>
              </CardDrawing>
            )}
            <Cards>
              <Card width={'11rem'} height={'10rem'} onClick={Handle}>
                <InnerContainer>
                  <Typo.H4>
                    <div style={{ marginTop: '0.5rem' }}>등록 장비</div>
                  </Typo.H4>
                </InnerContainer>
              </Card>
              <Card width={'11rem'} height={'10rem'} onClick={Handle}>
                <InnerContainer>
                  <Typo.H4>
                    <div style={{ marginTop: '0.5rem' }}>등록 LOTO</div>
                  </Typo.H4>
                </InnerContainer>
              </Card>
              <Card width={'11rem'} height={'10rem'} onClick={Handle}>
                <InnerContainer>
                  <Typo.H4>
                    <div
                      style={{
                        display: 'flex',
                        justifyContent: 'space-between',
                      }}
                    >
                      <div style={{ margin: '0.5rem 0.5rem 0 0' }}>오늘의</div>
                      <Typo.H2 color={Color.RED100}>LOTO</Typo.H2>
                    </div>
                    사용현황
                  </Typo.H4>
                </InnerContainer>
              </Card>
              <Card width={'11rem'} height={'10rem'} onClick={Handle}>
                <InnerContainer>
                  <Typo.H4>
                    <div
                      style={{
                        display: 'flex',
                        justifyContent: 'space-between',
                      }}
                    >
                      <div style={{ margin: '0.5rem 0.3rem 0 0' }}>이번 주</div>
                      <Typo.H2 color={Color.RED100}>LOTO</Typo.H2>
                    </div>
                    사용현황
                  </Typo.H4>
                </InnerContainer>
              </Card>
              <Card width={'11rem'} height={'10rem'} onClick={Handle}>
                <InnerContainer>
                  <Typo.H4>
                    <div
                      style={{
                        display: 'flex',
                        justifyContent: 'space-between',
                      }}
                    >
                      <div style={{ margin: '0.5rem 0.8rem 0 0' }}>이번 달</div>
                      <Typo.H2 color={Color.RED100}>재해</Typo.H2>
                    </div>
                    발생현황
                  </Typo.H4>
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
