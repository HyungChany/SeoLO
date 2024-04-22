import * as FontStyle from '@/config/fontStyle/fontStyle.ts';
import styled from 'styled-components';

interface TypographyProps {
  color?: string;
  children: React.ReactNode;
}

interface FontProps extends TypographyProps {
  fontFamily: string;
  fontSize: number;
}

const StyledHeaderTypography = styled.div<FontProps>`
  font-family: ${(props) => props.fontFamily};
  font-size: ${(props) => `${props.fontSize}rem`};
  color: ${(props) => props.color || 'black'};
  line-height: ${(props) => `${props.fontSize * 1.5}rem`};
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const StyledTypography = styled.div<FontProps>`
  font-family: ${(props) => props.fontFamily};
  font-size: ${(props) => `${props.fontSize}rem`};
  color: ${(props) => props.color || 'black'};
  line-height: ${(props) => `${props.fontSize * 1.5}rem`};
`;

export const H1 = (props: TypographyProps) => {
  return (
    <StyledHeaderTypography
      fontFamily={FontStyle.F_PRIMARY_EB}
      fontSize={FontStyle.F_SIZE_XXXXL}
      color={props.color}
    >
      {props.children}
    </StyledHeaderTypography>
  );
};

export const H2 = (props: TypographyProps) => {
  return (
    <StyledHeaderTypography
      fontFamily={FontStyle.F_PRIMARY_EB}
      fontSize={FontStyle.F_SIZE_XXXL}
      color={props.color}
    >
      {props.children}
    </StyledHeaderTypography>
  );
};

export const H3 = (props: TypographyProps) => {
  return (
    <StyledHeaderTypography
      fontFamily={FontStyle.F_PRIMARY_EB}
      fontSize={FontStyle.F_SIZE_XXL}
      color={props.color}
    >
      {props.children}
    </StyledHeaderTypography>
  );
};

export const H4 = (props: TypographyProps) => {
  return (
    <StyledHeaderTypography
      fontFamily={FontStyle.F_PRIMARY_EB}
      fontSize={FontStyle.F_SIZE_XL}
      color={props.color}
    >
      {props.children}
    </StyledHeaderTypography>
  );
};

export const Body0B = (props: TypographyProps) => {
  return (
    <StyledTypography
      fontFamily={FontStyle.F_PRIMARY_B}
      fontSize={FontStyle.F_SIZE_XXXL}
      color={props.color}
    >
      {props.children}
    </StyledTypography>
  );
};

export const Body0M = (props: TypographyProps) => {
  return (
    <StyledTypography
      fontFamily={FontStyle.F_PRIMARY_M}
      fontSize={FontStyle.F_SIZE_XXXL}
      color={props.color}
    >
      {props.children}
    </StyledTypography>
  );
};

export const Body1B = (props: TypographyProps) => {
  return (
    <StyledTypography
      fontFamily={FontStyle.F_PRIMARY_B}
      fontSize={FontStyle.F_SIZE_L}
      color={props.color}
    >
      {props.children}
    </StyledTypography>
  );
};

export const Body1M = (props: TypographyProps) => {
  return (
    <StyledTypography
      fontFamily={FontStyle.F_PRIMARY_M}
      fontSize={FontStyle.F_SIZE_L}
      color={props.color}
    >
      {props.children}
    </StyledTypography>
  );
};

export const Body2B = (props: TypographyProps) => {
  return (
    <StyledTypography
      fontFamily={FontStyle.F_PRIMARY_B}
      fontSize={FontStyle.F_SIZE_M}
      color={props.color}
    >
      {props.children}
    </StyledTypography>
  );
};

export const Body2M = (props: TypographyProps) => {
  return (
    <StyledTypography
      fontFamily={FontStyle.F_PRIMARY_M}
      fontSize={FontStyle.F_SIZE_M}
      color={props.color}
    >
      {props.children}
    </StyledTypography>
  );
};

export const Body3B = (props: TypographyProps) => {
  return (
    <StyledTypography
      fontFamily={FontStyle.F_PRIMARY_B}
      fontSize={FontStyle.F_SIZE_S}
      color={props.color}
    >
      {props.children}
    </StyledTypography>
  );
};

export const Body3M = (props: TypographyProps) => {
  return (
    <StyledTypography
      fontFamily={FontStyle.F_PRIMARY_M}
      fontSize={FontStyle.F_SIZE_S}
      color={props.color}
    >
      {props.children}
    </StyledTypography>
  );
};

export const Body4B = (props: TypographyProps) => {
  return (
    <StyledTypography
      fontFamily={FontStyle.F_PRIMARY_B}
      fontSize={FontStyle.F_SIZE_XS}
      color={props.color}
    >
      {props.children}
    </StyledTypography>
  );
};

export const Body4M = (props: TypographyProps) => {
  return (
    <StyledTypography
      fontFamily={FontStyle.F_PRIMARY_M}
      fontSize={FontStyle.F_SIZE_XS}
      color={props.color}
    >
      {props.children}
    </StyledTypography>
  );
};

export const Detail0 = (props: TypographyProps) => {
  return (
    <StyledTypography
      fontFamily={FontStyle.F_PRIMARY_M}
      fontSize={FontStyle.F_SIZE_XXS}
      color={props.color}
    >
      {props.children}
    </StyledTypography>
  );
};

export const Detail1 = (props: TypographyProps) => {
  return (
    <StyledTypography
      fontFamily={FontStyle.F_PRIMARY_M}
      fontSize={FontStyle.F_SIZE_XXXS}
      color={props.color}
    >
      {props.children}
    </StyledTypography>
  );
};
