import { api } from './Base.ts';

export const Facilities = async (companycode: string) => {
  try {
    const accessToken = sessionStorage.getItem('accesstoken');
    const response = await api.get(`/facilities`, {
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
