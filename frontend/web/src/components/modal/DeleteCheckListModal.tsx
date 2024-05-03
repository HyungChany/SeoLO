// import { deleteCheckList } from '@/apis/CheckList.ts';
// import { Modal } from './Modal.tsx';
// import { Button } from '@/components/button/Button.tsx';
// import * as Color from '@/config/color/Color.ts';
// import { useNavigate } from 'react-router-dom';
// import styled from 'styled-components';
// interface DeleteCheckListModalProps {
//   onClick?: (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => void;
// }

// const Text = styled.div`
//   width: 100%;
//   height: 100%;
//   display: flex;
//   align-items: center;
//   font-size: 2.5rem;
//   font-weight: 900;
//   justify-content: center;
//   font-family: NYJGothicEB;
// `;

// const ButtonContainer = styled.div`
//   display: flex;
//   flex-direction: row;
//   justify-content: space-evenly;
// `;

// const DeleteCheckListModal = ({ onClick }: DeleteCheckListModalProps) => {
//   const navigate = useNavigate();
//   const handleDelete = async () => {
//     try {
//       await deleteCheckList(12);
//       console.log('삭제 성공');
//       navigate('/checklist');
//     } catch (error) {
//       console.error('삭제 실패:', error);
//       alert('삭제 실패');
//     }
//   };
//   return (
//     <Modal onClick={onClick}>
//       <Text>정말로 삭제하시겠습니까?</Text>
//       <ButtonContainer>
//         <Button
//           onClick={() => {}}
//           width={5}
//           height={2}
//           $backgroundColor={Color.GRAY100}
//           $borderColor={'white'}
//           $borderRadius={0.3125}
//           $hoverBackgroundColor={'white'}
//           $hoverBorderColor={'white'}
//           children={'돌아가기'}
//         ></Button>
//         <Button
//           onClick={handleDelete}
//           width={5}
//           height={2}
//           $backgroundColor={'red'}
//           $borderColor={'white'}
//           $borderRadius={0.3125}
//           $hoverBackgroundColor={'red'}
//           $hoverBorderColor={'red'}
//           children={'삭제'}
//         ></Button>
//       </ButtonContainer>
//     </Modal>
//   );
// };

// export default DeleteCheckListModal;
