import { AxiosError } from 'axios';
import { api } from './Base.ts';

interface ListType {
  context: string;
}

// 체크리스트 조회
export const getBasicCheckList = async () => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get('/checklists', {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
        'Device-Type': 'web',
      },
    });
    return response.data.basic_checklists;
  } catch (error) {
    if (error instanceof AxiosError) {
      const errorCode = error.response?.data.error_code;
      console.log('AxiosError:', error.response);
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
export const getCheckList = async () => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get('/checklists', {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
        'Device-Type': 'web',
      },
    });
    return response.data.checklists;
  } catch (error) {
    console.log('체크리스트', error);
    if (error instanceof AxiosError) {
      const errorCode = error.response?.data.error_code;
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

// 체크리스트 추가
export const postCheckList = async (listData: ListType) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.post('/checklists', listData, {
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
      console.log('AxiosError:', error.response);
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

// 체크리스트 수정
export const patchtCheckList = async (
  listData: ListType,
  checklistId: number,
) => {
  try {
    const response = await api.patch(`/checklist/${checklistId}`, listData);
    return response.data;
  } catch (error) {
    if (error instanceof AxiosError) {
      const errorCode = error.response?.data.error_code;
      console.log('AxiosError:', error.response);
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

// 체크리스트 삭제
export const deleteCheckList = async (checklistId: number) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.delete(`/checklists/${checklistId}`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
        'Device-Type': 'web',
      },
    });
    return response;
  } catch (error) {
    if (error instanceof AxiosError) {
      const errorCode = error.response?.data.error_code;
      console.log('AxiosError:', error.response);
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
