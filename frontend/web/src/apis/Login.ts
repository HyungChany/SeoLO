import { api } from './Base.ts';

interface LoginType {
  username: string;
  password: string;
  company_code: string;
}

export const userLogin = async (loginData: LoginType) => {
  try {
    const response = await api.post('/login', loginData);
    return response.data;
  } catch (error) {
    console.error('로그인 실패: ', error);
    throw error;
  }
};

export const Logout = async () => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.post(
      `/logout`,
      {},
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          'Company-Code': companyCode,
        },
      },
    );
    sessionStorage.removeItem('accessToken');
    sessionStorage.removeItem('companyCode');
    return response.data;
  } catch (error) {
    console.error('로그아웃 실패: ', error);
  }
};
