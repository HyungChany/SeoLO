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
