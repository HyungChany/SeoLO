import { api } from './Base.ts';
// interface MachinesType {

// }
export const EquipmentRegistration = async (companycode: string) => {
  try {
    const accessToken = sessionStorage.getItem('accesstoken');
    const response = await api.post(`/machines`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companycode,
      },
    });
    return response.data;
  } catch (error) {
    console.log(error);
  }
};
