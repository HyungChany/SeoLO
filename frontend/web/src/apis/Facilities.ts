import { AxiosError } from 'axios';
import { api } from './Base.ts';

export const Facilities = async () => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get(`/facilities`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
        'Device-Type': 'web',
      },
    });

    return response.data.facilities;
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
