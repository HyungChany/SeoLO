import styled from 'styled-components';
import { Modal } from './Modal.tsx';
import { Button } from '../button/Button.tsx';
import * as Color from '@/config/color/Color.ts';
import React, { useEffect, useState } from 'react';
import { RegistratedEmployee } from '@/apis/Employee.ts';
import { Column } from 'react-table';
import { useTable } from 'react-table';
import { useNavigate } from 'react-router-dom';
interface EmployeeModalProps {
  onClick?: (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => void;
}

interface EmployeeType {
  id: number;
  name: string;
  empolyee_num: string;
  title: string;
  team: string;
  role: string;
}

const ButtonBox = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  justify-content: flex-end;
`;
const EmployeeModal = ({ onClick }: EmployeeModalProps) => {
  const [employeeData, setEmployeeData] = useState<EmployeeType[]>([]);
  const navigate = useNavigate();
  useEffect(() => {
    const fetchData = async () => {
      const data = await RegistratedEmployee();
      setEmployeeData(data);
    };
    fetchData();
  });

  const columns: Column<EmployeeType>[] = React.useMemo<Column<EmployeeType>[]>(
    () => [
      { Header: '이름', accessor: 'name' },
      { Header: '사번', accessor: 'empolyee_num' },
      { Header: '소속 부서', accessor: 'team' },
      { Header: '직급', accessor: 'title' },
      { Header: '역할', accessor: 'role' },
    ],
    [],
  );

  const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow } =
    useTable({ columns, data: employeeData });

  const handleButtonClick = () => {
    navigate('/employee');
  };
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
        style={{
          width: '100%',
          borderCollapse: 'collapse',
          marginTop: '1rem',
        }}
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
                      fontWeight: 'bold',
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
    </Modal>
  );
};

export default EmployeeModal;
