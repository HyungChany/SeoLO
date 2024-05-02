import { api } from './Base.ts';

// interface LoginType {
//   companyCode: string;
//   username: string;
//   password: string;
// }

export const userLogin = async (loginData: FormData) => {
  try {
    // FormData 객체 생성
    // const formData = new FormData();
    // formData.append('username', loginData.username);
    // formData.append('password', loginData.password);
    // formData.append('companyCode', loginData.companyCode);
    // console.log('loginData', loginData);
    // formData.append('logindata', loginData);
    // FormData를 사용하여 로그인 요청 전송
    const response = await api.post('/login', loginData);
    return response.data;
  } catch (error) {
    console.error('로그인 실패: ', error);
    throw error;
  }
};
