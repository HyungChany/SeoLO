import { Button } from '@/components/button/Button.tsx';
import * as Color from '@/config/color/Color.ts';
import { useEffect, useState } from 'react';
import styled from 'styled-components';
import Check from '/assets/icons/Check.svg';
import NonCheck from '/assets/icons/NonCheck.svg';
import ReportCheckModal from '@/components/modal/ReportCheckModal.tsx';
import { totalReport } from '@/apis/Report.ts';
interface ButtonProps {
  backgroundColor: string;
  color: string;
}
interface EquipmentData {
  reportId: number;
  machineNumber: string;
  machineName: string;
  workerName: string;
  tasktype: string;
  accidentType: string;
  victimsNum: number;
  taskStartDateTime: string;
  taskEndDateTime: string;
  accident: boolean;
  selected: boolean;
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
  border-bottom: 1px solid ${Color.GRAY200};
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
const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5); // 반투명 검은색 배경
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 10;
`;
const Report = () => {
  const [selectedButtonIndex, setSelectedButtonIndex] = useState<number>(0);
  const [reportModal, setReportModal] = useState<boolean>(false);
  const [reportData, setReportData] = useState<EquipmentData[]>([]);
  const [detailReport, setDetailReport] = useState<number>(1);
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
  const handleCloseModal = () => {
    setReportModal(!reportModal);
  };
  const handleSelectToggle = (
    index: number,
    e: React.MouseEvent<HTMLDivElement, MouseEvent>,
  ) => {
    const updatedEquipment = reportData.map((item, idx) => {
      if (idx === index) return { ...item, selected: !item.selected };
      return item;
    });
    setReportData(updatedEquipment);
    e.stopPropagation();
  };
  const handleReport = (index: number) => {
    setReportModal(true);
    setDetailReport(index);
    console.log('인덱스', index);
  };
  const formatDate = (dateString: string) => {
    return dateString.replace('T', ' ').slice(0, 16); // 'T'를 공백으로 대체하고 초 이후를 잘라냄
  };

  useEffect(() => {
    const report = async () => {
      try {
        const data = await totalReport();
        const formattedData = data.map((item: EquipmentData) => ({
          ...item,
          taskStartDateTime: formatDate(item.taskStartDateTime),
          taskEndDateTime: formatDate(item.taskEndDateTime),
          accidentType: item.accidentType === null ? '-' : item.accidentType,
          victimsNum: item.victimsNum === null ? '-' : item.victimsNum,
          accident: item.accident === false ? 'N' : 'T',
        }));
        setReportData(formattedData);
      } catch (e) {
        console.error(e);
      }
    };
    report();
  }, []);

  return (
    <MainBox>
      {reportModal && (
        <Overlay
          onClick={(e) => {
            e.stopPropagation();
            handleCloseModal();
          }}
        >
          <ReportCheckModal
            onClose={handleCloseModal}
            contentIndex={detailReport}
          ></ReportCheckModal>
        </Overlay>
      )}
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
            justifyContent={index === 0 || index === 5 ? 'center' : undefined}
          >
            {data}
          </Title>
        ))}
      </TitleBox>
      <ContentBox>
        {reportData.map((data, index) => (
          <TitleBox onClick={() => handleReport(data.reportId)}>
            <Title
              width="5%"
              justifyContent="center"
              onClick={(event) => handleSelectToggle(index, event)}
            >
              {data.selected ? (
                <img src={Check} alt="" style={{ cursor: 'pointer' }} />
              ) : (
                <img src={NonCheck} alt="" style={{ cursor: 'pointer' }} />
              )}
            </Title>
            <Title>{data.machineNumber}</Title>
            <Title>{data.machineName}</Title>
            <Title>{data.workerName}</Title>
            <Title>{data.tasktype}</Title>
            <Title justifyContent="center">{data.accident}</Title>
            <Title>{data.accidentType}</Title>
            <Title>{data.victimsNum}</Title>
            <Title>{data.taskStartDateTime}</Title>
            <Title>{data.taskEndDateTime}</Title>
          </TitleBox>
        ))}
      </ContentBox>
    </MainBox>
  );
};

export default Report;
