import { useState, ChangeEvent, useEffect } from 'react';
import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import Card from '@/components/card/Card.tsx';
import * as Typo from '@/components/typography/Typography.tsx';
import InputBox from '@/components/inputbox/InputBox.tsx';
import { Button } from '@/components/button/Button.tsx';
import People from '/assets/images/people.png';
import {
  EmployeeDetail,
  EmployeeRegistration,
  RegistratedEmployee,
} from '@/apis/Employee.ts';
import { useNavigate } from 'react-router-dom';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
interface EmployeeType {
  employee_join_date: string;
  employee_leave_date: string;
  employee_num: string;
  employee_name: string;
  employee_title: string;
  employee_team: string;
  employee_birthday: string;
  employee_thum: string;
}
const Background = styled.div`
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  background-color: ${Color.GRAY200};
  display: flex;
  align-items: center;
  overflow-x: auto;
  position: relative;
`;
const Box = styled.div`
  min-width: 1280px;
  height: 34rem;
  display: flex;
  justify-content: space-between;
  margin: 0 auto;
`;
const ImgBox = styled.img`
  width: 13.5rem;
`;
const InformationBox = styled.div`
  width: calc(100% - 26rem);
  height: 100%;
  display: flex;
  justify-content: space-between;
  padding: 1.8rem;
  box-sizing: border-box;
  background-color: ${Color.WHITE};
  border-radius: 1.25rem;
  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.25);
  flex-direction: column;
  /* overflow: hidden; */
`;
const TopBox = styled.div`
  width: 100%;
  height: 88%;
  box-sizing: border-box;
  display: flex;
  justify-content: space-between;
`;
const LeftBox = styled.div`
  width: 47.5%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
`;
// const DropdownBox = styled.div`
//   width: 100%;
//   display: flex;
//   flex-direction: column;
//   gap: 1rem;
// `;
const PhotoBox = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;
const Photo = styled.div`
  width: 100%;
  height: 24rem;
  display: flex;
  justify-content: center;
  align-items: center;
  color: ${Color.GRAY500};
  border: 1px solid ${Color.GRAY200};
  border-radius: 1.25rem;
  font-size: 1.5rem;
  font-weight: bold;
  background-color: ${Color.GRAY100};
`;
const Preview = styled.img`
  width: 100%;
  height: 100%;
  border-radius: 1.25rem;
  /* object-fit: cover; */
`;
const RightBox = styled.div`
  width: 47.5%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  box-sizing: border-box;
`;
const CommonBox = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  gap: 1rem;
`;
const ButtonBox = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
`;
const ContentBox = styled.div`
  width: 100%;
  height: 3rem;
  display: flex;
  box-sizing: border-box;
  padding: 0.5rem;
  background-color: ${Color.GRAY100};
  border: 1px solid ${Color.GRAY300};
  border-radius: 8px;
  font-size: 1.25rem;
  align-items: center;
`;
const Content = styled.div`
  width: auto;
  height: auto;
  font-size: 1.5rem;
  font-weight: 400;
  color: ${Color.BLACK};
`;
const SearchBox = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  gap: 1rem;
  align-items: center;
`;
const WidthInputBox = styled(InputBox)<{ width?: string }>`
  width: ${(props) => props.width || '100%'};
