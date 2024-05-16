import { api } from './Base.ts';
interface BlueprintType {
  data: FormData;
  facilityId: number;
}

interface MarkerType {
  machine_id: number;
  marker_x: number;
  marker_y: number;
}
export const MainInformation = async (facilityId: number) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get(`/statistics/main/${facilityId}`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
        'Device-Type': 'web',
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
          'Device-Type': 'web',
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
        'Device-Type': 'web',
      },
    });
    return response.data;
  } catch (error) {
    console.error('도면 불러오기 실패: ', error);
    throw error;
  }
};

export const registrationMarker = async (data: MarkerType) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.post(`/markers`, data, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
        'Device-Type': 'web',
      },
    });
    return response.data;
  } catch (error) {
    console.log(error);
  }
};

export const simpleMachineCheck = async (facilityId: number) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get(`/machines/facilities/${facilityId}/easy`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
        'Device-Type': 'web',
      },
    });
    return response.data.machine_id_name_list;
  } catch (e) {
    console.log(e);
  }
};
