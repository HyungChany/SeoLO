import React, { useState, useRef, ChangeEvent, useEffect } from 'react';
import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import Card from '@/components/card/Card.tsx';
import Dropdown from '@/components/dropdown/DropDown.tsx';
import * as Typo from '@/components/typography/Typography.tsx';
import Equipmentimage from '/assets/images/equipment.png';
import InputBox from '@/components/inputbox/InputBox.tsx';
import { Button } from '@/components/button/Button.tsx';
import { Facilities } from '@/apis/Facilities.ts';
import {
  MachineList,
  MachinePhoto,
  MachineRegistration,
} from '@/apis/Machine.ts';

interface OptionType {
  value: number;
  label: string;
}
interface FacilityType {
  id: string;
  name: string;
}
const Background = styled.div`
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  background-color: ${Color.GRAY200};
  display: flex;
  align-items: center;
  overflow-x: auto;
  position: relative;
`;
const Box = styled.div`
  min-width: 1280px;
  height: 34rem;
  display: flex;
  justify-content: space-between;
  margin: 0 auto;
`;
const ImgBox = styled.img`
  width: 13.5rem;
`;
const InformationBox = styled.div`
  width: calc(100% - 26rem);
  height: 100%;
  display: flex;
  justify-content: space-between;
  padding: 1.8rem;
  box-sizing: border-box;
  background-color: ${Color.WHITE};
  border-radius: 1.25rem;
  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.25);
  /* overflow: hidden; */
`;
const LeftBox = styled.div`
  width: 47.5%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
`;
const DropdownBox = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;
const PhotoBox = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;
const Photo = styled.div`
  width: 100%;
  height: 15rem;
  display: flex;
  justify-content: center;
  align-items: center;
  color: ${Color.GRAY500};
  border: 1px solid ${Color.GRAY200};
  border-radius: 1.25rem;
  cursor: pointer;
  transition:
    background-color 0.5s,
    border-color 0.3s;

  &:hover {
    background-color: ${Color.GRAY200};
    border-color: ${Color.GRAY300};
  }
`;
const Preview = styled.img`
  width: 100%;
  height: 100%;
  border-radius: 1.25rem;
  /* object-fit: cover; */
`;
const PhotoInputBox = styled.input.attrs({
  type: 'file',
  accept: 'image/*', // 이미지 파일만 받도록 설정
})`
  display: none;
`;
const RightBox = styled.div`
  width: 47.5%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  box-sizing: border-box;
`;
const CommonBox = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  gap: 1rem;
`;
const IntroBox = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  flex-direction: row;
`;
const TypoBox = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  flex-direction: row;
`;
const BoxTwo = styled.div`
  display: flex;
  gap: 1rem;
  flex-direction: column;
`;

const ButtonBox = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
`;

const WidthInputBox = styled(InputBox)<{ width?: string }>`
  width: ${(props) => props.width || '100%'};
`;

