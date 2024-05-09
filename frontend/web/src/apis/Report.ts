import { api } from './Base.ts';

export const totalReport = async () => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get('/reports', {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
      },
    });
    return response.data.reports;
  } catch (error) {
    console.log(error);
  }
};
