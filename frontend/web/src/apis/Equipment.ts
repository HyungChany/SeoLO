import { api } from './Base.ts';
// interface MachinesType {

// }
export const EquipmentRegistration = async () => {
  try {
    const accessToken = sessionStorage.getItem('accesstoken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.post(`/machines`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
      },
    });
    return response.data;
  } catch (error) {
    console.log(error);
  }
};
