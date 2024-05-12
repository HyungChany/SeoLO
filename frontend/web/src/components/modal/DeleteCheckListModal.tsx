import { deleteCheckList } from '@/apis/CheckList.ts';
import { Button } from '@/components/button/Button.tsx';
import * as Color from '@/config/color/Color.ts';
import styled from 'styled-components';
interface DeleteCheckListModalProps {
  onClick?: (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => void;
  checklistId: number;
  onClose: () => void;
}

const Text = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  font-size: 2rem;
  font-weight: 900;
  justify-content: center;
  font-family: NYJGothicEB;
`;

const ButtonContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
`;
const Box = styled.div`
  width: auto;
  min-width: 50%;
  max-width: 85%;
  height: auto;
  border-radius: 1.25rem;
  border: 1px solid ${Color.GRAY100};
  background-color: ${Color.WHITE};
  justify-content: center;
  align-items: center;
  display: flex;
  padding: 1.5rem;
  font-size: 1.6rem;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  gap: 2rem;
  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.25);
`;
const DeleteCheckListModal = ({
  onClick,
  checklistId,
  onClose,
}: DeleteCheckListModalProps) => {
  const handleDelete = async () => {
    try {
      await deleteCheckList(checklistId);
      onClose();
      window.location.reload();
    } catch (error) {
      console.error('삭제 실패:', error);
      alert('삭제 실패');
    }
  };

  return (
    <Box onClick={onClick}>
      <Text>정말로 삭제하시겠습니까?</Text>
      <ButtonContainer>
        <Button
          onClick={handleDelete}
          width={8}
          height={3}
          $backgroundColor={Color.RED}
          $borderColor={Color.RED}
          $borderRadius={1.25}
          $hoverBackgroundColor={Color.RED1}
          $hoverBorderColor={Color.RED1}
          children={'삭제'}
          fontSize={1.2}
          fontWeight={900}
        ></Button>
      </ButtonContainer>
    </Box>
  );
};

export default DeleteCheckListModal;
