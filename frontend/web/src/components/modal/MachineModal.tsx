import styled from 'styled-components';
import { Modal } from './Modal.tsx';
import { Button } from '../button/Button.tsx';
import * as Color from '@/config/color/Color.ts';
import { useNavigate } from 'react-router-dom';
import React, { useEffect, useState } from 'react';
import { MachineList } from '@/apis/Machine.ts';
import { Column } from 'react-table';
import { useTable } from 'react-table';
interface EquipmentModalProps {
  onClick?: (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => void;
  option: number;
}
interface EquipmentType {
  id: string;
  facility_id: string;
  facility_name: string;
  machine_name: string;
  machine_code: string;
  introduction_date: string;
  main_manager_id: string;
  main_manager_name: string;
  sub_manager_id: string;
  sub_manager_name: string;
}

const ButtonBox = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  justify-content: flex-end;
`;
const EquipmentModal = ({ onClick, option }: EquipmentModalProps) => {
  const navigate = useNavigate();
  const handleButtonClick = () => {
    navigate('/equipment');
  };
  const [machineData, setMachineData] = useState<EquipmentType[]>([]);
  useEffect(() => {
    const fetchEquipment = async () => {
      const equipmentData = await MachineList(option);
      setMachineData(
        equipmentData.map((item: EquipmentType) => ({
          ...item,
          introduction_date: item.introduction_date.split('T')[0], // 'T' 전까지만 파싱
        })),
      );
    };
    fetchEquipment();
  }, []);
  // 표 만들기

  const columns: Column<EquipmentType>[] = React.useMemo<
    Column<EquipmentType>[]
  >(
    () => [
      {
        Header: '작업장',
        accessor: 'facility_name',
      },
      {
        Header: '장비 명',
        accessor: 'machine_name',
      },
      {
        Header: '장비번호',
        accessor: 'machine_code',
      },
      {
        Header: '도입 일자',
        accessor: 'introduction_date',
      },
      {
        Header: '담당자',
        accessor: 'main_manager_name',
      },
    ],
    [],
  );
  const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow } =
    useTable({ columns, data: machineData });
  return (
    <Modal onClick={onClick}>
      <ButtonBox>
        <Button
          onClick={handleButtonClick}
          width={5}
          height={2}
          $backgroundColor={Color.WHITE}
          $borderColor={Color.GRAY100}
          $borderRadius={2.5}
          $hoverBackgroundColor={Color.SAFETY_YELLOW}
          $hoverBorderColor={Color.SAFETY_YELLOW}
          fontSize={1.25}
        >
          등록
        </Button>
      </ButtonBox>

      <table
        {...getTableProps}
        style={{ width: '100%', borderCollapse: 'collapse', marginTop: '1rem' }}
      >
        <thead>
          {headerGroups.map((headerGroup) => (
            <tr {...headerGroup.getHeaderGroupProps()}>
              {headerGroup.headers.map((column) => (
                <th
                  {...column.getHeaderProps()}
                  style={{
                    borderBottom: '2px solid black',
                    paddingBottom: '1rem',
                    boxSizing: 'border-box',
                    color: Color.BLACK,
                    fontWeight: 'bold',
                    fontFamily: 'NYJGothicB',
                    fontSize: '1.5rem',
                  }}
                >
                  {column.render('Header')}
                </th>
              ))}
            </tr>
          ))}
        </thead>
        <tbody {...getTableBodyProps()}>
          {rows.map((row) => {
            prepareRow(row);
            return (
              <tr {...row.getRowProps()}>
                {row.cells.map((cell) => (
                  <td
                    {...cell.getCellProps()}
                    style={{
                      padding: '1rem',
                      textAlign: 'center',
                    }}
                  >
                    {cell.render('Cell')}
                  </td>
                ))}
              </tr>
            );
          })}
        </tbody>
      </table>
      {/* <ContentBox>
        {machineData.map((item) => (
          <Line>
            <Content>{item.facility_name}</Content>
            <Content>{item.machine_name}</Content>
            <Content>{item.machine_code}</Content>
            <Content>{item.introduction_date}</Content>
            <Content>{item.main_manager_name}</Content>
          </Line>
        ))}
      </ContentBox> */}
    </Modal>
  );
};

export default EquipmentModal;
