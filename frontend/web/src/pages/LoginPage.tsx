import * as Color from '@/config/color/Color.ts';
import styled from 'styled-components';
import Company from '/assets/icons/Company.svg';
import InputBox from '@/components/inputbox/InputBox.tsx';
import { ChangeEvent, useState } from 'react';
import User from '/assets/icons/Id.svg';
import Lock from '/assets/icons/Lock.svg';
const Background = styled.div`
  width: 100dvw;
  height: 100dvh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${Color.GRAY700};
`;

const LoginBox = styled.div`
  box-sizing: border-box;
  width: 28.5rem;
  height: 41rem;
  border-radius: 0.625rem;
  background-color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: column;
  padding: 5% 0;
`;

const NameBox = styled.div`
  display: flex;
  flex-direction: column;
`;

const NameKor = styled.div`
  font-family: esamanru;
  font-size: 5rem;
  font-weight: 700;
  color: ${Color.BLUE100};
`;

const NameEng = styled.div`
  margin-top: -12%;
  font-family: esamanru;
  font-size: 2.8rem;
  font-weight: 500;
  color: ${Color.BLUE100};
`;

const InputContainer = styled.div`
  width: 25rem;
  height: 13rem;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
`;
const InputContent = styled.div`
  width: 25rem;
  height: 3.125rem;
  display: flex;
  justify-content: space-between;
`;

const ButtonBox = styled.div`
  width: 25rem;
  height: 3.125rem;
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  font-family: NYJGothicM;
  font-size: 1.1rem;
  border-radius: 0.5rem;
  background-color: ${Color.BLUE100};
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  cursor: pointer;
  transition: box-shadow 0.3s ease;

  &:hover {
    background-color: #6d9ac5;
    transition: background-color 0.5s ease;
  }

  &:focus {
    box-shadow: none;
  }
`;
const LoginPage = () => {
  const [companyNumber, setCompanyNumber] = useState<string>('');
  const [loginId, setLoginId] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const handleCompanyNumber = (e: ChangeEvent<HTMLInputElement>) => {
    setCompanyNumber(e.target.value);
  };
  const handleLoginId = (e: ChangeEvent<HTMLInputElement>) => {
    setLoginId(e.target.value);
  };
  const handlePassword = (e: ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };
  return (
    <>
      <Background>
        <LoginBox>
          <NameBox>
            <NameKor>서로</NameKor>
            <NameEng>SeoLO</NameEng>
          </NameBox>
          <InputContainer>
            <InputContent>
              <img src={Company} alt="company" />
              <InputBox
                width={21.25}
                height={3.125}
                value={companyNumber}
                placeholder="회사번호"
                onChange={handleCompanyNumber}
              />
            </InputContent>
            <InputContent>
              <img src={User} alt="User" style={{ marginLeft: '0.3rem' }} />
              <InputBox
                width={21.25}
                height={3.125}
                value={loginId}
                placeholder="아이디"
                onChange={handleLoginId}
              />
            </InputContent>
            <InputContent>
              <img src={Lock} alt="Lock" style={{ marginLeft: '0.3rem' }} />
              <InputBox
                width={21.25}
                height={3.125}
                value={password}
                placeholder="비밀번호"
                onChange={handlePassword}
              />
            </InputContent>
          </InputContainer>
          <ButtonBox>로그인</ButtonBox>
        </LoginBox>
      </Background>
    </>
  );
};

export default LoginPage;
