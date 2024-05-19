import { api } from './Base.ts';

interface LoginType {
  username: string;
  password: string;
  company_code: string;
}

export const userLogin = async (loginData: LoginType) => {
  const response = await api.post('/login', loginData, {
    headers: {
      'Device-Type': 'web',
    },
  });
  return response.data;
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
          'Device-Type': 'web',
        },
      },
    );
    sessionStorage.removeItem('accessToken');
    sessionStorage.removeItem('companyCode');
    return response;
  } catch (error) {
    alert('로그아웃 실패');
  }
};
