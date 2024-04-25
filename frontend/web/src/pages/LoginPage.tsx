// import * as Typo from '@/components/typography/Typography.tsx';
import * as Color from '@/config/color/Color.ts';
import styled from 'styled-components';

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

const InputBox = styled.div`
  width: 28.5rem;
  height: 10rem;
  background-color: #56ff6c;
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
  return (
    <>
      <Background>
        <LoginBox>
          <NameBox>
            <NameKor>서로</NameKor>
            <NameEng>SeoLO</NameEng>
          </NameBox>
          <InputBox>여기에 입력창 넣으셈</InputBox>
          <ButtonBox>로그인</ButtonBox>
        </LoginBox>
      </Background>
    </>
  );
};

export default LoginPage;
