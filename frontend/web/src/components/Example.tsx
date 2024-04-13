import * as Typo from '@/components/typography/Typography.tsx';
import * as Color from '@/config/color/Color.ts';
import Exam from '../../assets/icons/Exam.svg?react';
import styled from 'styled-components';

interface ContainerProps {
  width: number;
  height: number;
}

const Container = styled.div<ContainerProps>`
  width: ${(props) => props.width}px;
  height: ${(props) => props.height}px;
`;

export const Example = () => {
  return (
    <>
      <Typo.H1 color={Color.BLACK}>헤더1</Typo.H1>
      <Typo.H2 color={Color.RED500}>헤더2</Typo.H2>
      <Typo.H3 color={Color.YELLOW500}>헤더3</Typo.H3>
      <Typo.H4 color={Color.MINT200}>헤더4</Typo.H4>
      <Container height={300} width={300}>
        <Exam />
      </Container>
      <Typo.Body0B>본문0</Typo.Body0B>
      <Typo.Body0M>본문0</Typo.Body0M>
      <Typo.Body1B>본문1</Typo.Body1B>
      <Typo.Body1M>본문1</Typo.Body1M>
      <Typo.Body2M>Body2M</Typo.Body2M>
      <Typo.Body2B>Body2B</Typo.Body2B>
      <Typo.Body3B>Body3B</Typo.Body3B>
      <Typo.Body3M>Body3M</Typo.Body3M>
      <Typo.Body4B>Body4B</Typo.Body4B>
      <Typo.Body4M>Body4M</Typo.Body4M>
      <Typo.Detail0>Detail0</Typo.Detail0>
      <Typo.Detail1>Detail1</Typo.Detail1>
    </>
  );
};
