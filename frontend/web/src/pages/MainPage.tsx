import CheckList from '@/../assets/icons/CheckList.svg?react';
import ListModify from '@/../assets/icons/ListModify.svg?react';
import logoutIcon from '@/../assets/icons/Logout.png';
import Position from '@/../assets/icons/Position.svg?react';
import { Facilities } from '@/apis/Facilities.ts';
import { Logout } from '@/apis/Login.ts';
import { MainInformation } from '@/apis/Main.ts';
import { Spacer } from '@/components/basic/Spacer.tsx';
import { Button } from '@/components/button/Button.tsx';
import Card from '@/components/card/Card.tsx';
import Dropdown from '@/components/dropdown/DropDown.tsx';
import { Leaflet } from '@/components/leaflet/Leafet.tsx';
import { Menu } from '@/components/menu/Menu.tsx';
import * as Typo from '@/components/typography/Typography.tsx';
import * as Color from '@/config/color/Color.ts';
import 'leaflet/dist/leaflet.css';
import { useEffect, useState } from 'react';
import { MapContainer } from 'react-leaflet';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
interface NumberType {
  color: string;
  marginTop?: string;
  marginBottom?: string;
}
interface FacilityType {
  id: string;
  name: string;
}

interface OptionType {
  value: number;
  label: string;
}
interface MainInformationType {
  num_all_machines_in_this_facility: number;
  num_all_lockers_in_this_company: number;
  num_toody_task_historiese_in_this_facility: number;
  num_this_week_task_historiese_in_this_facility: number;
  num_all_accidents_in_this_company: number;
}
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
  display: flex;
  justify-content: center;
  text-align: center;
  margin: 4% 0 12%;
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
  width: 10rem;
  font-family: NYJGothicB;
  font-size: 1.3rem;
  justify-content: space-evenly;
  align-items: center;
  background-color: ${Color.GRAY100};
  border-radius: 0.625rem;
  border: 2px solid rgba(0, 0, 0, 0.2);
  box-shadow: 0 5px 6px rgba(0, 0, 0, 0.3);
  margin: 0 auto;
  padding: 0.3rem 0;
  cursor: pointer;

  &:hover {
    background-color: ${Color.RED};
  }

  &:active {
    background-color: ${Color.RED1};
  }
`;

const LogoutIcon = styled.img`
  width: 2.3rem;
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

const NumberContainer = styled.div<NumberType>`
  width: 100%;
  height: auto;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 6rem;
  font-weight: 700;
  color: ${(props) => props.color};
  margin-top: ${(props) => props.marginTop || '0'};
  margin-bottom: ${(props) => props.marginBottom || '0'};
`;

const Handle = () => {};

