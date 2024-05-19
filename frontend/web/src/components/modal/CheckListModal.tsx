import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
// import * as Color from '@/config/color/Color.ts';
interface CheckListModalProps {
  onClick?: (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => void;
  context: string;
}
const Modal = styled.div`
  width: auto;
  min-width: 50%;
  max-width: 85%;
  height: 5rem;
  border-radius: 1.25rem;
  border: 1px solid ${Color.GRAY100};
  background-color: ${Color.WHITE};
  justify-content: center;
  align-items: center;
  display: flex;
  padding: 1.5rem;
  font-size: 2rem;
  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.25);
`;

const CheckListModal: React.FC<CheckListModalProps> = ({
  onClick,
  context,
}) => {
  return <Modal onClick={onClick}>{context}</Modal>;
};

export default CheckListModal;
