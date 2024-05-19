import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import Card from '@/components/card/Card.tsx';
import WorkPlace from '/assets/images/workplace.png';
import * as Typo from '@/components/typography/Typography.tsx';
import Equipment from '/assets/images/equipment.png';
import People from '/assets/images/people.png';
import Dropdown from '@/components/dropdown/DropDown.tsx';
import EquipmentModal from '@/components/modal/MachineModal.tsx';
import React, { useEffect, useState } from 'react';
import { Facilities } from '@/apis/Facilities.ts';
import { MachineList } from '@/apis/Machine.ts';
import { RegistratedEmployee } from '@/apis/Employee.ts';
import EmployeeModal from '@/components/modal/EmployeeModal.tsx';
import { useQuery } from '@tanstack/react-query';

interface OptionType {
  value: number;
  label: string;
}
interface FacilityType {
  id: string;
  name: string;
}

interface EmployeeType {
  id: number;
  employee_num: string;
  name: string;
  title: string;
  team: string;
  role: string;
}

interface MachineType {
  machine_id: string;
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

const CompanyInformation = () => {
  const [equipModal, setEquipModal] = useState<boolean>(false);
  const [employeeModal, setEmployeeModal] = useState<boolean>(false);
  const [options, setOptions] = useState<OptionType[]>([]);
  const [selectedOption, setSelectedOption] = useState<OptionType | null>(null);
  const [facilities, setFacilities] = useState<number>(0);
  const [employees, setEmployees] = useState<number>(0);
  const handleEquipmentClick = () => {
    if (selectedOption) {
      setEquipModal(true);
    } else {
      alert('공장을 선택하세요.');
    }
  };
  const handleEmployeeClick = () => {
    setEmployeeModal(true);
  };
  const handleCloseModal = (
    e: React.MouseEvent<HTMLDivElement, MouseEvent>,
  ) => {
    setEquipModal(false);
    setEmployeeModal(false);
    e.stopPropagation();
  };
  const handleModalClick = (
    e: React.MouseEvent<HTMLDivElement, MouseEvent>,
  ) => {
    e.stopPropagation();
  };
  // 드롭다운에 넣을 데이터 불러우기
  const { data: dropdownData } = useQuery({
    queryKey: ['dropdown'],
    queryFn: () => Facilities(),
  });

  // 드롭다운 옵션에서 키값을 value와 label로 받아줘야해서 수정
  useEffect(() => {
    if (dropdownData) {
      const newOptions = dropdownData?.map((facility: FacilityType) => ({
        value: facility.id,
        label: facility.name,
      }));
      setOptions(newOptions);
    }
  }, [dropdownData]);

  // 기계 리스트 불러오기
  const { data: machineData } = useQuery<MachineType[]>({
    queryKey: ['machineList', selectedOption],
    queryFn: () => {
      if (selectedOption) {
        return MachineList(selectedOption.value);
      } else {
        return [];
      }
    },
  });

  // 데이터 길이 추출
  useEffect(() => {
    if (machineData) {
      setFacilities(machineData.length);
    }
  }, [selectedOption, machineData]);

  // 등록 임직원 데이터 불러오기
  const { data: employeeData } = useQuery<EmployeeType[]>({
    queryKey: ['employee'],
    queryFn: () => RegistratedEmployee(),
  });

  useEffect(() => {
    if (employeeData) {
      setEmployees(employeeData.length);
    }
  }, [employeeData]);
  const handleOptionChange = (option: OptionType): void => {
    setSelectedOption(option);
  };
  useEffect(() => {
    if (options.length > 0) {
      setSelectedOption(options[0]);
    }
  }, [options]);
  return (
    <Background>
      <Box>
        {equipModal && selectedOption && (
          <Overlay onClick={handleCloseModal}>
            <EquipmentModal
              onClick={handleModalClick}
              option={selectedOption.value}
            />
          </Overlay>
        )}
        {employeeModal && (
          <Overlay onClick={handleCloseModal}>
            <EmployeeModal onClick={handleModalClick} />
          </Overlay>
        )}
        <Card
          width={22}
          height={'100%'}
          justifyContent={'space-between'}
          flexDirection={'column'}
          alignItems="center"
        >
          <Typo.H3>
            <div style={{ marginTop: '1.3rem' }}>등록 작업장 수</div>
          </Typo.H3>
          <ImgBox src={WorkPlace} />
          <Typo.H0>{options.length}</Typo.H0>
        </Card>
        <Card
          width={22}
          height={'100%'}
          justifyContent={'space-between'}
          flexDirection={'column'}
          alignItems="center"
          onClick={handleEquipmentClick}
          $hoverBackgroundColor={Color.GRAY300}
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
          <ImgBox src={Equipment} />
          <Typo.H0>{facilities}</Typo.H0>
        </Card>
        <Card
          width={22}
          height={'100%'}
          justifyContent={'space-between'}
          flexDirection={'column'}
          alignItems="center"
          onClick={handleEmployeeClick}
          $hoverBackgroundColor={Color.GRAY300}
        >
          <Typo.H3>
            <div style={{ marginTop: '1.3rem' }}>등록 임직원현황</div>
          </Typo.H3>
          <ImgBox src={People} />
          <Typo.H0>{employees}</Typo.H0>
        </Card>
      </Box>
    </Background>
  );
};

export default CompanyInformation;
