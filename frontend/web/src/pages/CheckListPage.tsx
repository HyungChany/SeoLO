import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import { Button } from '@/components/button/Button.tsx';

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
  /* display: flex; */
  width: 80%;
  height: 100%;
  /* flex-direction: column; */
  /* justify-content: center; */
  flex-shrink: 0;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  /* word-break: break-all; */
  align-items: center;
  font-size: 1.75rem;
  font-weight: 900;
`;

// const Content = styled.div`
//   width: 100%;
//   height: 100%;
//   display: flex;
//   align-items: center;
//   font-size: 1.75rem;
//   font-weight: 900;
//   overflow: hidden;
//   white-space: nowrap;
//   text-overflow: ellipsis;
//   /* word-break: break-all; */
// `;

const PlusContent = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  font-size: 2.5rem;
  font-weight: 900;
  justify-content: center;
`;

const CheckListPage = () => {
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
        <Box>
          {lists.map((list) => (
            <ListBox>
              <ContentBox key={list.id}>{list.content}</ContentBox>
              <Button
                onClick={() => console.log()}
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
                onClick={() => console.log()}
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
            <PlusContent>+</PlusContent>
          </ListBox>
        </Box>
      </BackGround>
    </>
  );
};

export default CheckListPage;
