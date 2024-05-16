import { useEffect } from 'react';
import { useSetRecoilState } from 'recoil';
import { EventSourcePolyfill } from 'event-source-polyfill';
import { notificationEventsState } from '@/recoil/sseState.tsx';

interface NotificationEvent {
  battery_info: number;
  worker_name: string;
  facility_name: string;
  machine_number: string;
  locker_uid: string;
  act_type: string;
}

const useSSE = () => {
  const setEvents = useSetRecoilState(notificationEventsState);
  const url = `${import.meta.env.VITE_REACT_APP_API_URL}/pub`;

  useEffect(() => {
    let isMounted = true;
    const accessToken = sessionStorage.getItem('accessToken') || '';
    const companyCode = sessionStorage.getItem('companyCode') || '';

    console.log('Access Token:', accessToken);  // 로그 추가
    console.log('Company Code:', companyCode);  // 로그 추가

    const connectSSE = () => {
      const eventSource = new EventSourcePolyfill(url, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          'Company-Code': companyCode,
          'Device-Type': 'web',
        },
        heartbeatTimeout: 20 * 60 * 1000, // 20분
    
      });
      
      eventSource.onmessage = (event) => {
        if (event.data === 'heartbeat') {
          console.log('Heartbeat received');
        } else {
          try {
            const newEvent: NotificationEvent = JSON.parse(event.data);
            if (isMounted) {
              setEvents((prevEvents) => [...prevEvents, newEvent]);
            }
            console.log('New SSE Event:', newEvent);
          } catch (error) {
            console.error('Message parsing error:', error);
          }
        }
      };

      eventSource.onerror = (error) => {
        console.error('EventSource failed:', error);
        eventSource.close();
        if (isMounted) {
          setTimeout(connectSSE, 15000); // 5초 후 재연결 시도
        }
      };

      return () => {
        eventSource.close();
      };
    };

    const disconnectSSE = connectSSE();

    return () => {
      isMounted = false;
      disconnectSSE();
    };
  }, [url, setEvents]);

  return null;
};

export default useSSE;
