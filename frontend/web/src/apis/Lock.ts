import { api } from './Base.ts';

export const lockCheck = async (companycode: string) => {
  try {
    const response = await api.get('/locks', {
      headers: { 'Company-Code': companycode },
    });
    return response.data;
  } catch (error) {
    console.log(error);
  }
};
