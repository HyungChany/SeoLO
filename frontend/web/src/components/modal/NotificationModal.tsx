import React, { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import styled from 'styled-components';
import { notificationEventsState } from '@/recoil/sseState.tsx';
import batteryIcon from '@/../assets/images/battery60.png';
import lockerIcon from '@/../assets/images/locker1.png';

const OuterContainer = styled.div`
  width: 100%;
  height: 100%;
  position: relative;
`;

const ModalContainer = styled.div`
  width: 238px;
  height: 193px;
  position: absolute;
  right: 40px;
  bottom: 45px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const AdditionalImage = styled.img`
  width: 50px;
  height: 70.65px;
  position: absolute;
  right: 10px;
  bottom: 5px;
`;

const Modal = styled.div`
  width: 238px;
  height: 193px;
  position: relative;
  background-color: #fffafa;
  box-shadow: 6px 10px 4px 1px rgba(0, 0, 0, 0.25);
  border-radius: 10px;
  overflow: hidden;
  border: 0.5px solid #c0b6b6;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
`;

const BatteryIcon = styled.img`
  width: 18px;
  height: 50px;
`;

const TextContainer = styled.div`
  width: 208px;
  height: 146px;
  padding: 10px;
`;

const WorkerName = styled.span`
  color: #135b9e;
  font-size: 24px;
  font-family: Arial;
  font-weight: 400;
  word-wrap: break-word;
`;

const Text = styled.span`
  color: black;
  font-size: 24px;
  font-family: Arial;
  font-weight: 400;
  word-wrap: break-word;
`;

const ActionType = styled.span`
  color: #fd4154;
  font-size: 24px;
  font-family: Arial;
  font-weight: 400;
  word-wrap: break-word;
`;

const NotificationModal: React.FC = () => {
  const events = useRecoilValue(notificationEventsState);
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    if (events.length > 0) {
      setVisible(true);
      const timer = setTimeout(() => {
        setVisible(false);
      }, 5000); // 5초 후 모달 닫기

      return () => clearTimeout(timer);
    }
  }, [events]);

  if (!visible || events.length === 0) {
    return null; // 모달이 보이지 않거나 이벤트가 없으면 표시하지 않음
  }

  const event = events[events.length - 1]; // 가장 최근 이벤트를 표시

  return (
    <OuterContainer>
      <BatteryIcon src={batteryIcon} />
      <ModalContainer>
        <Modal>
          <TextContainer>
            <WorkerName>{event.worker_name}</WorkerName>
            <Text>
              님이
              <br />
              {event.facility_name} <br />
              {event.machine_number} 장비
              <br />
            </Text>
            <ActionType>{event.act_type}</ActionType>
            <Text> 하셨습니다.</Text>
          </TextContainer>
        </Modal>
      </ModalContainer>
      <AdditionalImage src={lockerIcon} />
    </OuterContainer>
  );
};

export default NotificationModal;
