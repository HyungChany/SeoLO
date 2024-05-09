import { Modal } from './Modal.tsx';
import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import { Button } from '../button/Button.tsx';
import React, { ChangeEvent, useState } from 'react';
import InputBox from '../inputbox/InputBox.tsx';

interface ReportCheckModalProps {
  onClose: () => void; // 모달을 닫는 함수
}

interface AccidentCheckType {
  backgroundColor: string;
  color: string;
}
const Box = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  padding: 3rem 3rem 0 3rem;
  box-sizing: border-box;
`;
const ContentBox = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: row;
  gap: 4%;
  box-sizing: border-box;
`;
const Container = styled.div`
  width: 48%;
  height: auto;
  display: flex;
  flex-direction: row;
`;
const TitleContentBox = styled.div`
  width: 50%;
  height: auto;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
`;

const Content = styled.div`
  width: auto;
  height: 2rem;
  font-size: 1.3rem;
  font-style: normal;
  font-weight: 700;
  color: ${Color.SAMSUNG_BLUE};
  display: flex;
  align-items: center;
`;

const RightContent = styled.div`
  width: auto;
  height: 2rem;
  font-size: 1rem;
  font-style: normal;
  font-weight: 700;
  color: ${Color.BLACK};
  display: flex;
  align-items: center;
`;
const ButtonBox = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
`;

const AccidentButton = styled.div<AccidentCheckType>`
  width: 5rem;
  height: 2rem;
  background-color: ${(props) => props.backgroundColor};
  display: flex;
  align-items: center;
  justify-content: center;
  color: ${(props) => props.color};
  border-radius: 8px;
  cursor: pointer;
`;
const ReportCheckModal: React.FC<ReportCheckModalProps> = ({ onClose }) => {
  const [modifyModal, setModifyModal] = useState<boolean>(false);
  const [accidentBtn, setAccidentBtn] = useState<boolean>(false);
  const [accidentText, setAccidentText] = useState<string>('');
  const [accidentPeople, setAccidentPeople] = useState<string>('');
  const leftTitle = [
    '작업자',
    '사번',
    '소속 부서',
    '직급',
    '작업장',
    '장비 명',
    '장비 번호',
  ];
  const leftContent = [
    '오해원',
    '1010315',
    '품질보증팀',
    '주임',
    '1공장 검사라인',
    'L / W - 2',
    '3578DA1423',
  ];
  const rightTitle = [
    'LOTO 목적',
    '시작 시간',
    '종료 시간',
    '사고 여부',
    '사고 유형',
    '인명 피해',
  ];
  const rightContent = [
    '정비',
    '2021.05.20 12:00',
    '2021.05.20 13:00',
    '감전 사고',
    '없음',
    '0',
  ];
  const handleInnerClick = (
    event: React.MouseEvent<HTMLDivElement, MouseEvent>,
  ) => {
    event.stopPropagation();
  };
  const handleModifyClick = () => {
    setModifyModal(!modifyModal);
  };
  const handleCloseModal = () => {
    onClose();
  };
  const handleAccidentClick = () => {
    setAccidentBtn(!accidentBtn);
  };
  const handleAccidentText = (e: ChangeEvent<HTMLInputElement>) => {
    setAccidentText(e.target.value);
  };
  const handleAccidentPeopleText = (e: ChangeEvent<HTMLInputElement>) => {
    setAccidentPeople(e.target.value);
  };
  return (
    <Modal onClick={handleInnerClick}>
      <Box>
        <ContentBox>
          <Container>
            <TitleContentBox>
              {leftTitle.map((title) => (
                <Content>{title}</Content>
              ))}
            </TitleContentBox>
            <TitleContentBox>
              {leftContent.map((content) => (
                <RightContent>{content}</RightContent>
              ))}
            </TitleContentBox>
          </Container>
          <Container>
            <TitleContentBox>
              {rightTitle.map((title) => (
                <Content>{title}</Content>
              ))}
            </TitleContentBox>
            <TitleContentBox>
              {rightContent.map((content, index) => {
                if (modifyModal && index === 3) {
                  return (
                    <>
                      {accidentBtn ? (
                        <AccidentButton
                          backgroundColor={Color.SAMSUNG_BLUE}
                          color={Color.WHITE}
                          onClick={handleAccidentClick}
                        >
                          발생
                        </AccidentButton>
                      ) : (
                        <AccidentButton
                          backgroundColor={Color.GRAY200}
                          color={Color.BLACK}
                          onClick={handleAccidentClick}
                        >
                          없음
                        </AccidentButton>
                      )}
                    </>
                  );
                } else if (modifyModal && index === 4) {
                  return (
                    <RightContent>
                      <InputBox
                        width={10}
                        height={2.3}
                        value={accidentText}
                        onChange={handleAccidentText}
                      />
                    </RightContent>
                  );
                } else if (modifyModal && index === 5) {
                  return (
                    <RightContent>
                      <InputBox
                        width={4}
                        height={2.3}
                        value={accidentPeople}
                        onChange={handleAccidentPeopleText}
                      />
                    </RightContent>
                  );
                } else {
                  return <RightContent key={content}>{content}</RightContent>;
                }
              })}
            </TitleContentBox>
          </Container>
        </ContentBox>
        <ButtonBox>
          {modifyModal ? (
            <Button
              width={5}
              height={2.5}
              $backgroundColor={Color.WHITE}
              $borderColor={Color.GRAY100}
              $borderRadius={2.5}
              $hoverBackgroundColor={Color.GRAY300}
              $hoverBorderColor={Color.GRAY100}
              onClick={handleCloseModal}
            >
              확인
            </Button>
          ) : (
            <>
              <Button
                width={5}
                height={2}
                $backgroundColor={Color.WHITE}
                $borderColor={Color.GRAY100}
                $borderRadius={2.5}
                $hoverBackgroundColor={Color.GRAY300}
                $hoverBorderColor={Color.GRAY100}
                onClick={handleModifyClick}
              >
                수정
              </Button>
              <Button
                width={5}
                height={2}
                $backgroundColor={Color.WHITE}
                $borderColor={Color.GRAY100}
                $borderRadius={2.5}
                $hoverBackgroundColor={Color.GRAY300}
                $hoverBorderColor={Color.GRAY100}
                onClick={handleCloseModal}
              >
                확인
              </Button>
            </>
          )}
        </ButtonBox>
      </Box>
    </Modal>
  );
};

export default ReportCheckModal;
