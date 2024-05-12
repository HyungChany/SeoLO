import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import Card from '@/components/card/Card.tsx';
import WorkPlace from '/assets/images/workplace.png';
import * as Typo from '@/components/typography/Typography.tsx';
import Equipment from '/assets/images/equipment.png';
import People from '/assets/images/people.png';
import Dropdown from '@/components/dropdown/DropDown.tsx';
import EquipmentModal from '@/components/modal/MachineModal.tsx';
import Employee from '@/components/modal/EmployeeModal.tsx';
import React, { useEffect, useState } from 'react';
import { Facilities } from '@/apis/Facilities.ts';
import { MachineList } from '@/apis/Machine.ts';
import { RegistratedEmployee } from '@/apis/Employee.ts';

interface OptionType {
  value: number;
  label: string;
}
interface FacilityType {
  id: string;
  name: string;
}
const Background = styled.div`
  width: 100%;
  height: 100%;
  background-color: ${Color.GRAY200};
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-left: 6rem;
  padding-right: 6rem;
  box-sizing: border-box;
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
    const fetchEmployee = async () => {
      const data = await RegistratedEmployee();
      setEmployees(data.length);
    };
    fetchEmployee();
  }, []);
  const handleOptionChange = (option: OptionType): void => {
    setSelectedOption(option);
  };
  return (
    <Background>
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
          <Employee onClick={handleModalClick} />
        </Overlay>
      )}
      <Card
        width={22}
        height={30}
        justifyContent={'space-between'}
        flexDirection={'column'}
        alignItems="center"
      >
        <Typo.H3 color={Color.BLACK}>등록 작업장 수</Typo.H3>
        <ImgBox src={WorkPlace} />
        <Typo.H0 color={Color.BLACK}>{options.length}</Typo.H0>
      </Card>
      <Card
        width={22}
        height={30}
        justifyContent={'space-between'}
        flexDirection={'column'}
        alignItems="center"
        onClick={handleEquipmentClick}
      >
        <Typo.H3 color={Color.BLACK}>현재 작업장의 장비 현황</Typo.H3>
        <Dropdown
          options={options}
          selectedOption={selectedOption}
          onOptionChange={handleOptionChange}
          placeholder="공장을 선택하세요"
        />
        <ImgBox src={Equipment} />
        <Typo.H0 color={Color.BLACK}>{facilities}</Typo.H0>
      </Card>
      <Card
        width={22}
        height={30}
        justifyContent={'space-between'}
        flexDirection={'column'}
        alignItems="center"
        onClick={handleEmployeeClick}
      >
        <Typo.H3 color={Color.BLACK}>등록 임직원현황</Typo.H3>
        <ImgBox src={People} />
        <Typo.H0 color={Color.BLACK}>{employees}</Typo.H0>
      </Card>
    </Background>
  );
};

export default CompanyInformation;
