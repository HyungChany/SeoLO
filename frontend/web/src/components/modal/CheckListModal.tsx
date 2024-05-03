import { Modal } from './Modal.tsx';
// import * as Color from '@/config/color/Color.ts';
interface CheckListModalProps {
  onClick?: (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => void;
}

const CheckListModal = ({ onClick }: CheckListModalProps) => {
  return (
    <Modal onClick={onClick}>
      <div>'모달입니다'</div>
    </Modal>
  );
};

export default CheckListModal;
