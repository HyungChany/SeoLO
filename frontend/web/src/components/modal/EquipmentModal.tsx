import styled from 'styled-components';
import { Modal } from './Modal.tsx';

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
  padding: 0 1.5rem;
  box-sizing: border-box;
  overflow-y: auto;
`;

const Content = styled.div`
  width: auto;
  height: auto;
  font-size: 1.5rem;
  font-weight: 700;
`;
const EquipmentModal = () => {
  //   const List = ['이름', '사번', '소속부서', '직급', 'LOTO 시행 횟수'];
  return (
    <Modal>
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
          <Chapter>LOTO 시행 횟수</Chapter>
          <Content>4</Content>
        </ContentBox>
      </MainBox>
    </Modal>
  );
};

export default EquipmentModal;
