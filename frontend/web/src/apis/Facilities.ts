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
    console.log('에러', error);
    if (error instanceof AxiosError) {
      const errorCode = error.response?.data.error_code;
      console.log('공장:', error.response);
      if (errorCode && errorCode.startsWith('JT')) {
        sessionStorage.removeItem('accessToken');
        sessionStorage.removeItem('companyCode');
      }
    } else {
      // Handle other errors
      console.log('Unexpected Error:', error);
    }
  }
};