`;

const Employee = () => {
  const [equipmentNumber, setEquipmentNumber] = useState<string>('');
  const companyCode = sessionStorage.getItem('companyCode');
  const [EmployeeNumber, setEmployeeNumber] = useState<number>(0);
  const [employeeInformation, setEmployeeInformation] =
    useState<EmployeeType | null>(null);
  const [searchEnabled, setSearchEnabled] = useState(false);
  const handleEquipmentNumber = (e: ChangeEvent<HTMLInputElement>) => {
    setEquipmentNumber(e.target.value);
  };
  const queryClient = useQueryClient();
  // 작업자 등록
  const { mutate: registrationMutate } = useMutation({
    mutationFn: EmployeeRegistration,
    onSuccess: () =>
      queryClient.invalidateQueries({ queryKey: ['employeeList'] }),
  });

  // 작업자 디테일 정보
  const { data: employeeDetail } = useQuery({
    queryKey: ['employeeDetail'],
    queryFn: () => {
      if (equipmentNumber) {
        return EmployeeDetail(equipmentNumber);
      }
    },
    enabled: searchEnabled,
  });

  // 등록된 작업자 전체 조회
  const { data: employeeList } = useQuery({
    queryKey: ['employeeList'],
    queryFn: () => {
      return RegistratedEmployee();
    },
  });
  const navigate = useNavigate();
  const handleSubmit = async () => {
    if (employeeInformation && companyCode) {
      const formattedBirthday = employeeInformation.employee_birthday.replace(
        /-/g,
        '',
      );
      const employeeData = {
        username: equipmentNumber,
        password: 'A' + 'a' + formattedBirthday + '@', // 초기 비밀번호는 Aa생년월일@
        company_code: companyCode,
      };
      registrationMutate(employeeData);
    } else {
      alert('사번을 입력해주세요.');
    }
  };
  const handleCancel = () => {
    setEquipmentNumber('');
    navigate('/information');
  };
  const handleSearch = () => {
    setSearchEnabled(true);
  };

  useEffect(() => {
    if (employeeDetail) {
      setEmployeeInformation(employeeDetail);
      setSearchEnabled(false);
    }
  }, [employeeDetail]);
  useEffect(() => {
    if (employeeList) {
      setEmployeeNumber(employeeList.length);
    }
  }, [employeeList]);

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      handleSearch();
    }
  };
  return (
    <Background>
      <Box>
        <Card
          width={22}
          height={'100%'}
          justifyContent={'space-between'}
          flexDirection={'column'}
          alignItems="center"
        >
          <Typo.H3>
            <div style={{ marginTop: '1.3rem' }}>등록 임직원현황</div>
          </Typo.H3>
          <ImgBox src={People} />
          <Typo.H0 color={Color.BLACK}>{EmployeeNumber}</Typo.H0>
        </Card>
        <InformationBox>
          <TopBox>
            <LeftBox>
              <CommonBox>
                <Typo.H3>사번</Typo.H3>

                <SearchBox>
                  <WidthInputBox
                    height={3}
                    value={equipmentNumber}
                    onChange={handleEquipmentNumber}
                    placeholder="사번을 입력하세요"
                    onKeyDown={handleKeyDown}
                  />
                  <Button
                    width={5}
                    height={2.5}
                    $backgroundColor={Color.GRAY200}
                    $borderColor={Color.GRAY200}
                    $borderRadius={0.75}
                    $hoverBackgroundColor={Color.GRAY300}
                    $hoverBorderColor={Color.GRAY300}
                    onClick={handleSearch}
                    fontSize={'1.2'}
                    fontWeight={'bold'}
                  >
                    검색
                  </Button>
                </SearchBox>
              </CommonBox>
              <CommonBox>
                <Typo.H3>이름</Typo.H3>
                <ContentBox>
                  {employeeInformation ? (
                    <Content>{employeeInformation.employee_name}</Content>
                  ) : (
                    <Content></Content>
                  )}
                </ContentBox>
              </CommonBox>
              <CommonBox>
                <Typo.H3>소속 부서</Typo.H3>
                <ContentBox>
                  {employeeInformation ? (
                    <Content>{employeeInformation.employee_team}</Content>
                  ) : (
                    <Content></Content>
                  )}
                </ContentBox>
              </CommonBox>
              <CommonBox>
                <Typo.H3>직급</Typo.H3>
                <ContentBox>
                  {employeeInformation ? (
                    <Content>{employeeInformation.employee_title}</Content>
                  ) : (
                    <Content></Content>
                  )}
                </ContentBox>
              </CommonBox>
            </LeftBox>
            <RightBox>
              <PhotoBox>
                <Typo.H3>사원 사진</Typo.H3>
                <Photo>
                  {employeeInformation ? (
                    <Preview
                      src={employeeInformation.employee_thum}
                      alt="Uploaded Image Preview"
                    />
                  ) : (
                    '사진'
                  )}
                </Photo>
              </PhotoBox>
            </RightBox>
          </TopBox>
          <ButtonBox>
            <Button
              width={5}
              height={2.5}
              $backgroundColor={Color.GRAY200}
              $borderColor={Color.GRAY200}
              $borderRadius={0.75}
              $hoverBackgroundColor={Color.GRAY300}
              $hoverBorderColor={Color.GRAY300}
              onClick={handleCancel}
              fontSize={'1.2'}
              fontWeight={'bold'}
            >
              취소
            </Button>
            <Button
              width={5}
              height={2.5}
              $backgroundColor={Color.GRAY200}
              $borderColor={Color.GRAY200}
              $borderRadius={0.75}
              $hoverBackgroundColor={Color.GRAY300}
              $hoverBorderColor={Color.GRAY300}
              onClick={handleSubmit}
              fontSize={'1.2'}
              fontWeight={'bold'}
            >
              완료
            </Button>
          </ButtonBox>
        </InformationBox>
      </Box>
    </Background>
  );
};

export default Employee;
