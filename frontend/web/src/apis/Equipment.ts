import { api } from './Base.ts';
interface MachinesType {
  facilityId: string;
  machineName: string;
  machineCode: string;
  machineThum: string;
  introductionDate: string;
  mainManagerId: string;
  subManagerId: string;
}
export const EquipmentRegistration = async (machineData: MachinesType) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.post(`/machines`, machineData, {
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

export const EquipmentList = async (facilityId: string) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get(`/machines/facility/${facilityId}`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
      },
    });
    console.log(response.data.machines);
    return response.data.machines;
  } catch (error) {
    console.log(error);
  }
};
