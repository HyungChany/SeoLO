import { Button } from '@/components/button/Button.tsx';
import * as Color from '@/config/color/Color.ts';
import { useState } from 'react';
import styled from 'styled-components';
import Check from '/assets/icons/Check.svg';
import NonCheck from '/assets/icons/NonCheck.svg';
interface ButtonProps {
  backgroundColor: string;
  color: string;
}
interface EquipmentData {
  selected: boolean;
  equipmentNumber: string;
  equipmentName: string;
  manager: string;
  lotoPurpose: string;
  accidentOccurred: string;
  accidentType: string;
  casualties: number;
  startTime: string;
  endTime: string;
}
interface TitleType {
  width?: string;
  justifyContent?: string;
}

const Title = styled.div<TitleType>`
  width: ${(props) => props.width || '10%'};
  height: auto;
  display: flex;
  align-items: center;
  font-size: 1.25rem;
  font-weight: 900;
  justify-content: ${(props) => props.justifyContent || 'flex-start'};
`;
const TitleBox = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: row;
`;
const SelectBox = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  justify-content: space-between;
  flex-direction: row;
  padding: 0 1.5rem 0 1.5rem;
  box-sizing: border-box;
`;
const ButtonBox = styled.div`
  width: auto;
  height: auto;
  display: flex;
  gap: 0.4rem;
  flex-direction: row;
  justify-content: space-between;
`;
const MainBox = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  box-sizing: border-box;
  padding: 2rem 1rem 0 1rem;
  flex-direction: column;
  gap: 2rem;
`;
const SelectButton = styled.div<ButtonProps>`
  width: 3.4rem;
  height: 2rem;
  background-color: ${(props) => props.backgroundColor};
  border: 1px solid ${Color.GRAY100};
  border-radius: 1.25rem;
  font-size: 1rem;
  font-weight: bold;
  color: ${(props) => props.color};
  justify-content: center;
  align-items: center;
  display: flex;
  cursor: pointer;
`;
const ContentBox = styled.div`
  width: 100%;
  height: 80%;
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  gap: 1.2rem;
`;
const Report = () => {
  const [selectedButtonIndex, setSelectedButtonIndex] = useState<number>(0);
  const handleButtonClick = (index: number) => {
    setSelectedButtonIndex(index); // 클릭된 버튼의 인덱스로 상태 업데이트
    console.log(index);
  };
  const handleSubmit = () => {
    console.log('클릭');
  };
  const selectTitle = ['전체', '일', '주', '월', '년'];
  const titleList = [
    '선택',
    '장비 번호',
    '장비 명',
    '담당자',
    'LOTO 목적',
    '사고 여부',
    '사고 유형',
    '인명피해(명)',
    '시작 일시',
    '종료 일시',
  ];
  const [equipment, setEquipment] = useState<EquipmentData[]>([
    {
      selected: true,
      equipmentNumber: 'A/W - 1',
      equipmentName: 'Wire bonder',
      manager: '오민상',
      lotoPurpose: '정비 수지 작업',
      accidentOccurred: 'N',
      accidentType: '-',
      casualties: 0,
      startTime: '24.04.06 - 11:00',
      endTime: '24.04.06 - 14:00',
    },
    {
      selected: false,
      equipmentNumber: 'A/W - 1',
      equipmentName: 'Wire bonder',
      manager: '오민상',
      lotoPurpose: '정비 수지 작업',
      accidentOccurred: 'N',
      accidentType: '-',
      casualties: 0,
      startTime: '24.04.06 - 11:00',
      endTime: '24.04.06 - 14:00',
    },
    // 추가 데이터는 여기에...
  ]);

  const handleSelectToggle = (index: number) => {
    const updatedEquipment = equipment.map((item, idx) => {
      if (idx === index) return { ...item, selected: !item.selected };
      return item;
    });
    setEquipment(updatedEquipment);
  };
  //   const dummyData: EquipmentData[] = [
  //     {
  //       selected: true,
  //       equipmentNumber: 'A/W - 1',
  //       equipmentName: 'Wire bonder',
  //       manager: '오민상',
  //       lotoPurpose: '정비 수지 작업',
  //       accidentOccurred: 'N',
  //       accidentType: '-',
  //       casualties: 0,
  //       startTime: '24.04.06 - 11:00',
  //       endTime: '24.04.06 - 14:00',
  //     },
  //     {
  //       selected: false,
  //       equipmentNumber: 'A/W - 1',
  //       equipmentName: 'Wire bonder',
  //       manager: '오민상',
  //       lotoPurpose: '정비 수지 작업',
  //       accidentOccurred: 'N',
  //       accidentType: '-',
  //       casualties: 0,
  //       startTime: '24.04.06 - 11:00',
  //       endTime: '24.04.06 - 14:00',
  //     },
  //     // 더 많은 데이터를 추가할 수 있습니다.
  //   ];
  return (
    <MainBox>
      <SelectBox>
        <ButtonBox>
          {selectTitle.map((title, index) => (
            <SelectButton
              key={index}
              backgroundColor={
                selectedButtonIndex === index
                  ? Color.SAMSUNG_BLUE
                  : Color.GRAY200
              }
              color={selectedButtonIndex === index ? Color.WHITE : Color.BLACK}
              onClick={() => handleButtonClick(index)}
            >
              {title}
            </SelectButton>
          ))}
        </ButtonBox>
        <Button
          width={8}
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
          .csv로 내보내기
        </Button>
      </SelectBox>
      <TitleBox>
        {titleList.map((data, index) => (
          <Title
            key={index}
            width={index === 0 ? '5%' : undefined}
            justifyContent={index === 0 ? 'center' : undefined}
          >
            {data}
          </Title>
        ))}
      </TitleBox>
      <ContentBox>
        {equipment.map((data, index) => (
          <TitleBox>
            <Title
              width="5%"
              justifyContent="center"
              onClick={() => handleSelectToggle(index)}
            >
              {data.selected ? (
                <img src={Check} alt="" />
              ) : (
                <img src={NonCheck} alt="" />
              )}
            </Title>
            <Title>{data.equipmentNumber}</Title>
            <Title>{data.equipmentName}</Title>
            <Title>{data.manager}</Title>
            <Title>{data.lotoPurpose}</Title>
            <Title>{data.accidentOccurred}</Title>
            <Title>{data.accidentType}</Title>
            <Title>{data.casualties}</Title>
            <Title>{data.startTime}</Title>
            <Title>{data.endTime}</Title>
          </TitleBox>
        ))}
      </ContentBox>
    </MainBox>
  );
};

export default Report;
