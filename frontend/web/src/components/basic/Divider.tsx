import * as Color from '@/config/color/Color.ts';

// 타입 정의
interface DividerProps {
  borderWidth?: number;
  marginHorizontal?: number;
  borderColor?: string;
}

/**
 * div와 같은 컴포넌트를 구분짓는 divider입니다.
 *
 * @returns 컴포넌트의 스타일은 props를 통해 조절 가능합니다:
 *   - borderWidth (기본값: 0.05rem): Divider의 선 두께를 설정합니다.
 *   - marginHorizontal (기본값: 1rem): 좌우 마진을 설정합니다.
 *   - borderColor (기본값: Color.GRAY300): 선의 색상을 설정합니다.
 *
 * ### 사용 예시
 *
 * ```tsx
 * <Divider />
 * <Divider borderWidth={0.1} marginHorizontal={2} borderColor="Color.GRAY300" />
 * <Divider borderWidth={0.2} marginHorizontal={3} borderColor="#Color.RED100" />
 * ```
 *
 * 각 Divider는 페이지 내 다른 섹션들을 구분하는 데 사용할 수 있습니다.
 * @author 오민상
 */

// 컴포넌트 정의
export const Divider = (props: DividerProps) => {
  return (
    <div
      style={{
        alignSelf: 'stretch',
        borderWidth: `${props.borderWidth || 0.05}rem`,
        marginLeft: `${props.marginHorizontal || 1}rem`,
        marginRight: `${props.marginHorizontal || 1}rem`,
        borderColor: props.borderColor || Color.GRAY300,
        borderStyle: 'solid',
      }}
    />
  );
};
