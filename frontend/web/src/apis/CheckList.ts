import { api } from './Base.ts';

interface ListType {
  context: string;
}

// 체크리스트 조회
export const getCheckList = async () => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const companyCode = sessionStorage.getItem('companyCode');
    const response = await api.get('/checklists', {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Company-Code': companyCode,
      },
    });
    return response.data;
  } catch (error) {
    console.error('체크리스트 불러오기 실패: ', error);
    throw error;
  }
};

// 체크리스트 추가
export const postCheckList = async (listData: ListType) => {
  try {
    const response = await api.post('/checklist', listData);
    return response.data;
  } catch (error) {
    console.error('체크리스트 추가 실패: ', error);
    throw error;
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
    console.error('체크리스트 수정 실패: ', error);
    throw error;
  }
};

// 체크리스트 삭제
export const deleteCheckList = async (checklistId: number) => {
  try {
    const response = await api.delete(`/checklist/${checklistId}`);
    return response.data;
  } catch (error) {
    console.error('체크리스트 삭제 실패: ', error);
    throw error;
  }
};
