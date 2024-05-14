import { api } from './Base.ts';
interface PatchData {
  isAccident: boolean;
  accident_type: string;
  victims_num: number;
  index: number;
}
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

export const detailReport = async (id: number) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get(`/reports/${id}`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
      },
    });
    return response.data;
  } catch (error) {
    console.log(error);
  }
};

export const modifyReport = async (data: PatchData) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const patchData = {
      isAccident: data.isAccident,
      accident_type: data.accident_type,
      victims_num: data.victims_num,
    };
    const response = await api.patch(`/reports/${data.index}`, patchData, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
      },
    });
    return response.data;
  } catch (error) {
    console.log(error);
  }
};

export const RangeReport = async (start: string, end: string) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get(
      `/reports/term?startDate=${start}&endDate=${end}`,
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          'Company-Code': companyCode,
        },
      },
    );
    return response.data.reports;
  } catch (e) {
    console.log(e);
  }
};
