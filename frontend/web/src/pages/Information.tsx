import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import Card from '@/components/card/Card.tsx';
import WorkPlace from '/assets/images/workplace.png';
import * as Typo from '@/components/typography/Typography.tsx';
import Equipment from '/assets/images/equipment.png';
import People from '/assets/images/people.png';
import Dropdown from '@/components/dropdown/DropDown.tsx';

const Background = styled.div`
  width: 100%;
  height: 100%;
  background-color: ${Color.GRAY200};
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-left: 6rem;
  padding-right: 6rem;
  box-sizing: border-box;
`;
const ImgBox = styled.img`
  width: 13rem;
  height: 13rem;
`;
const CompanyInformation = () => {
  return (
    <Background>
      {/* <MainContainer> */}
      <Card
        width={20.625}
        height={30}
        justifyContent={'space-between'}
        flexDirection={'column'}
        alignItems="center"
      >
        <Typo.H3 color={Color.BLACK}>등록 작업장 수</Typo.H3>
        <ImgBox src={WorkPlace} />
        <Typo.H0 color={Color.BLACK}>3</Typo.H0>
      </Card>
      <Card
        width={20.625}
        height={30}
        justifyContent={'space-between'}
        flexDirection={'column'}
        alignItems="center"
      >
        <Typo.H3 color={Color.BLACK}>현재 작업장의 장비 현황</Typo.H3>
        <Dropdown />
        <ImgBox src={Equipment} />
        <Typo.H0 color={Color.BLACK}>34</Typo.H0>
      </Card>
      <Card
        width={20.625}
        height={30}
        justifyContent={'space-between'}
        flexDirection={'column'}
        alignItems="center"
      >
        <Typo.H3 color={Color.BLACK}>등록 임직원현황</Typo.H3>
        <ImgBox src={People} />
        <Typo.H0 color={Color.BLACK}>125</Typo.H0>
      </Card>
      {/* </MainContainer> */}
    </Background>
  );
};

export default CompanyInformation;
