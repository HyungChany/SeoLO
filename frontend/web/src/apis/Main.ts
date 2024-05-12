import { api } from './Base.ts';

export const MainInformation = async (facilityId: number) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get(`/statistics/main/${facilityId}`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
      },
    });
    return response.data;
  } catch (error) {
    console.error('메인정보 불러오기 실패: ', error);
    throw error;
  }
};
