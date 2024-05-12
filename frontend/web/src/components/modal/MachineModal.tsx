import styled from 'styled-components';
import { Modal } from './Modal.tsx';
import { Button } from '../button/Button.tsx';
import * as Color from '@/config/color/Color.ts';
import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { MachineList } from '@/apis/Machine.ts';
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
const Chapter = styled.div`
  border-bottom: 1px solid black;
`;

const ContentBox = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  overflow-y: auto;
  box-sizing: border-box;
  margin-top: 1.5rem;
`;
const Line = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: row;
`;
const Content = styled.div`
  width: 20%;
  height: auto;
  display: flex;
  text-overflow: ellipsis;
  justify-content: center;
  align-items: center;
`;
const ChanpterBox = styled.div`
  justify-content: center;
  display: flex;
  width: 100%;
  height: auto;
  align-items: center;
  margin-top: 1rem;
`;
const TitleBox = styled.div`
  width: 20%;
  height: auto;
  display: flex;
  justify-content: center;
  align-items: center;
`;
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
  const [data, setData] = useState<EquipmentType[]>([]);
  useEffect(() => {
    const fetchEquipment = async () => {
      const equipmentData = await MachineList(option);
      setData(
        equipmentData.map((item: EquipmentType) => ({
          ...item,
          introduction_date: item.introduction_date.split('T')[0], // 'T' 전까지만 파싱
        })),
      );
    };
    fetchEquipment();
  }, []);

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
      <ChanpterBox>
        <TitleBox>
          <Chapter>작업장</Chapter>
        </TitleBox>
        <TitleBox>
          <Chapter>장비 명</Chapter>
        </TitleBox>
        <TitleBox>
          <Chapter>장비번호</Chapter>
        </TitleBox>
        <TitleBox>
          <Chapter>도입 일자</Chapter>
        </TitleBox>
        <TitleBox>
          <Chapter>담당자</Chapter>
        </TitleBox>
      </ChanpterBox>
      <ContentBox>
        {data.map((item) => (
          <Line>
            <Content>{item.facility_name}</Content>
            <Content>{item.machine_name}</Content>
            <Content>{item.machine_code}</Content>
            <Content>{item.introduction_date}</Content>
            <Content>{item.main_manager_name}</Content>
          </Line>
        ))}
      </ContentBox>
    </Modal>
  );
};

export default EquipmentModal;
