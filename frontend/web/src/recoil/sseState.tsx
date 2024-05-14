import { atom } from 'recoil';

interface NotificationEvent {
  battery_info: number;
  worker_name: string;
  facility_name: string;
  machine_number: string;
  locker_uid: string;
  act_type: string;
}

export const notificationEventsState = atom<NotificationEvent[]>({
  key: 'notificationEventsState',
  default: [],
});
