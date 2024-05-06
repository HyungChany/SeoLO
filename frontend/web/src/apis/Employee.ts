import { api } from './Base.ts';

export const EmployeeList = async () => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get(`/employees`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
      },
    });
    return response.data.employees;
  } catch (error) {
    console.log(error);
  }
};
