import styled from 'styled-components';
import { Modal } from './Modal.tsx';
import { Button } from '../button/Button.tsx';
import * as Color from '@/config/color/Color.ts';
interface EquipmentModalProps {
  onClick?: (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => void;
}

const Chapter = styled.div`
  border-bottom: 1px solid black;
`;

const ContentBox = styled.div`
  height: 100%;
  width: auto;
  display: flex;
  gap: 1.5rem;
  justify-content: flex-start;
  flex-direction: column;
  box-sizing: border-box;
  align-items: center;
`;
const MainBox = styled.div`
  justify-content: space-between;
  display: flex;
  width: 100%;
  height: 100%;
  padding: 1.5rem;
  box-sizing: border-box;
  overflow-y: auto;
`;

const Content = styled.div`
  width: auto;
  height: auto;
  font-size: 1.5rem;
  font-weight: 700;
`;

const ButtonBox = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  justify-content: flex-end;
`;
const EquipmentModal = ({ onClick }: EquipmentModalProps) => {
  const handleButtonClick = () => {};
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
      <MainBox>
        <ContentBox>
          <Chapter>이름</Chapter>
          <Content>오정민</Content>
        </ContentBox>
        <ContentBox>
          <Chapter>사번</Chapter>
          <Content>1014826</Content>
        </ContentBox>
        <ContentBox>
          <Chapter>소속부서</Chapter>
          <Content>해외사업부</Content>
        </ContentBox>
        <ContentBox>
          <Chapter>직급</Chapter>
          <Content>대리</Content>
        </ContentBox>
        <ContentBox>
          <Chapter>권한</Chapter>
          <Content>4</Content>
        </ContentBox>
      </MainBox>
    </Modal>
  );
};

export default EquipmentModal;
