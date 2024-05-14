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
      }, 1000000); // 5초 후 모달 닫기

      return () => clearTimeout(timer);
    }
  }, [events]);

  if (!visible || events.length === 0) {
    return null; // 모달이 보이지 않거나 이벤트가 없으면 표시하지 않음
  }

  const event = events[events.length - 1]; // 가장 최근 이벤트를 표시

  return (
    <div style={outerContainerStyle}>
    <img style={imageStyle} src="@/../assets/images/battery60.png" alt="Icon" />
      <div style={modalContainerStyle}>
        <div style={modalStyle}>   
          <div style={textContainerStyle}>
            <span style={workerNameStyle}>{event.worker_name}</span>
            <span style={textStyle}>
              님이<br />
              {event.facility_name} <br />
              {event.machine_number} 장비<br />
            </span>
            <span style={actionTypeStyle}>{event.act_type}</span>
            <span style={textStyle}> 하셨습니다.</span>
          </div>
        </div>
      </div>
      <img style={additionalImageStyle} src="@/../assets/images/locker1.png" alt="Decoration" />
    </div>
  );
};

const outerContainerStyle: React.CSSProperties = {
  width: '100%',
  height: '100%',
  position: 'relative',
};

const modalContainerStyle: React.CSSProperties = {
  width: 238,
  height: 193,
  position: 'absolute',
  right: '40px',
  bottom: '45px',
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
};

const additionalImageStyle: React.CSSProperties = {
  width: 50,
  height: 70.65,
  position: 'absolute',
  right: '10px',
  bottom: '5px',
};


const modalStyle: React.CSSProperties = {
  width: 238,
  height: 193,
  position: 'relative',
  backgroundColor: '#FFFAFA',
  boxShadow: '6px 10px 4px 1px rgba(0, 0, 0, 0.25)',
  borderRadius: 10,
  overflow: 'hidden',
  border: '0.50px solid #C0B6B6',
  display: 'flex',
  flexDirection: 'column',
  justifyContent: 'flex-start',
  alignItems: 'flex-start',
};

const imageStyle: React.CSSProperties = {
    width: 18,
    height: 50,
};

const textContainerStyle: React.CSSProperties = {
  width: 208,
  height: 146,
  padding: '10px',
};

const workerNameStyle: React.CSSProperties = {
  color: '#135B9E',
  fontSize: 24,
  fontFamily: 'Arial', // 실제 폰트 패밀리로 교체
  fontWeight: '400',
  wordWrap: 'break-word',
};

const textStyle: React.CSSProperties = {
  color: 'black',
  fontSize: 24,
  fontFamily: 'Arial', // 실제 폰트 패밀리로 교체
  fontWeight: '400',
  wordWrap: 'break-word',
};

const actionTypeStyle: React.CSSProperties = {
  color: '#FD4154',
  fontSize: 24,
  fontFamily: 'Arial', // 실제 폰트 패밀리로 교체
  fontWeight: '400',
  wordWrap: 'break-word',
};


export default NotificationModal;
