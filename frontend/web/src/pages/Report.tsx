import * as Color from '@/config/color/Color.ts';
import { useEffect, useState } from 'react';
import styled from 'styled-components';
import Check from '/assets/icons/Check.svg';
import NonCheck from '/assets/icons/NonCheck.svg';
import ReportCheckModal from '@/components/modal/ReportCheckModal.tsx';
import { totalReport } from '@/apis/Report.ts';
import CsvDownloadButton from 'react-json-to-csv';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import dayjs, { Dayjs } from 'dayjs';
import 'dayjs/locale/ko';
dayjs.locale('ko');
// interface ButtonProps {
//   backgroundColor: string;
//   color: string;
// }
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
  workerTeam: string;
  workerTitle: string;
  facilityName: string;
}
interface CsvEquipmentData {
  '보고서 ID': number;
  '장비 번호': string;
  '장비 명': string;
  담당자: string;
  'LOTO 목적': string;
  '사고 여부': string;
  '사고 유형': string;
  '인명피해(명)': string;
  '시작 일시': string;
  '종료 일시': string;
  작업팀: string;
  직급: string;
  작업장: string;
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
const DaySelectBox = styled.div`
  width: auto;
  height: auto;
  display: flex;
  gap: 0.4rem;
  flex-direction: row;
  align-items: center;
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
// const SelectButton = styled.div<ButtonProps>`
//   width: 3.4rem;
//   height: 2rem;
//   background-color: ${(props) => props.backgroundColor};
//   border: 1px solid ${Color.GRAY100};
//   border-radius: 1.25rem;
//   font-size: 1rem;
//   font-weight: bold;
//   color: ${(props) => props.color};
//   justify-content: center;
//   align-items: center;
//   display: flex;
//   cursor: pointer;
// `;
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
  // const [selectedButtonIndex, setSelectedButtonIndex] = useState<number>(0);
  const [reportModal, setReportModal] = useState<boolean>(false);
  const [reportData, setReportData] = useState<EquipmentData[]>([]);
  const [detailReport, setDetailReport] = useState<number>(1);
  const [csvData, setCsvData] = useState<EquipmentData[]>([]);
  const [startDate, setStartDate] = useState<Dayjs | null>(null);

  const [endDate, setEndDate] = useState<Dayjs | null>(null);
  const [formatStartDate, setFormatStartDate] = useState<string>('');
  const [formatEndDate, setFormatEndDate] = useState<string>('');
  // const handleButtonClick = (index: number) => {
  //   setSelectedButtonIndex(index); // 클릭된 버튼의 인덱스로 상태 업데이트
  //   console.log(index);
  // };
  // const handleSubmit = () => {
  //   console.log('클릭');
  // };
  const transformDataForCsv = (data: EquipmentData[]): CsvEquipmentData[] => {
    return data.map((item) => ({
      '보고서 ID': item.reportId,
      '장비 번호': item.machineNumber,
      '장비 명': item.machineName,
      담당자: item.workerName,
      'LOTO 목적': item.tasktype,
      '사고 여부': item.accident ? '있음' : '없음',
      '사고 유형': item.accidentType || '없음',
      '인명피해(명)': item.victimsNum?.toString() || '-',
      '시작 일시': item.taskStartDateTime,
      '종료 일시': item.taskEndDateTime,
      작업팀: item.workerTeam,
      직급: item.workerTitle,
      작업장: item.facilityName,
    }));
  };

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
    data: EquipmentData,
  ) => {
    const updatedEquipment = reportData.map((item, idx) => {
      if (idx === index) return { ...item, selected: !item.selected };
      return item;
    });
    setReportData(updatedEquipment);
    e.stopPropagation();
    if (updatedEquipment[index].selected) {
      setCsvData((prevData) => [...prevData, data]);
    } else {
      setCsvData((prevData) =>
        prevData.filter((d) => d.reportId !== data.reportId),
      );
    }
  };

  const handleReport = (index: number) => {
    setReportModal(true);
    setDetailReport(index);
  };
  const formatDate = (dateString: string) => {
    return dateString.replace('T', ' ').slice(0, 16); // 'T'를 공백으로 대체하고 초 이후를 잘라냄
  };
  const [transformedCsvData, setTransformedCsvData] = useState<
    CsvEquipmentData[]
  >([]);

  // csvData가 변경될 때마다 자동으로 데이터를 변환
  useEffect(() => {
    setTransformedCsvData(transformDataForCsv(csvData));
  }, [csvData]);
  useEffect(() => {
    const report = async () => {
      try {
        const data = await totalReport();
        const formattedData = data.map((item: EquipmentData) => ({
          ...item,
          taskStartDateTime:
            item.taskStartDateTime === null
              ? '-'
              : formatDate(item.taskStartDateTime),
          taskEndDateTime:
            item.taskEndDateTime === null
              ? '-'
              : formatDate(item.taskEndDateTime),
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
  const customFormats = {
    normalDate: 'YYYY년 MM월 DD일',
  };
  const handleStartDateChange = (date: Dayjs) => {
    const formattedDate = dayjs(date).format('YYYY-MM-DD');
    setFormatStartDate(formattedDate);
  };

  const handleEndDateChange = (date: Dayjs) => {
    const formattedDate = dayjs(date).format('YYYY-MM-DD');
    setFormatEndDate(formattedDate);
  };
  useEffect(() => {
    if (startDate && endDate) {
      handleStartDateChange(startDate);
      handleEndDateChange(endDate);
    }
  }, [startDate, endDate]);

  console.log('시작시간', formatStartDate);
  console.log('종료시간', formatEndDate);
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
        <DaySelectBox>
          <LocalizationProvider
            dateAdapter={AdapterDayjs}
            adapterLocale="ko"
            dateFormats={customFormats}
            localeText={{ datePickerToolbarTitle: '선택' }}
          >
            <DatePicker
              onChange={(date: Dayjs | null) => {
                setStartDate(date);
              }}
              format="YYYY-MM-DD"
              label="시작 일"
              orientation="portrait"
              disableFuture
            />
            {/* </LocalizationProvider> */}

            <div>~</div>
            {/* <LocalizationProvider dateAdapter={AdapterDayjs}> */}
            <DatePicker
              // value={endDate}
              onChange={(date: Dayjs | null) => {
                setEndDate(date);
              }}
              format="YYYY-MM-DD"
              label="종료 일"
              orientation="portrait"
              disableFuture
            />
          </LocalizationProvider>
        </DaySelectBox>
        {/* <ButtonBox>
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
        </ButtonBox> */}
        <CsvDownloadButton data={transformedCsvData} delimiter="," />
      </SelectBox>
      <TitleBox>
        {titleList.map((data, index) => (
          <Title
            key={index}
            width={index === 0 ? '5%' : undefined}
            justifyContent={
              index === 0 || index === 5 || index === 3 || index === 4
                ? 'center'
                : undefined
            }
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
              onClick={(event) => handleSelectToggle(index, event, data)}
            >
              {data.selected ? (
                <img src={Check} alt="" style={{ cursor: 'pointer' }} />
              ) : (
                <img src={NonCheck} alt="" style={{ cursor: 'pointer' }} />
              )}
            </Title>
            <Title>{data.machineNumber}</Title>
            <Title>{data.machineName}</Title>
            <Title justifyContent="center">{data.workerName}</Title>
            <Title justifyContent="center">{data.tasktype}</Title>
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
