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
  font-size: 1.6rem;
`;

const CheckListModal = ({ onClick, context }: CheckListModalProps) => {
  return <Modal onClick={onClick}>{context}</Modal>;
};

export default CheckListModal;
