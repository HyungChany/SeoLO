import { api } from './Base.ts';
interface BlueprintType {
  data: FormData;
  facilityId: number;
}
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

export const blueprintRegitration = async (data: BlueprintType) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.put(
      `/facilities/${data.facilityId}/blueprints`,
      data.data,
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          'Company-Code': companyCode,
        },
      },
    );
    console.log(1);
    return response.data;
  } catch (error) {
    console.error('도면 등록 실패: ', error);
    throw error;
  }
};

export const blueprintList = async (facilityId: number) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get(`/facilities/${facilityId}/blueprints`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
      },
    });
    return response.data;
  } catch (error) {
    console.error('도면 불러오기 실패: ', error);
    throw error;
  }
};
