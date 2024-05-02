import { api } from './Base.ts';

// interface LoginType {
//   companyCode: string;
//   username: string;
//   password: string;
// }

export const userLogin = async (loginData: FormData) => {
  try {
    const response = await api.post('/login', loginData);
    return response.data;
  } catch (error) {
    console.error('로그인 실패: ', error);
    throw error;
  }
};
