import { AxiosError } from 'axios';
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

    return response.data;
  } catch (error) {
    if (error instanceof AxiosError) {
      const errorCode = error.response?.data.error_code;
      if (errorCode && errorCode.startsWith('JT')) {
        sessionStorage.removeItem('accessToken');
        sessionStorage.removeItem('companyCode');
      }
    }
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

    return response.data.machines;
  } catch (error) {
    if (error instanceof AxiosError) {
      const errorCode = error.response?.data.error_code;

      if (errorCode && errorCode.startsWith('JT')) {
        sessionStorage.removeItem('accessToken');
        sessionStorage.removeItem('companyCode');
      }
    }
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
    if (error instanceof AxiosError) {
      const errorCode = error.response?.data.error_code;
      if (errorCode && errorCode.startsWith('JT')) {
        sessionStorage.removeItem('accessToken');
        sessionStorage.removeItem('companyCode');
      }
    }
  }
};
