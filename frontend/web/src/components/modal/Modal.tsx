import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';

// 타입 정의
interface ModalProps {
  // $maxWidth: number;
  // $maxHeight: number;
  children: React.ReactNode;
  onClick?: (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => void;
}

// 스타일 정의
const ModalContainer = styled.div<ModalProps>`
  width: 50%;
  height: 30rem;
  border-radius: 3.125rem;
  border: 1px solid ${Color.GRAY100};
  background-color: ${Color.GRAY50};
  padding: 0.7rem;
  display: flex;
  flex-direction: column;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 10;
  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.25);
`;

/**
 * 중앙에 고정된 모달 컴포넌트입니다. 주어진 최대 너비와 높이 내에서 화면의 중앙에 모달을 표시하며,
 * 뷰포트 사이즈에 따라 반응형으로 조절됩니다.
 *
 * @param props 컴포넌트 설정을 위한 프로퍼티:
 * - `maxWidth`: 모달의 최대 너비를 지정합니다.
 * - `maxHeight`: 모달의 최대 높이를 지정합니다.
 * - `children`: 모달 내부에 표시될 자식 요소입니다.
 *
 * ### 사용 예시
 * ```jsx
 * <Modal maxWidth={500} maxHeight={400}>
 *   <p>Modal Content Here</p>
 * </Modal>
 * ```
 *
 * @returns JSX.Element - 스타일이 적용된 모달 요소를 반환합니다.
 */

// 컴포넌트 정의
export const Modal = ({ children, onClick }: ModalProps) => (
  <ModalContainer onClick={onClick}>{children}</ModalContainer>
);
