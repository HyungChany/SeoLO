import { AxiosError } from 'axios';
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
    if (error instanceof AxiosError) {
      const errorCode = error.response?.data.error_code;
      if (errorCode && errorCode.startsWith('JT')) {
        sessionStorage.removeItem('accessToken');
        sessionStorage.removeItem('companyCode');
      }
    }
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
    return response.data;
  } catch (error) {
    if (error instanceof AxiosError) {
      const errorCode = error.response?.data.error_code;

      if (errorCode && errorCode.startsWith('JT')) {
        sessionStorage.removeItem('accessToken');
        sessionStorage.removeItem('companyCode');
      } else if (errorCode && errorCode.startsWith('SF')) {
        alert(error.response?.data.message);
      }
    }
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
    if (error instanceof AxiosError) {
      const errorCode = error.response?.data.error_code;

      if (errorCode && errorCode.startsWith('JT')) {
        sessionStorage.removeItem('accessToken');
        sessionStorage.removeItem('companyCode');
      }
    }
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
    if (error instanceof AxiosError) {
      const errorCode = error.response?.data.error_code;
      if (errorCode && errorCode.startsWith('JT')) {
        sessionStorage.removeItem('accessToken');
        sessionStorage.removeItem('companyCode');
      } else if (errorCode && errorCode.startsWith('MR')) {
        alert(error.response?.data.message);
      }
    }
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

export const deleteMarker = async (index: number) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.delete(`/markers/${index}`, {
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
      }
    }
  }
};

export const detailMarker = async (markerId: number) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get(`/markers/${markerId}`, {
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
      }
    }
  }
};

export const presentUser = async () => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get(`/users/profile`, {
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
      }
    }
  }
};
