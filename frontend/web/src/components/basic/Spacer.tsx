// 타입 정의
interface SpacerProps {
  space: string;
  horizontal?: boolean;
}

/**
 * div의 margin을 선언한 컴포넌트입니다. 좀 더 편한 space를 위합니다.
 * 이 컴포넌트는 주로 레이아웃 내에서 요소 간의 간격을 조절할 때 사용됩니다.
 *
 * @param props 컴포넌트 설정을 위한 프로퍼티:
 * - `space`: 마진의 크기를 설정합니다. CSS 단위를 포함한 문자열로 지정하세요 (예: "1rem", "20px").
 * - `horizontalDirection`: `true`일 경우, 좌우 마진(`marginLeft`)을 적용하고, `false` 또는 생략 시 상단 마진(`marginTop`)을 적용합니다.
 *
 * ### 사용 예시
 *
 * 수직 공간 추가:
 * ```jsx
 * <Spacer space="2rem" />
 * ```
 *
 * 수평 공간 추가:
 * ```jsx
 * <Spacer space="50px" horizontal={true} />
 * ```
 *
 * 각 Spacer는 페이지 내 다른 섹션들을 구분하거나, 요소 간의 공간을 추가하는 데 유용합니다.
 *
 * @returns JSX.Element - 설정된 마진을 갖는 div 요소를 반환합니다.
 * @author 오민상
 */

// 컴포넌트 정의
export const Spacer = (props: SpacerProps) => {
  if (props.horizontal) {
    return <div style={{ marginLeft: props.space }} />;
  }

  return <div style={{ marginTop: props.space }} />;
};
