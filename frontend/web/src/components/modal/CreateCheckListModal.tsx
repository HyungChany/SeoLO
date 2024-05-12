import styled from 'styled-components';
import { Button } from '../button/Button.tsx';
import * as Color from '@/config/color/Color.ts';
import InputBox from '../inputbox/InputBox.tsx';
import { ChangeEvent, useState } from 'react';
import { postCheckList } from '@/apis/CheckList.ts';

interface CheckListModalProps {
  onClick?: (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => void;
  onClose: () => void;
}
const Box = styled.div`
  width: 60%;
  height: 13rem;
  display: flex;
  flex-direction: column;
  border: 1px solid ${Color.GRAY100};
  background-color: ${Color.GRAY50};
  border-radius: 1.25rem;
  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.25);
`;
const ButtonBox = styled.div`
  width: 100%;
  height: 3rem;
  display: flex;
  justify-content: space-between;
  box-sizing: border-box;
  padding: 0 2rem;
  margin: 1.7rem 0 2.3rem 0;
  align-items: center;
`;
const Title = styled.div`
  width: auto;
  height: auto;
  display: flex;
  align-items: center;
  font-size: 1.8rem;
`;
const Input = styled.div`
  width: auto;
  height: auto;
  display: flex;
  justify-content: center;
  align-items: center;
`;
const CreateCheckListModal = ({ onClose, onClick }: CheckListModalProps) => {
  const [checklist, setChecklist] = useState<string>('');
  const handleChecklist = (e: ChangeEvent<HTMLInputElement>) => {
    setChecklist(e.target.value);
  };

  const handleSubmit = () => {
    if (checklist) {
      const fetchData = async () => {
        const checklistData = { context: checklist };
        await postCheckList(checklistData);
        onClose();
        window.location.reload();
      };
      fetchData();
    }
  };
  return (
    <Box onClick={onClick}>
      <ButtonBox>
        <Title>체크리스트 추가</Title>
        <Button
          width={5.5}
          height={2.8}
          $backgroundColor={Color.WHITE}
          $borderColor={Color.GRAY200}
          $borderRadius={2.5}
          $hoverBackgroundColor={Color.BLUE100}
          $hoverBorderColor={Color.BLUE100}
          fontSize={1.2}
          fontWeight={900}
          onClick={handleSubmit}
        >
          확인
        </Button>
      </ButtonBox>
      <Input>
        <InputBox
          width={50}
          height={3.5}
          value={checklist}
          onChange={handleChecklist}
          placeholder="체크리스트를 입력하세요"
        />
      </Input>
    </Box>
  );
};

export default CreateCheckListModal;