const Equipment = () => {
  const [imagePreviewUrl, setImagePreviewUrl] = useState<string>('');
  const [image, setImage] = useState<File | null>(null);
  const [imageURL, setImageURL] = useState<string>('');
  const [equipmentName, setEquipmentName] = useState<string>('');
  const [equipmentNumber, setEquipmentNumber] = useState<string>('');
  const [date, setDate] = useState<string>('');
  const [mainManager, setMainManager] = useState<string>('');
  const [subManager, setSubManager] = useState<string>('');
  const [options, setOptions] = useState<OptionType[]>([]);
  const [selectedOption, setSelectedOption] = useState<OptionType | null>(null);
  const [facilities, setFacilities] = useState<number>(0);
  const [dateError, setDateError] = useState<string>('');
  const validateDate = (data: string): boolean => {
    // YYYY-MM-DD 정규식
    const regex = /^\d{4}-\d{2}-\d{2}$/;
    if (!data.match(regex)) {
      // 형식에 맞지 않는 경우
      setDateError('날짜 형식이 올바르지 않습니다. (예: 2024-05-20)');
      return false;
    }

    const d = new Date(date);
    const dNum = d.getTime();
    if (!dNum && dNum !== 0) {
      // NaN 값 체크
      setDateError('날짜 형식이 올바르지 않습니다. (예: 2024-05-20)');
      return false;
    }

    // 입력된 날짜가 현재 날짜 이후인지 체크
    if (d > new Date()) {
      setDateError('미래의 날짜는 입력할 수 없습니다.');
      return false;
    }

    return d.toISOString().slice(0, 10) === data;
  };

  // const [submitOptions, setSubmitOptions] = useState<OptionType[]>([]);
  const [selectedSubmitOption, setSelectedSubmitOption] =
    useState<OptionType | null>(null);
  const handleEquipmentName = (e: ChangeEvent<HTMLInputElement>) => {
    setEquipmentName(e.target.value);
  };
  const handleEquipmentNumber = (e: ChangeEvent<HTMLInputElement>) => {
    setEquipmentNumber(e.target.value);
  };
  const handleDate = (e: ChangeEvent<HTMLInputElement>) => {
    setDate(e.target.value);
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
      setImage(file);
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
  const handleSubmit = () => {
    if (dateError) {
      alert(dateError);
      return;
    }
    if (selectedSubmitOption && image) {
      const fetchMachine = async () => {
        const machineData = {
          facilityId: selectedSubmitOption.value,
          machineName: equipmentName,
          machineCode: equipmentNumber,
          machineThum: imageURL,
          introductionDate: date,
          mainManagerNum: mainManager,
          subManagerNum: subManager,
        };
        console.log(machineData);
        await MachineRegistration(machineData);
      };
      fetchMachine();
    }
  };
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
  const handleOptionChange = (option: OptionType): void => {
    setSelectedOption(option); // 선택된 옵션 상태 업데이트
  };
  useEffect(() => {
    const fetchEquipment = async () => {
      if (selectedOption?.value) {
        const equipmentData = await MachineList(selectedOption.value);
        console.log(equipmentData);
        setFacilities(equipmentData.length);
      }
    };
    fetchEquipment();
  }, [selectedOption]);
  useEffect(() => {
    if (imagePreviewUrl && image) {
      const fetchData = async () => {
        const machine = new FormData();
        machine.append('multipartFiles', image);
        const data = await MachinePhoto(machine);
        setImageURL(data.urls.url1);
      };
      fetchData();
    }
  }, [image, imagePreviewUrl]);

  const handleSubmitOptionChange = (option: OptionType): void => {
    setSelectedSubmitOption(option);
  };
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
          <Typo.H3>
            <div style={{ marginTop: '1.3rem' }}>현재 작업장의 장비 현황</div>
          </Typo.H3>
          <Dropdown
            options={options}
            selectedOption={selectedOption}
            onOptionChange={handleOptionChange}
            placeholder="공장을 선택하세요"
          />
          <ImgBox src={Equipmentimage} />
          <Typo.H0 color={Color.BLACK}>{facilities}</Typo.H0>
        </Card>
        <InformationBox>
          <LeftBox>
            <DropdownBox>
              <Typo.H3>등록 작업장</Typo.H3>
              <Dropdown
                options={options}
                selectedOption={selectedSubmitOption}
                onOptionChange={handleSubmitOptionChange}
                placeholder="공장을 선택하세요"
              />
            </DropdownBox>
            <PhotoBox>
              <Typo.H3>작업장 사진</Typo.H3>
              <Photo onClick={handleFileUpload}>
                {imagePreviewUrl ? (
                  <Preview src={imagePreviewUrl} alt="Uploaded Image Preview" />
                ) : (
                  '사진을 업로드해주세요'
                )}
                <PhotoInputBox ref={fileInputRef} onChange={handleFileChange} />
              </Photo>
            </PhotoBox>
          </LeftBox>
          <RightBox>
            <CommonBox>
              <Typo.H3>장비 명</Typo.H3>
              <WidthInputBox
                height={3}
                value={equipmentName}
                onChange={handleEquipmentName}
                placeholder="ex) 레이저 웰더"
              />
            </CommonBox>
            <CommonBox>
              <Typo.H3>장비 번호</Typo.H3>
              <WidthInputBox
                height={3}
                value={equipmentNumber}
                onChange={handleEquipmentNumber}
                placeholder="ex)  L / W - 2"
              />
            </CommonBox>
            <CommonBox>
              <Typo.H3>도입 일자</Typo.H3>
              <IntroBox>
                <WidthInputBox
                  height={3}
                  value={date}
                  onChange={handleDate}
                  placeholder="ex) 2024-05-20"
                  onBlur={() => {
                    if (!validateDate(date)) {
                      console.log('Invalid date');
                    } else {
                      setDateError(''); // 오류가 없으면 오류 메시지 초기화
                    }
                  }}
                />
              </IntroBox>
            </CommonBox>
            <CommonBox>
              <TypoBox>
                <BoxTwo>
                  <Typo.H3>담당자(정)</Typo.H3>
                  <InputBox
                    width={11}
                    height={3}
                    value={mainManager}
                    onChange={handleMainManager}
                    placeholder="ex) 김대한"
                  />
                </BoxTwo>
                <BoxTwo>
                  <Typo.H3>담당자(부)</Typo.H3>
                  <InputBox
                    width={11}
                    height={3}
                    value={subManager}
                    onChange={handleSubManager}
                    placeholder="ex) 박민국"
                  />
                </BoxTwo>
              </TypoBox>
            </CommonBox>
            <ButtonBox>
              <Button
                width={5}
                height={2.5}
                $backgroundColor={Color.GRAY200}
                $borderColor={Color.GRAY200}
                $borderRadius={0.75}
                $hoverBackgroundColor={Color.GRAY300}
                $hoverBorderColor={Color.GRAY300}
                onClick={handleSubmit}
                fontSize={'1.2'}
                fontWeight={'bold'}
              >
                취소
              </Button>
              <Button
                width={5}
                height={2.5}
                $backgroundColor={Color.GRAY200}
                $borderColor={Color.GRAY200}
                $borderRadius={0.75}
                $hoverBackgroundColor={Color.GRAY300}
                $hoverBorderColor={Color.GRAY300}
                onClick={handleSubmit}
                fontSize={'1.2'}
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
