import { api } from './Base.ts';

export const EquipmentRegistration = async () => {
  try {
    const accessToken = sessionStorage.getItem('accesstoken');
    const response = await api.post(`/machines`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data;
  } catch (error) {
    console.log(error);
  }
};
