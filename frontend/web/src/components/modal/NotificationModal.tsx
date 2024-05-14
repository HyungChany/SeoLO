import React, { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import { notificationEventsState } from '../../recoil/sseState';

const NotificationModal: React.FC = () => {
  const events = useRecoilValue(notificationEventsState);
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    if (events.length > 0) {
      setVisible(true);
      const timer = setTimeout(() => {
        setVisible(false);
      }, 100000); // 5초 후 모달 닫기

      return () => clearTimeout(timer);
    }
  }, [events]);

  if (!visible || events.length === 0) {
    return null; // 모달이 보이지 않거나 이벤트가 없으면 표시하지 않음
  }

  const event = events[events.length - 1]; // 가장 최근 이벤트를 표시

  return (
    <div style={modalStyle}>
      <h1>Notification</h1>
      <ul>
        <li><strong>Worker Name:</strong> {event.worker_name}</li>
        <li><strong>Facility Name:</strong> {event.facility_name}</li>
        <li><strong>Machine Number:</strong> {event.machine_number}</li>
        <li><strong>Locker UID:</strong> {event.locker_uid}</li>
        <li><strong>Act Type:</strong> {event.act_type}</li>
        <li><strong>Battery Info:</strong> {event.battery_info}</li>
      </ul>
    </div>
  );
};

const modalStyle: React.CSSProperties = {
  position: 'fixed',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  padding: '20px',
  backgroundColor: 'white',
  boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
  zIndex: 1000,
};

export default NotificationModal;
