import { api } from './Base.ts';
interface MachinesType {
  facilityId: number;
  machineName: string;
  machineCode: string;
  machineThum: string;
  introductionDate: string;
  mainManagerNum: string;
  subManagerNum: string;
}
export const MachineRegistration = async (machineData: MachinesType) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.post(`/machines`, machineData, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
        'Device-Type': 'web',
      },
    });
    alert('장비등록에 성공하였습니다.');
    return response.data;
  } catch (error) {
    console.log(error);
  }
};

export const MachineList = async (facilityId: number) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get(`/machines/facility/${facilityId}`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
        'Device-Type': 'web',
      },
    });
    console.log(response.data.machines);
    return response.data.machines;
  } catch (error) {
    console.log(error);
  }
};

export const MachinePhoto = async (machine: FormData) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.post(`/file`, machine, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
        'Device-Type': 'web',
      },
    });
    return response.data;
  } catch (error) {
    console.log(error);
  }
};
