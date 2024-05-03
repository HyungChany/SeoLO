import { api } from './Base.ts';

export const lockCheck = async (companycode: string) => {
  try {
    const accessToken = sessionStorage.getItem('accesstoken');
    const response = await api.get('/locks', {
      headers: {
        'Company-Code': companycode,
        Authorization: `Bearer ${accessToken}`,
      },
    });
    return response.data;
  } catch (error) {
    console.log(error);
  }
};
