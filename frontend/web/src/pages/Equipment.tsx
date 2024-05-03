import React, { useState, useRef, ChangeEvent } from 'react';
import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import Card from '@/components/card/Card.tsx';
import Dropdown from '@/components/dropdown/DropDown.tsx';
import * as Typo from '@/components/typography/Typography.tsx';
import Equipmentimage from '/assets/images/equipment.png';
import InputBox from '@/components/inputbox/InputBox.tsx';
import { Button } from '@/components/button/Button.tsx';
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
  justify-content: space-between;
`;
const ImgBox = styled.img`
  width: 13rem;
  height: 13rem;
`;
const InformationBox = styled.div`
  width: 60rem;
  height: 100%;
  display: flex;
  padding-top: 1rem;
  padding-left: 2.5rem;
  padding-right: 2.5rem;
  box-sizing: border-box;
  background-color: ${Color.WHITE};
  border-radius: 3.125rem;
  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.25);
  justify-content: space-between;
`;
const LeftBox = styled.div`
  width: 25.0625rem;
  height: 35rem;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  gap: 1.5rem;
`;
const DropdownBox = styled.div`
  width: 19.375rem;
  height: 8rem;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
`;
const PhotoBox = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  gap: 0.5rem;
`;
const Photo = styled.div`
  width: 25.0625rem;
  height: 23.125rem;
  display: flex;
  justify-content: center;
  align-items: center;
  color: ${Color.GRAY400};
  border: 1px solid ${Color.GRAY300};
  border-radius: 1.25rem;
`;
const Preview = styled.img`
  width: 100%;
  height: 100%;
  border-radius: 1.25rem;
`;
const PhotoInputBox = styled.input.attrs({
  type: 'file',
  accept: 'image/*', // 이미지 파일만 받도록 설정
})`
  display: none;
`;
const RightBox = styled.div`
  width: 20rem;
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
const IntroBox = styled.div`
  width: 20rem;
  height: 4.59rem;
  display: flex;
  justify-content: space-between;
  flex-direction: row;
`;
const TypoBox = styled.div`
  width: 20rem;
  height: 1.75rem;
  display: flex;
  justify-content: space-between;
  flex-direction: row;
`;
const ButtonBox = styled.div`
  width: 20rem;
  height: 3rem;
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
`;

const Equipment = () => {
  const [imagePreviewUrl, setImagePreviewUrl] = useState<string>('');
  const [equipmentName, setEquipmentName] = useState<string>('');
  const [equipmentNumber, setEquipmentNumber] = useState<string>('');
  const [year, setYear] = useState<string>('');
  const [month, setMonth] = useState<string>('');
  const [mainManager, setMainManager] = useState<string>('');
  const [subManager, setSubManager] = useState<string>('');
  const handleEquipmentName = (e: ChangeEvent<HTMLInputElement>) => {
    setEquipmentName(e.target.value);
  };
  const handleEquipmentNumber = (e: ChangeEvent<HTMLInputElement>) => {
    setEquipmentNumber(e.target.value);
  };
  const handleYear = (e: ChangeEvent<HTMLInputElement>) => {
    setYear(e.target.value);
  };
  const handleMonth = (e: ChangeEvent<HTMLInputElement>) => {
    setMonth(e.target.value);
  };
  const handleMainManager = (e: ChangeEvent<HTMLInputElement>) => {
    setMainManager(e.target.value);
  };
  const handleSubManager = (e: ChangeEvent<HTMLInputElement>) => {
    setSubManager(e.target.value);
  };
  const fileInputRef = useRef<HTMLInputElement>(null);
  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files.length > 0) {
      const file = event.target.files[0];

      // Create an image preview
      const reader = new FileReader();
      reader.onloadend = () => {
        setImagePreviewUrl(reader.result as string);
      };
      reader.readAsDataURL(file);
    }
  };

  const handleFileUpload = async () => {
    fileInputRef.current?.click();
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
          <Typo.H3 color={Color.BLACK}>현재 작업장의 장비 현황</Typo.H3>
          <Dropdown />
          <ImgBox src={Equipmentimage} />
          <Typo.H0 color={Color.BLACK}>34</Typo.H0>
        </Card>
        <InformationBox>
          <LeftBox>
            <DropdownBox>
              <Typo.H3>등록 작업장</Typo.H3>
              <Dropdown />
            </DropdownBox>
            <PhotoBox onClick={handleFileUpload}>
              <Typo.H3>작업장 사진</Typo.H3>
              <Photo>
                {imagePreviewUrl && (
                  <Preview src={imagePreviewUrl} alt="Uploaded Image Preview" />
                )}
                <PhotoInputBox ref={fileInputRef} onChange={handleFileChange} />
              </Photo>
            </PhotoBox>
          </LeftBox>
          <RightBox>
            <CommonBox>
              <Typo.H3>장비 명</Typo.H3>
              <InputBox
                width={20}
                height={4}
                value={equipmentName}
                onChange={handleEquipmentName}
                placeholder="장비명을 입력하세요"
              />
            </CommonBox>
            <CommonBox>
              <Typo.H3>장비 번호</Typo.H3>
              <InputBox
                width={20}
                height={4}
                value={equipmentNumber}
                onChange={handleEquipmentNumber}
                placeholder="장비번호를 입력하세요"
              />
            </CommonBox>
            <CommonBox>
              <Typo.H3>도입 일자</Typo.H3>
              <IntroBox>
                <InputBox
                  width={12}
                  height={4.59}
                  value={year}
                  onChange={handleYear}
                  placeholder="2024"
                />
                <InputBox
                  width={7}
                  height={4.59}
                  value={month}
                  onChange={handleMonth}
                  placeholder="12"
                />
              </IntroBox>
            </CommonBox>
            <CommonBox>
              <TypoBox>
                <Typo.H3>담당자(정)</Typo.H3>
                <Typo.H3>담당자(부)</Typo.H3>
              </TypoBox>
              <IntroBox>
                <InputBox
                  width={8}
                  height={4.59}
                  value={mainManager}
                  onChange={handleMainManager}
                  placeholder="ex)김진명"
                />
                <InputBox
                  width={8}
                  height={4.59}
                  value={subManager}
                  onChange={handleSubManager}
                  placeholder="ex)오정민"
                />
              </IntroBox>
            </CommonBox>
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

export default Equipment;
