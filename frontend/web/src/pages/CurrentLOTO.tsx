import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import { lockCheck } from '@/apis/Lock.ts';
import { useEffect, useState } from 'react';
interface ContentBoxProps {
  battery: number; // battery 속성에 대한 타입 정의
}
interface LockTypes {
  id: number;
  uid: string;
  locked: boolean;
  battery: number;
}
const BackGround = styled.div`
  width: 100%;
  height: auto;
  min-height: 100%;
  justify-content: center;
  display: flex;
`;
const Box = styled.div`
  width: 46.9375rem;
  height: 100%;
  display: flex;
  flex-direction: column;
`;

const TitleBox = styled.div`
  width: 100%;
  height: 4.25rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-sizing: border-box;
`;
const Content = styled.div`
  width: 8.75rem;
  height: 1.5rem;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.25rem;
  font-weight: 500;
  text-overflow: ellipsis;
`;
const ContentBox = styled.div<ContentBoxProps>`
  width: 100%;
  height: 3.125rem;
  justify-content: space-between;
  align-items: center;
  /* padding: 0 1rem; */
  display: flex;
  flex-direction: row;
  box-sizing: border-box;
  background-color: ${(props) => (props.battery < 40 ? Color.RED100 : null)};
  border-bottom: 2px solid ${Color.GRAY200};
`;
const CurrentLOTO = () => {
  const [lockers, setLockers] = useState<LockTypes[]>([]);
  // const lock = lockCheck('SFY001KOR');
  // console.log('라커', lock);
  useEffect(() => {
    const fetchLocks = async () => {
      const data = await lockCheck();
      console.log('라커', data); // data를 상태로 설정
      const newOptions = data.map((locks: LockTypes) => ({
        id: locks.id,
        uid: locks.uid,
        locked: locks.locked,
        battery: locks.battery,
      }));
      setLockers(newOptions);
    };
    fetchLocks();
  }, []);
  return (
    <BackGround>
      <Box>
        <TitleBox>
          <Content style={{ fontSize: '1.5rem', fontWeight: '900' }}>
            No.
          </Content>
          <Content style={{ fontSize: '1.5rem', fontWeight: '900' }}>
            LOTO S/N
          </Content>
          <Content style={{ fontSize: '1.5rem', fontWeight: '900' }}>
            잠금 상태
          </Content>
          <Content style={{ fontSize: '1.5rem', fontWeight: '900' }}>
            배터리 잔량
          </Content>
        </TitleBox>
        {lockers.map((locker) => (
          <ContentBox key={locker.id} battery={locker.battery}>
            <Content>{locker.id}</Content>
            <Content>{locker.uid}</Content>
            <Content>{locker.locked ? 'LOCK' : 'UNLOCK'}</Content>
            <Content>{locker.battery}%</Content>
          </ContentBox>
        ))}
      </Box>
    </BackGround>
  );
};

export default CurrentLOTO;
