import * as Color from '@/config/color/Color.ts';
import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import Check from '/assets/icons/Check.svg';
import NonCheck from '/assets/icons/NonCheck.svg';
import Excel from '/assets/icons/Excel.svg';
import ReportCheckModal from '@/components/modal/ReportCheckModal.tsx';
import { RangeReport, totalReport } from '@/apis/Report.ts';
import CsvDownloadButton from 'react-json-to-csv';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import dayjs, { Dayjs } from 'dayjs';
import 'dayjs/locale/ko';
import { Button } from '@/components/button/Button.tsx';
import { Column, Row } from 'react-table';
import { useTable } from 'react-table';
import { useQuery } from '@tanstack/react-query';

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

const SelectBox = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
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
  overflow-y: auto;
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

const CsvButton = styled(CsvDownloadButton)`
  background-color: ${Color.GREEN900};
  height: 2.5rem;
  color: white;
  padding: 0 1rem;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: bold;
  box-shadow: 0px 4px 8px 0px rgba(0, 0, 0, 0.3);
  display: flex;
  align-items: center;
  &:hover {
    background-color: #19633a;
  }
`;

const TableRow = styled.tr`
  &:hover {
    background-color: ${Color.GRAY100}; // 원하는 호버 배경 색
  }
`;

const StickyTh = styled.th`
  height: 3rem;
  position: sticky;
  top: -2rem;
  background-color: white;
  z-index: 10;
  font-weight: bold;
  padding: 0.5rem 0;
  border-bottom: 2px solid black;
`;

const Report = () => {
  const [reportModal, setReportModal] = useState<boolean>(false);
  const [reportData, setReportData] = useState<EquipmentData[]>([]);
  const [detailReport, setDetailReport] = useState<number>(1);
  const [csvData, setCsvData] = useState<EquipmentData[]>([]);
  const [startDate, setStartDate] = useState<Dayjs | null>(null);

  const [endDate, setEndDate] = useState<Dayjs | null>(null);
  const [formatStartDate, setFormatStartDate] = useState<string>('');
  const [formatEndDate, setFormatEndDate] = useState<string>('');

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
  // 표 구현
  const [tableData, setTableData] = useState<EquipmentData[]>([]);
  useEffect(() => {
    setTableData(reportData);
  }, [reportData]);

  const handleSelectAllToggle = () => {
    const allSelected = reportData.every((item) => item.selected);
    const updatedEquipment = reportData.map((item) => ({
      ...item,
      selected: !allSelected,
    }));
    setReportData(updatedEquipment);
    if (allSelected) {
      setCsvData([]);
    } else {
      setCsvData(updatedEquipment);
    }
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
  const columns: Column<EquipmentData>[] = React.useMemo(
    () => [
      {
        Header: (
          <div onClick={handleSelectAllToggle} style={{ cursor: 'pointer' }}>
            선택
          </div>
        ),
        accessor: 'selected',
        Cell: ({ row }: { row: Row<EquipmentData> }) => (
          <img
            src={row.original.selected ? Check : NonCheck}
            alt={row.original.selected ? 'Checked' : 'Not Checked'}
            style={{ cursor: 'pointer' }}
            onClick={(event) =>
              handleSelectToggle(row.index, event, row.original)
            }
          />
        ),
      },
      { Header: '장비 번호', accessor: 'machineNumber' },
      { Header: '장비 명', accessor: 'machineName' },
      { Header: '담당자', accessor: 'workerName' },
      { Header: 'LOTO 목적', accessor: 'tasktype' },
      { Header: '사고 여부', accessor: 'accident' },
      { Header: '사고 유형', accessor: 'accidentType' },
      { Header: '인명피해(명)', accessor: 'victimsNum' },
      { Header: '시작 일시', accessor: 'taskStartDateTime' },
      { Header: '종료 일시', accessor: 'taskEndDateTime' },
    ],
    [tableData],
  );
  const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow } =
    useTable({ columns, data: tableData });

  // react query로 변경

  const formatDate = (dateString: string) => {
    return dateString.replace('T', ' ').slice(0, 16); // 'T'를 공백으로 대체하고 초 이후를 잘라냄
  };

  const [transformedCsvData, setTransformedCsvData] = useState<
    CsvEquipmentData[]
  >([]);

  const { data: totalData } = useQuery({
    queryKey: ['report'],
    queryFn: () => totalReport(),
  });

  useEffect(() => {
    if (totalData) {
      const formattedData = totalData.map((item: EquipmentData) => ({
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
    }
  }, [totalData]);
  const handleCloseModal = () => {
    setReportModal(!reportModal);
  };

  const handleReport = (index: number) => {
    setReportModal(true);
    setDetailReport(index);
  };

  // csvData가 변경될 때마다 자동으로 데이터를 변환
  useEffect(() => {
    setTransformedCsvData(transformDataForCsv(csvData));
  }, [csvData]);

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
  const handleSearch = async (start: string, end: string) => {
    const data = await RangeReport(start, end);
    const formattedData = data.map((item: EquipmentData) => ({
      ...item,
      taskStartDateTime:
        item.taskStartDateTime === null
          ? '-'
          : formatDate(item.taskStartDateTime),
      taskEndDateTime:
        item.taskEndDateTime === null ? '-' : formatDate(item.taskEndDateTime),
      accidentType: item.accidentType === null ? '-' : item.accidentType,
      victimsNum: item.victimsNum === null ? '-' : item.victimsNum,
      accident: item.accident === false ? 'N' : 'T',
    }));
    setReportData(formattedData);
  };

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

            <div style={{ fontSize: '2rem', marginBottom: '0.6rem' }}>-</div>

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
          <div style={{ marginLeft: '1rem' }}>
            <Button
              onClick={() => handleSearch(formatStartDate, formatEndDate)}
              width={4}
              height={2.5}
              fontWeight={900}
              fontSize={1.2}
              $backgroundColor={Color.GRAY200}
              $borderColor={Color.GRAY200}
              $borderRadius={0.5}
              $hoverBackgroundColor={Color.GRAY300}
              $hoverBorderColor={Color.GRAY300}
            >
              검색
            </Button>
          </div>
        </DaySelectBox>
        <CsvButton data={transformedCsvData} delimiter=",">
          <img src={Excel} style={{ marginRight: '0.5rem', width: '1.5rem' }} />
          .CSV 다운로드
        </CsvButton>
      </SelectBox>
      <table
        {...getTableProps()}
        style={{ width: '100%', borderCollapse: 'collapse' }}
      >
        <thead>
          {headerGroups.map((headerGroup) => (
            <tr {...headerGroup.getHeaderGroupProps()} key={headerGroup.id}>
              {headerGroup.headers.map((column) => (
                <StickyTh {...column.getHeaderProps()} key={column.id}>
                  {column.render('Header')}
                </StickyTh>
              ))}
            </tr>
          ))}
        </thead>
        <tbody {...getTableBodyProps()}>
          {rows.map((row) => {
            prepareRow(row);
            return (
              <TableRow
                {...row.getRowProps()}
                key={row.id}
                onClick={() => handleReport(row.original.reportId)}
              >
                {row.cells.map((cell) => (
                  <td
                    {...cell.getCellProps()}
                    key={cell.column.id}
                    style={{
                      padding: '1rem',
                      textAlign: 'center',
                      cursor: 'pointer',
                    }}
                  >
                    {cell.render('Cell')}
                  </td>
                ))}
              </TableRow>
            );
          })}
        </tbody>
      </table>
    </MainBox>
  );
};

export default Report;
