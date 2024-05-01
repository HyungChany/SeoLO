import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import { Button } from '@/components/button/Button.tsx';
import { useState } from 'react';
import CheckListModal from '@/components/modal/CheckListModal.tsx';
import DeleteCheckListModal from '@/components/modal/DeleteCheckListModal.tsx';

const BackGround = styled.div`
  width: 100%;
  min-height: 100%;
  height: auto;
  background-color: ${Color.GRAY200};
  justify-content: center;
  display: flex;
  overflow-y: auto;
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
  justify-content: space-evenly;
  align-items: center;
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

const CheckListPage = () => {
  const [checkListModal, setCheckListModal] = useState<boolean>(false);
  const [deleteCheckListModal, setDeleteCheckListModal] =
    useState<boolean>(false);
  const handleCheckListClick = () => {
    setCheckListModal(true);
  };
  const handleDeleteCheckListClick = () => {
    setDeleteCheckListModal(true);
  };
  const handleCloseModal = (
    e: React.MouseEvent<HTMLDivElement, MouseEvent>,
  ) => {
    setCheckListModal(false);
    setDeleteCheckListModal(false);
    e.stopPropagation();
  };
  const handleModalClick = (
    e: React.MouseEvent<HTMLDivElement, MouseEvent>,
  ) => {
    e.stopPropagation();
  };
  const lists = [
    { id: 1, content: '체크리스트 1' },
    { id: 2, content: '체크리스트 2' },
    {
      id: 3,
      content:
        '체크리스트 3ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ',
    },
    { id: 4, content: '체크리스트 4' },
    { id: 5, content: '체크리스트 5' },
  ];
  return (
    <>
      <BackGround>
        {checkListModal && (
          <Overlay onClick={handleCloseModal}>
            <CheckListModal onClick={handleModalClick} />
          </Overlay>
        )}
        {deleteCheckListModal && (
          <Overlay onClick={handleCloseModal}>
            <DeleteCheckListModal onClick={handleModalClick} />
          </Overlay>
        )}
        <Box>
          {lists.map((list) => (
            <ListBox>
              <ContentBox key={list.id}>
                <ContentWrapper>
                  <Content>{list.content}</Content>
                </ContentWrapper>
              </ContentBox>
              <Button
                onClick={handleCheckListClick}
                width={3.5}
                height={1.5625}
                $backgroundColor={Color.GRAY100}
                $borderColor={'white'}
                $borderRadius={0.3125}
                $hoverBackgroundColor={'white'}
                $hoverBorderColor={'white'}
                children={'수정'}
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
            </ListBox>
          ))}
          <ListBox>
            <PlusContent onClick={handleCheckListClick}>+</PlusContent>
          </ListBox>
        </Box>
      </BackGround>
    </>
  );
};

export default CheckListPage;
