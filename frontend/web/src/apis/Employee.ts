import { api } from './Base.ts';
import axios, { AxiosError } from 'axios';
interface EmployeeType {
  username: string;
  password: string;
  company_code: string;
}
export const EmployeeList = async () => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get(`/employees`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
        'Device-Type': 'web',
      },
    });
    return response.data.employees;
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
export const RegistratedEmployee = async () => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get(`/users`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
        'Device-Type': 'web',
      },
    });
    return response.data.workers;
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
export const EmployeeDetail = async (employeeNumber: string) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get(`/employees/${employeeNumber}`, {
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
      } else {
        alert('해당 직원이 존재하지 않습니다');
      }
    }
  }
};

export const EmployeeRegistration = async (employeeData: EmployeeType) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const response = await api.post(`/join`, employeeData, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Device-Type': 'web',
      },
    });
    alert('임직원 등록에 성공하였습니다.');
    return response.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      if (error.response) {
        const errorCode = error.response?.data.error_code;
        if (errorCode && errorCode.startsWith('JT')) {
          sessionStorage.removeItem('accessToken');
          sessionStorage.removeItem('companyCode');
        }
        alert(error.response.data.message); // 오류 메시지를 보여줍니다.
      }
    }
  }
};