const MainPage = () => {
  const [modifyMode, setModifyMode] = useState<boolean>(false);
  const [imageFile, setImageFile] = useState<string | null>(null);
  const [mainInformation, setMainInformation] = useState<MainInformationType>();
  const [options, setOptions] = useState<OptionType[]>([]);
  // const [selectedOption, setSelectedOption] = useState<OptionType>(options[0]);
  const [selectedOption, setSelectedOption] = useState<OptionType | null>(null);
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

  const handleOptionChange = (option: OptionType): void => {
    setSelectedOption(option);
  };

  useEffect(() => {
    const fetchData = async () => {
      if (selectedOption) {
        const data = await MainInformation(selectedOption?.value);
        setMainInformation(data);
      }
    };
    fetchData();
  }, [selectedOption]);
  useEffect(() => {
    const fetchData = async () => {
      const data = await Facilities();
      const newOptions = data.map((facility: FacilityType) => ({
        value: facility.id,
        label: facility.name,
      }));
      setOptions(newOptions);
    };
    fetchData();
  }, []);
  useEffect(() => {
    if (options.length > 0) {
      setSelectedOption(options[0]);
    }
  }, [options]);

  return (
    <>
      <Background>
        <MainContainer>
          <LeftContainer>
            <HeaderContainer>
              <Dropdown
                options={options}
                selectedOption={selectedOption}
                onOptionChange={handleOptionChange}
                placeholder="공장을 선택하세요"
              />
              {/* <Typo.H4 color={Color.BLACK}>1공장 조립 라인</Typo.H4> */}
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
                <Typo.Body1B color={Color.ONYX}>마커 위치 편집</Typo.Body1B>
              </Menu>
              <Spacer space={'1rem'} />
              {modifyMode && (
                <RowContainer>
                  <Button
                    onClick={changeModifyMode}
                    width={3.5}
                    height={2}
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
                    height={2}
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
              <Card
                width={11}
                height={11}
                // onClick={Handle}
                flexDirection="column"
              >
                <InnerContainer>
                  <Typo.H4>
                    <div style={{ marginTop: '0.3rem' }}>등록 장비</div>
                  </Typo.H4>
                </InnerContainer>
                <NumberContainer color={Color.GREEN400} marginTop="1rem">
                  {mainInformation?.num_all_machines_in_this_facility}
                </NumberContainer>
              </Card>
              <Card
                width={11}
                height={11}
                // onClick={Handle}
                flexDirection="column"
              >
                <InnerContainer>
                  <Typo.H4>
                    <div style={{ marginTop: '0.3rem' }}>자물쇠 현황</div>
                  </Typo.H4>
                </InnerContainer>
                <NumberContainer color={Color.GREEN400} marginTop="1rem">
                  {mainInformation?.num_all_lockers_in_this_company}
                </NumberContainer>
              </Card>
              <Card
                width={11}
                height={11}
                // onClick={Handle}
                flexDirection="column"
              >
                <InnerContainer>
                  <Typo.H4>
                    <div
                      style={{
                        display: 'flex',
                        justifyContent: 'space-between',
                      }}
                    >
                      <div style={{ marginTop: '0.3rem' }}>오늘의</div>
                      <Typo.H2 color={Color.RED100}>LOTO</Typo.H2>
                    </div>
                    작업내역
                  </Typo.H4>
                  <NumberContainer color={Color.RED100} marginTop="-0.7rem">
                    {
                      mainInformation?.num_toody_task_historiese_in_this_facility
                    }
                  </NumberContainer>
                </InnerContainer>
              </Card>
              <Card
                width={11}
                height={11}
                // onClick={Handle}
                flexDirection="column"
              >
                <InnerContainer>
                  <Typo.H4>
                    <div
                      style={{
                        display: 'flex',
                        justifyContent: 'space-between',
                      }}
                    >
                      <div style={{ marginTop: '0.3rem' }}>이번 주</div>
                      <Typo.H2 color={Color.RED100}>LOTO</Typo.H2>
                    </div>
                    사용현황
                  </Typo.H4>
                </InnerContainer>
                <NumberContainer color={Color.RED100} marginTop="-0.7rem">
                  {
                    mainInformation?.num_this_week_task_historiese_in_this_facility
                  }
                </NumberContainer>
              </Card>
              <Card
                width={11}
                height={11}
                // onClick={Handle}
                flexDirection="column"
              >
                <InnerContainer>
                  <Typo.H4>
                    <div
                      style={{
                        display: 'flex',
                        justifyContent: 'space-between',
                      }}
                    >
                      <div style={{ marginTop: '0.3rem' }}>이번 달</div>
                      <Typo.H2 color={Color.RED100}>재해</Typo.H2>
                    </div>
                    발생현황
                  </Typo.H4>
                </InnerContainer>
                <NumberContainer color={Color.RED100} marginTop="-0.7rem">
                  {mainInformation?.num_all_accidents_in_this_company}
                </NumberContainer>
              </Card>
            </Cards>
          </RightContainer>
        </MainContainer>
      </Background>
    </>
  );
};

export default MainPage;
