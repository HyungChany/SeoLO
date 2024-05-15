import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import { Button } from '@/components/button/Button.tsx';
import { useState } from 'react';
import CheckListModal from '@/components/modal/CheckListModal.tsx';
import DeleteCheckListModal from '@/components/modal/DeleteCheckListModal.tsx';
import { getBasicCheckList, getCheckList } from '@/apis/CheckList.ts';
import CreateCheckListModal from '@/components/modal/CreateCheckListModal.tsx';
import { useQuery } from '@tanstack/react-query';
interface CheckListType {
  id: number;
  context: string;
}
const BackGround = styled.div`
  width: 100%;
  min-height: 100%;
  height: auto;
  background-color: ${Color.GRAY200};
  justify-content: center;
  display: flex;
  overflow-y: auto;
  -ms-overflow-style: none;
  scrollbar-width: none;
  &::-webkit-scrollbar {
    display: none;
  }
`;

const Box = styled.div`
  width: 76.6875rem;
  height: 100%;
  display: flex;
  flex-direction: column;
  gap: 3rem;
  padding: 3rem;
  box-sizing: border-box;
`;

const ListBox = styled.div`
  width: 100%;
  height: 6.25rem;
  border-radius: 1.25rem;
  background-color: white;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 1.5rem;
  box-sizing: border-box;
  justify-content: space-between;
  cursor: pointer;
`;
const PlusListBox = styled.div`
  width: 100%;
  height: 6.25rem;
  border-radius: 1.25rem;
  background-color: white;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding: 1.5rem;
  box-sizing: border-box;
  cursor: pointer;
`;

const ContentBox = styled.div`
  width: 92%;
  height: 100%;
  flex-shrink: 0;
  margin-right: 1rem;
  display: flex;
  align-items: center;
  font-size: 1.5rem;
  font-weight: 900;
  font-family: NYJGothicEB;
`;

const ContentWrapper = styled.div`
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  display: flex;
  align-items: center;
`;

const Content = styled.div`
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

const PlusContent = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  font-size: 3.5rem;
  font-weight: 900;
  justify-content: center;
  font-family: NYJGothicEB;
`;

const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6); // 반투명 검은색 배경
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 10;
  font-weight: 600;
`;
const ButtonBox = styled.div`
  width: 10.5rem;
  height: auto;
  display: flex;
  gap: 2rem;
  /* border: 1px solid ${Color.GRAY100}; */
`;
const CheckListPage = () => {
  const [checkListModal, setCheckListModal] = useState<boolean>(false);
  const [deleteCheckListModal, setDeleteCheckListModal] =
    useState<boolean>(false);
  const [createCheckListModal, setCreateCheckListModal] =
    useState<boolean>(false);
  const [currentChecklist, setCurrentChecklist] =
    useState<CheckListType | null>(null);
  const handleCheckListClick = (list: CheckListType) => {
    setCheckListModal(true);
    setCurrentChecklist(list);
  };
  const handlePlusCheckListClick = () => {
    setCreateCheckListModal(true);
  };
  const handleDeleteCheckListClick = (list: CheckListType) => {
    setDeleteCheckListModal(true);
    setCurrentChecklist(list);
  };
  const handleCloseModal = (
    e: React.MouseEvent<HTMLDivElement, MouseEvent>,
  ) => {
    setCheckListModal(false);
    setDeleteCheckListModal(false);
    setCreateCheckListModal(false);
    e.stopPropagation();
    setCurrentChecklist(null);
  };
  const handleModalClick = (
    e: React.MouseEvent<HTMLDivElement, MouseEvent>,
  ) => {
    e.stopPropagation();
  };
  const handleCloseCreateModal = () => {
    setCreateCheckListModal(false);
  };
  const handleCloseDeleteModal = () => {
    setDeleteCheckListModal(false);
  };
  // react query 이용해서 수정
  const { data: basicCheckListData } = useQuery({
    queryKey: ['basicCheckList'],
    queryFn: () => getBasicCheckList(),
  });
  const { data: checkListData } = useQuery({
    queryKey: ['checkList'],
    queryFn: () => getCheckList(),
  });
  return (
    <>
      <BackGround>
        {checkListModal && currentChecklist && (
          <Overlay onClick={handleCloseModal}>
            <CheckListModal
              onClick={handleModalClick}
              context={currentChecklist.context}
            />
          </Overlay>
        )}
        {deleteCheckListModal && currentChecklist && (
          <Overlay onClick={handleCloseModal}>
            <DeleteCheckListModal
              onClick={handleModalClick}
              checklistId={currentChecklist.id}
              onClose={handleCloseDeleteModal}
            />
          </Overlay>
        )}
        {createCheckListModal && (
          <Overlay onClick={handleCloseModal}>
            <CreateCheckListModal
              onClick={handleModalClick}
              onClose={handleCloseCreateModal}
            />
          </Overlay>
        )}
        <Box>
          <>
            {basicCheckListData?.map((list: CheckListType) => (
              <ListBox key={list.id} onClick={() => handleCheckListClick(list)}>
                <ContentBox>
                  <ContentWrapper>
                    <Content>{list.context}</Content>
                  </ContentWrapper>
                </ContentBox>
              </ListBox>
            ))}
            {checkListData?.map((list: CheckListType) => (
              <PlusListBox>
                <ContentBox onClick={() => handleCheckListClick(list)}>
                  <ContentWrapper>
                    <Content>{list.context}</Content>
                  </ContentWrapper>
                </ContentBox>
                <ButtonBox>
                  <Button
                    onClick={() => handleDeleteCheckListClick(list)}
                    width={4}
                    height={2.5}
                    $backgroundColor={Color.GRAY200}
                    $borderColor={Color.GRAY200}
                    $borderRadius={1}
                    $hoverBackgroundColor={Color.RED}
                    $hoverBorderColor={Color.GRAY300}
                    fontWeight={900}
                    children={'삭제'}
                    fontSize={1.1}
                  ></Button>
                </ButtonBox>
              </PlusListBox>
            ))}
          </>
          <ListBox>
            <PlusContent onClick={handlePlusCheckListClick}>+</PlusContent>
          </ListBox>
        </Box>
      </BackGround>
    </>
  );
};

export default CheckListPage;
