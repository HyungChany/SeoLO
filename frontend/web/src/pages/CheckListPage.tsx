import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import { Button } from '@/components/button/Button.tsx';
import { useEffect, useState } from 'react';
import CheckListModal from '@/components/modal/CheckListModal.tsx';
import DeleteCheckListModal from '@/components/modal/DeleteCheckListModal.tsx';
import { getCheckList } from '@/apis/CheckList.ts';
import CreateCheckListModal from '@/components/modal/CreateCheckListModal.tsx';
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
`;

const ContentBox = styled.div`
  width: 80%;
  height: 100%;
  flex-shrink: 0;
  display: flex;
  align-items: center;
  font-size: 1.75rem;
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
  font-size: 2.5rem;
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
  background-color: rgba(0, 0, 0, 0.5); // 반투명 검은색 배경
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 10;
`;
const ButtonBox = styled.div`
  width: 10.5rem;
  height: auto;
  display: flex;
  gap: 2rem;
`;
const CheckListPage = () => {
  const [checkListModal, setCheckListModal] = useState<boolean>(false);
  const [deleteCheckListModal, setDeleteCheckListModal] =
    useState<boolean>(false);
  const [lists, setLists] = useState<CheckListType[] | null>(null);
  const [plusLists, setPlusLists] = useState<CheckListType[] | null>(null);
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
  const handleDeleteCheckListClick = () => {
    setDeleteCheckListModal(true);
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
  //   const lists = [
  //     { id: 1, content: '체크리스트 1' },
  //     { id: 2, content: '체크리스트 2' },
  //     {
  //       id: 3,
  //       content:
  //         '체크리스트 3ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ',
  //     },
  //     { id: 4, content: '체크리스트 4' },
  //     { id: 5, content: '체크리스트 5' },
  //   ];
  useEffect(() => {
    const fetchLists = async () => {
      const data = await getCheckList();
      setLists(data.basic_checklists);
      setPlusLists(data.checklists);
      console.log(data);
    };
    fetchLists();
  }, []);
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
        {deleteCheckListModal && (
          <Overlay onClick={handleCloseModal}>
            <DeleteCheckListModal onClick={handleModalClick} />
          </Overlay>
        )}
        {createCheckListModal && (
          <Overlay onClick={handleCloseModal}>
            <CreateCheckListModal onClick={handleModalClick} />
          </Overlay>
        )}
        <Box>
          <>
            {lists &&
              lists.map((list) => (
                <ListBox>
                  <ContentBox key={list.id}>
                    <ContentWrapper>
                      <Content>{list.context}</Content>
                    </ContentWrapper>
                  </ContentBox>
                  <Button
                    onClick={() => handleCheckListClick(list)}
                    width={5}
                    height={1.5625}
                    $backgroundColor={Color.GRAY100}
                    $borderColor={'white'}
                    $borderRadius={0.3125}
                    $hoverBackgroundColor={'white'}
                    $hoverBorderColor={'white'}
                    children={'상세보기'}
                  ></Button>
                </ListBox>
              ))}
            {plusLists &&
              plusLists.map((list) => (
                <PlusListBox>
                  <ContentBox key={list.id}>
                    <ContentWrapper>
                      <Content>{list.context}</Content>
                    </ContentWrapper>
                  </ContentBox>
                  <ButtonBox>
                    <Button
                      onClick={() => handleCheckListClick(list)}
                      width={5}
                      height={1.5625}
                      $backgroundColor={Color.GRAY100}
                      $borderColor={'white'}
                      $borderRadius={0.3125}
                      $hoverBackgroundColor={'white'}
                      $hoverBorderColor={'white'}
                      children={'상세보기'}
                    ></Button>
                    <Button
                      onClick={handleDeleteCheckListClick}
                      width={3.5}
                      height={1.5625}
                      $backgroundColor={Color.GRAY100}
                      $borderColor={'white'}
                      $borderRadius={0.3125}
                      $hoverBackgroundColor={'white'}
                      $hoverBorderColor={'white'}
                      children={'삭제'}
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
