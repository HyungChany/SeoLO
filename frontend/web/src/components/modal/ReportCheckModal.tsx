import { Modal } from './Modal.tsx';
import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import { Button } from '../button/Button.tsx';
import React, { ChangeEvent, useEffect, useState } from 'react';
import InputBox from '../inputbox/InputBox.tsx';
import { detailReport, modifyReport } from '@/apis/Report.ts';
import StyledInputBox from '../inputbox/StyledInputBox.tsx';
import { useMutation, useQueryClient } from '@tanstack/react-query';

interface EquipmentData {
  reportId: number;
  machineNumber: string;
  machineName: string;
  workerNumber: string;
  workerName: string;
  tasktype: string;
  accidentType: string | null;
  victimsNum: number | null;
  taskStartDateTime: string;
  taskEndDateTime: string;
  accident: boolean;
  facilityName: string;
  workerTeam: string;
  workerTitle: string;
}
interface ReportCheckModalProps {
  onClose: () => void; // 모달을 닫는 함수
  contentIndex: number;
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
  justify-content: space-between;
  padding: 1rem 1rem 0.5rem;
  box-sizing: border-box;
`;
const ContentBox = styled.div`
  width: 100%;
  height: 90%;
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
const RightContainer = styled.div`
  width: 48%;
  height: auto;
  display: flex;
  gap: 2rem;
  flex-direction: row;
`;
const LeftTitleBox = styled.div`
  width: 40%;
  height: auto;
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;
const LeftContentBox = styled.div`
  width: auto;
  height: auto;
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;
const RightTitleBox = styled.div`
  width: auto;
  height: auto;
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;
const RightContentBox = styled.div`
  width: 60%;
  height: 101%;
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

const Content = styled.div`
  width: auto;
  height: 2rem;
  font-size: 1.3rem;
  font-style: normal;
  font-family: NYJGothicM;
  font-weight: 700;
  color: ${Color.SAMSUNG_BLUE};
  display: flex;
  align-items: center;
`;

const RightContent = styled.div`
  width: auto;
  height: 2rem;
  font-size: 20px;
  font-family: NYJGothicM;
  font-weight: 500;
  color: ${Color.BLACK};
  display: flex;
  word-wrap: break-word;
  align-items: center;
  overflow-wrap: break-word;
`;

const ButtonBox = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-top: 0.5rem;
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

const ReportCheckModal: React.FC<ReportCheckModalProps> = ({
  onClose,
  contentIndex,
}) => {
  const [modifyModal, setModifyModal] = useState<boolean>(false);
  const [accidentBtn, setAccidentBtn] = useState<boolean>(false);
  const [accidentText, setAccidentText] = useState<string>('');
  const [accidentPeople, setAccidentPeople] = useState<string>('');
  const [reportData, setReportData] = useState<EquipmentData | null>(null);
  const formatDate = (dateString: string) => {
    return dateString.replace('T', ' ').slice(0, 16); // 'T'를 공백으로 대체하고 초 이후를 잘라냄
  };
  const [accidentPeopleCount, setAccidentPeopleCount] = useState<number>(0); // 새로운 숫자 상태

  const handleAccidentPeopleCount = (e: ChangeEvent<HTMLInputElement>) => {
    setAccidentPeople(e.target.value); // 원래의 문자열 상태 업데이트
    const number = parseInt(e.target.value, 10); // 문자열을 숫자로 변환
    if (!isNaN(number)) {
      setAccidentPeopleCount(number); // 숫자로 변환된 값이 유효한 경우에만 상태 업데이트
    }
  };

  useEffect(() => {
    const report = async () => {
      try {
        const data = await detailReport(contentIndex);
        const formattedData = {
          ...data,
          taskStartDateTime: formatDate(data.taskStartDateTime),
          taskEndDateTime: formatDate(data.taskEndDateTime),
          accidentType: data.accidentType === null ? '-' : data.accidentType,
          victimsNum: data.victimsNum === null ? '-' : data.victimsNum,
        };
        setReportData(formattedData);
        console.log(data);
      } catch (e) {
        console.error(e);
      }
    };
    report();
    // console.log(reportData);
  }, [contentIndex]);
  const leftTitle = [
    '작업자',
    '사번',
    '소속 부서',
    '직급',
    '작업장',
    '장비 명',
    '장비 번호',
  ];

  const rightTitle = [
    'LOTO 목적',
    '시작 시간',
    '종료 시간',
    '사고 여부',
    '인명 피해',
    '사고 유형',
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
  const handleAccidentText = (e: ChangeEvent<HTMLTextAreaElement>) => {
    setAccidentText(e.target.value);
  };
  const handleAccidentPeopleText = (e: ChangeEvent<HTMLInputElement>) => {
    setAccidentPeople(e.target.value);
    handleAccidentPeopleCount(e);
  };
  // react query로 변환
  const queryClient = useQueryClient();
  const { mutate: modifyMutate } = useMutation({
    mutationFn: modifyReport,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['report'] });
    },
    onError: (error) => {
      console.error(error);
    },
  });
  const handleModify = () => {
    const data = {
      isAccident: accidentBtn,
      accident_type: accidentText,
      victims_num: accidentPeopleCount,
      index: contentIndex,
    };
    modifyMutate(data);
    onClose();
  };
  useEffect(() => {
    if (modifyModal && reportData) {
      setAccidentText(reportData.accidentType || '');
      setAccidentPeople(reportData.victimsNum?.toString() || '');
      setAccidentPeopleCount(reportData.victimsNum || 0);
      setAccidentBtn(reportData.accident);
    }
  }, [modifyModal, reportData]);
  return (
    <Modal onClick={handleInnerClick}>
      <Box>
        <ContentBox>
          <Container>
            <LeftTitleBox>
              {leftTitle.map((title) => (
                <Content>{title}</Content>
              ))}
            </LeftTitleBox>
            <LeftContentBox>
              {reportData && (
                <>
                  <RightContent>{reportData.workerName}</RightContent>
                  <RightContent>{reportData.workerNumber}</RightContent>
                  <RightContent>{reportData.workerTeam}</RightContent>
                  <RightContent>{reportData.workerTitle}</RightContent>
                  <RightContent>{reportData.facilityName}</RightContent>
                  <RightContent>{reportData.machineName}</RightContent>
                  <RightContent>{reportData.machineNumber}</RightContent>
                </>
              )}
            </LeftContentBox>
          </Container>
          <RightContainer>
            <RightTitleBox>
              {rightTitle.map((title) => (
                <Content>{title}</Content>
              ))}
            </RightTitleBox>
            <RightContentBox>
              {reportData && (
                <>
                  <RightContent>{reportData.tasktype}</RightContent>
                  <RightContent>{reportData.taskStartDateTime}</RightContent>
                  <RightContent>{reportData.taskEndDateTime}</RightContent>
                  <RightContent>
                    {!modifyModal ? (
                      reportData.accident ? (
                        '발생'
                      ) : (
                        '없음'
                      )
                    ) : (
                      <AccidentButton
                        backgroundColor={
                          accidentBtn ? Color.SAMSUNG_BLUE : Color.GRAY200
                        }
                        color={accidentBtn ? Color.WHITE : Color.BLACK}
                        onClick={handleAccidentClick}
                      >
                        {accidentBtn ? '발생' : '없음'}
                      </AccidentButton>
                    )}
                  </RightContent>
                  {/* <RightContent>
                    {modifyModal ? (
                      <InputBox
                        width={10}
                        height={2.3}
                        value={accidentText}
                        onChange={handleAccidentText}
                      />
                    ) : (
                      reportData.accidentType
                    )}
                  </RightContent> */}
                  <RightContent>
                    {modifyModal ? (
                      <>
                        <InputBox
                          width={4}
                          height={2.3}
                          value={accidentPeople}
                          onChange={handleAccidentPeopleText}
                        />
                        <span style={{ marginLeft: '1rem' }}>명</span>
                      </>
                    ) : (
                      reportData.victimsNum
                    )}
                  </RightContent>
                  {modifyModal ? (
                    <StyledInputBox
                      width={12.5}
                      height={4.3}
                      value={accidentText}
                      onChange={handleAccidentText}
                      maxLength={50}
                    />
                  ) : (
                    <RightContent>{reportData.accidentType}</RightContent>
                  )}
                </>
              )}
            </RightContentBox>
          </RightContainer>
        </ContentBox>
        {/* <ModifyBox>
          {modifyModal ? (
            <InputBox
              width={10}
              height={2.3}
              value={accidentText}
              onChange={handleAccidentText}
            />
          ) : null}
        </ModifyBox> */}
        <ButtonBox>
          {modifyModal ? (
            <Button
              width={5}
              height={2.5}
              fontWeight={700}
              fontSize={1.1}
              $backgroundColor={Color.WHITE}
              $borderColor={Color.GRAY100}
              $borderRadius={2.5}
              $hoverBackgroundColor={Color.GRAY200}
              $hoverBorderColor={Color.GRAY200}
              onClick={handleModify}
            >
              확인
            </Button>
          ) : (
            <>
              <Button
                width={5}
                height={2.5}
                fontWeight={700}
                fontSize={1.1}
                $backgroundColor={Color.WHITE}
                $borderColor={Color.GRAY100}
                $borderRadius={2.5}
                $hoverBackgroundColor={Color.GRAY200}
                $hoverBorderColor={Color.GRAY200}
                onClick={handleModifyClick}
              >
                수정
              </Button>
              <Button
                width={5}
                height={2.5}
                fontWeight={700}
                fontSize={1.1}
                $backgroundColor={Color.WHITE}
                $borderColor={Color.GRAY100}
                $borderRadius={2.5}
                $hoverBackgroundColor={Color.GRAY200}
                $hoverBorderColor={Color.GRAY200}
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
