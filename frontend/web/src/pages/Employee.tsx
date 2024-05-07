import React, { useState, ChangeEvent } from 'react';
import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import Card from '@/components/card/Card.tsx';
import * as Typo from '@/components/typography/Typography.tsx';
import InputBox from '@/components/inputbox/InputBox.tsx';
import { Button } from '@/components/button/Button.tsx';
import People from '/assets/images/people.png';

const Background = styled.div`
  width: 100%;
  height: 100%;
  background-color: ${Color.GRAY200};
  justify-content: center;
  align-items: center;
  display: flex;
`;
const Box = styled.div`
  width: 92%;
  height: 90%;
  display: flex;
  gap: 2rem;
`;
const ImgBox = styled.img`
  width: 13rem;
  height: 13rem;
`;
const InformationBox = styled.div`
  width: 80rem;
  height: 100%;
  display: flex;
  padding-top: 1rem;
  padding-left: 2.5rem;
  padding-right: 2.5rem;
  box-sizing: border-box;
  background-color: ${Color.WHITE};
  border-radius: 3.125rem;
  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.25);
  gap: 12rem;
  overflow: hidden;
`;
const LeftBox = styled.div`
  width: 25.0625rem;
  height: 100%;
  display: flex;

  flex-direction: column;
  gap: 1.5rem;
`;

const PhotoBox = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  gap: 1.5rem;
`;
const Photo = styled.div`
  width: 100%;
  height: 30rem;
  display: flex;
  justify-content: center;
  align-items: center;
  color: ${Color.GRAY400};
  border: 1px solid ${Color.GRAY300};
  border-radius: 1.25rem;
`;
// const Preview = styled.img`
//   width: 100%;
//   height: 100%;
//   border-radius: 1.25rem;
//   object-fit: cover;
// `;
const RightBox = styled.div`
  width: 100%;
  height: 35rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;
const CommonBox = styled.div`
  width: 20rem;
  height: 7rem;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
`;
const ButtonBox = styled.div`
  width: 100%;
  height: 3rem;
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
`;
const ContentBox = styled.div`
  width: 20rem;
  height: 4rem;
  display: flex;
  box-sizing: border-box;
  padding: 0.5rem;
  background-color: ${Color.GRAY100};
  border: 1px solid ${Color.GRAY300};
  border-radius: 8px;
  font-size: 1.25rem;
  align-items: center;
`;
const Content = styled.div`
  width: auto;
  height: auto;
  font-size: 1.5rem;
  font-weight: 400;
  color: ${Color.BLACK};
`;
const Employee = () => {
  // const [imagePreviewUrl, setImagePreviewUrl] = useState<string>('');
  const [equipmentNumber, setEquipmentNumber] = useState<string>('');
  const handleEquipmentNumber = (e: ChangeEvent<HTMLInputElement>) => {
    setEquipmentNumber(e.target.value);
  };

  const handleSubmit = () => {};
  return (
    <Background>
      <Box>
        <Card
          width={22}
          height={'100%'}
          justifyContent={'space-between'}
          flexDirection={'column'}
          alignItems="center"
        >
          <Typo.H3 color={Color.BLACK}>등록 임직원현황</Typo.H3>
          <ImgBox src={People} />
          <Typo.H0 color={Color.BLACK}>125</Typo.H0>
        </Card>
        <InformationBox>
          <LeftBox>
            <CommonBox>
              <Typo.H3>사번</Typo.H3>
              <InputBox
                width={20}
                height={4}
                value={equipmentNumber}
                onChange={handleEquipmentNumber}
                placeholder="사번을 입력하세요"
              />
            </CommonBox>
            <CommonBox>
              <Typo.H3>이름</Typo.H3>
              <ContentBox>
                <Content>오정민</Content>
              </ContentBox>
            </CommonBox>
            <CommonBox>
              <Typo.H3>소속 부서</Typo.H3>
              <ContentBox>
                <Content>자원 관리팀</Content>
              </ContentBox>
            </CommonBox>
            <CommonBox>
              <Typo.H3>직급</Typo.H3>
              <ContentBox>
                <Content>대리</Content>
              </ContentBox>
            </CommonBox>
          </LeftBox>
          <RightBox>
            <PhotoBox>
              <Typo.H3>작업장 사진</Typo.H3>
              <Photo>
                {/* {imagePreviewUrl ? (
                  <Preview src={imagePreviewUrl} alt="Uploaded Image Preview" />
                ) : (
                  '사진'
                )} */}
              </Photo>
            </PhotoBox>
            <ButtonBox>
              <Button
                width={5.25}
                height={3}
                $backgroundColor={Color.GRAY200}
                $borderColor={Color.GRAY200}
                $borderRadius={1.25}
                $hoverBackgroundColor={Color.GRAY200}
                $hoverBorderColor={Color.GRAY200}
                onClick={handleSubmit}
                fontSize={'1.25rem'}
                fontWeight={'bold'}
              >
                취소
              </Button>
              <Button
                width={5.25}
                height={3}
                $backgroundColor={Color.GRAY200}
                $borderColor={Color.GRAY200}
                $borderRadius={1.25}
                $hoverBackgroundColor={Color.GRAY200}
                $hoverBorderColor={Color.GRAY200}
                onClick={handleSubmit}
                fontSize={'1.25rem'}
                fontWeight={'bold'}
              >
                완료
              </Button>
            </ButtonBox>
          </RightBox>
        </InformationBox>
      </Box>
    </Background>
  );
};

export default Employee;
