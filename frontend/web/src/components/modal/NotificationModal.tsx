import React, { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import styled from 'styled-components';
import { notificationEventsState } from '@/recoil/sseState.tsx';
import battery20 from '/battery20.png';
import battery40 from '/battery40.png';
import battery60 from '/battery60.png';
import battery80 from '/battery80.png';
import battery100 from '/battery100.png';
import lockerIcon from '/locker1.png';
interface NotificationEvent {
  battery_info: number;
  worker_name: string;
  facility_name: string;
  machine_number: string;
  locker_uid: string;
  act_type: string;
}
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
  padding: 10px;
`;

const BatteryIcon = styled.img`
  width: 18px;
  height: 50px;
  position: absolute;
  top: 10px;
  right: 10px;
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
  const [batteryImage, setBatteryImage] = useState<string>('');
  const event = events[events.length - 1];
  console.log('이��트', event);
  console.log('저 왔어요');
  useEffect(() => {
    if (events.length > 0) {
      console.log(2);
      if (event.battery_info) {
        const getBatteryImage = (level: number) => {
          if (level <= 20) {
            return battery20;
          } else if (level <= 40) {
            return battery40;
          } else if (level <= 60) {
            return battery60;
          } else if (level <= 80) {
            return battery80;
          } else {
            return battery100;
          }
        };
        setBatteryImage(getBatteryImage(event.battery_info));
      }

      console.log('이벤트', events);
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

  // 가장 최근 이벤트를 표시

  return (
    <OuterContainer>
      <ModalContainer>
        <Modal>
          {batteryImage ? <BatteryIcon src={batteryImage} /> : null}
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
