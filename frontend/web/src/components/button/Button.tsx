import styled from 'styled-components';

// 타입 정의
interface ButtonProps {
  onClick: () => void;
  width: number | string;
  height: number | string;
  $backgroundColor: string;
  $borderColor: string;
  $borderRadius: number;
  $hoverBackgroundColor: string;
  $hoverBorderColor: string;
  $hoverTextColor?: string;
  children: React.ReactNode;
  fontWeight?: number | string;
  fontSize?: number | string;
}

// 스타일 정의
const ButtonContainer = styled.button<ButtonProps>`
  width: ${(props) =>
    typeof props.width === 'number' ? `${props.width}rem` : props.width};
  height: ${(props) =>
    typeof props.height === 'number' ? `${props.height}rem` : props.height};
  border-radius: ${(props) => props.$borderRadius}rem;
  border: 1px solid ${(props) => props.$borderColor};
  font-size: ${(props) => props.fontSize}rem;
  font-weight: ${(props) => props.fontWeight || 'normal'};
  padding: 0.7rem;
  align-items: center;
  justify-content: center;
  display: flex;
  cursor: pointer;
  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.2);
  background-color: ${(props) => props.$backgroundColor};
  &:hover {
    background-color: ${(props) =>
      props.$hoverBackgroundColor || props.$backgroundColor};
    border-color: ${(props) => props.$hoverBorderColor || props.$borderColor};
    color: ${(props) => props.$hoverTextColor};
  }
`;

/**
 * 사용자의 클릭 이벤트를 처리하고, 호버 시 시각적 피드백을 제공하는 버튼 컴포넌트입니다.
 * 이 컴포넌트는 사용자 상호작용을 위한 UI 요소로, 다양한 스타일 옵션을 지원합니다.
 *
 * @param props 컴포넌트 설정을 위한 프로퍼티:
 * - `onClick`: 버튼 클릭 시 호출되는 함수입니다.
 * - `width`: 버튼의 너비를 설정합니다. CSS 단위를 포함한 문자열 또는 숫자입니다.
 * - `height`: 버튼의 높이를 설정합니다. CSS 단위를 포함한 문자열 또는 숫자입니다.
 * - `$backgroundColor`: 버튼의 배경 색상입니다.
 * - `$borderColor`: 버튼의 테두리 색상입니다.
 * - `$borderRadius`: 버튼의 테두리 둥근 정도를 설정합니다 (픽셀 단위).
 * - `$hoverBackgroundColor`: 호버 시 버튼의 배경 색상입니다.
 * - `$hoverBorderColor`: 호버 시 버튼의 테두리 색상입니다.
 * - `children`: 버튼 내부에 표시될 내용입니다.
 *
 * ### 사용 예시
 *
 * 기본 버튼:
 * ```jsx
 * <Button onClick={handleClick} $borderColor="blue" $borderRadius={5} $backgroundColor="white" $hoverBackgroundColor="#f0f0f0" $hoverBorderColor="darkblue">
 *   Click Me
 * </Button>
 * ```
 *
 * 이 컴포넌트는 폼 제출, 다양한 사용자 상호작용을 위한 버튼으로 활용됩니다.
 *
 * @returns JSX.Element - 스타일이 적용된 버튼 요소를 반환합니다.
 * @author 오민상
 */

// 컴포넌트 정의
export const Button = ({
  onClick,
  width,
  height,
  $backgroundColor,
  $borderColor,
  $borderRadius,
  $hoverBackgroundColor,
  $hoverBorderColor,
  children,
  fontWeight,
  fontSize,
  $hoverTextColor,
}: ButtonProps) => (
  <ButtonContainer
    onClick={onClick}
    $backgroundColor={$backgroundColor}
    $borderColor={$borderColor}
    $borderRadius={$borderRadius}
    $hoverBackgroundColor={$hoverBackgroundColor}
    $hoverBorderColor={$hoverBorderColor}
    height={height}
    width={width}
    fontWeight={fontWeight}
    fontSize={fontSize}
    $hoverTextColor={$hoverTextColor}
  >
    {children}
  </ButtonContainer>
);
