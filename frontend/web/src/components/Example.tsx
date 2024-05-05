import * as Typo from '@/components/typography/Typography.tsx';
import * as Color from '@/config/color/Color.ts';
import Exam from '@/../assets/icons/Exam.svg?react';
import styled from 'styled-components';
import { Divider } from '@/components/basic/Divider.tsx';
import { Spacer } from '@/components/basic/Spacer.tsx';
import { Button } from '@/components/button/Button.tsx';
import { Menu } from '@/components/menu/Menu.tsx';
import CheckList from '@/../assets/icons/CheckList.svg?react';
// import { Modal } from '@/components/modal/Modal.tsx';
import Footer from '@/components/footer/Footer.tsx';
import Navigation from '@/components/navigation/Navigation.tsx';
import Card from '@/components/card/Card.tsx';
import InputBox from '@/components/inputbox/InputBox.tsx';
import { ChangeEvent, useState } from 'react';
import CustomRadioButton from '@/components/radiobutton/RadioButton.tsx';

// import Dropdown from '@/components/dropdown/DropDown.tsx';

interface ContainerProps {
  width: number;
  height: number;
}

const Container = styled.div<ContainerProps>`
  width: ${(props) => props.width}px;
  height: ${(props) => props.height}px;
`;
const Handle = () => {};

export const Example = () => {
  const [content, setContent] = useState<string>('');
  const handleContent = (e: ChangeEvent<HTMLInputElement>) => {
    setContent(e.target.value);
  };
  // const [selectedValue, setSelectedValue] = useState('');
  // const handleRadioChange = (value: string) => {
  //   setSelectedValue(value);
  // };
  const [activeIndex, setActiveIndex] = useState(0);
  const radioData = [
    {
      content: '발생',
      event: () => setActiveIndex(0),
      active: activeIndex === 0,
    },
    {
      content: '없음',
      event: () => setActiveIndex(1),
      active: activeIndex === 1,
    },
  ];
  return (
    <>
      <Navigation />
      <Card width={10} height={10} onClick={Handle}>
        gdgdgd
      </Card>
      <InputBox
        width={10}
        height={3}
        onChange={handleContent}
        value={content}
        placeholder="테스트지롱~"
      ></InputBox>
      <div>
        <CustomRadioButton data={radioData} width={10} />
      </div>
      <Typo.H1 color={Color.BLACK}>헤더1</Typo.H1>
      <Typo.H2 color={Color.RED500}>헤더2</Typo.H2>
      <Typo.H3 color={Color.YELLOW500}>헤더3</Typo.H3>
      <Typo.H4 color={Color.MINT200}>헤더4</Typo.H4>
      <Container height={10} width={10}>
        <Exam />
      </Container>
      <Typo.Body0B>본문0</Typo.Body0B>
      <Typo.Body0M>본문0</Typo.Body0M>
      <Spacer space={'2rem'} />
      <Typo.Body1B>본문1</Typo.Body1B>
      <Typo.Body1M>본문1</Typo.Body1M>
      <Spacer space={'20px'} />
      <Typo.Body2M>Body2M</Typo.Body2M>
      <Typo.Body2B>Body2B</Typo.Body2B>
      <Typo.Body3B>Body3B</Typo.Body3B>
      <Typo.Body3M>Body3M</Typo.Body3M>
      <Typo.Body4B>Body4B</Typo.Body4B>
      <Typo.Body4M>Body4M</Typo.Body4M>
      <Typo.Detail0>Detail0</Typo.Detail0>
      <Typo.Detail1>Detail1</Typo.Detail1>
      <Divider
        marginHorizontal={10}
        borderColor={Color.RED100}
        borderWidth={0.2}
      />
      <Spacer space={'20px'} />
      <Button
        onClick={() => console.log('클릭')}
        height={3}
        width={10}
        $backgroundColor={Color.GRAY200}
        $borderColor={Color.SNOW}
        $borderRadius={10}
        $hoverBackgroundColor={Color.GRAY400}
        $hoverBorderColor={Color.SNOW}
      >
        버튼입니다.
      </Button>
      <Menu onClick={() => console.log('클릭')} width={10} $enterSize={1}>
        <CheckList width={50} />
        <Spacer space={'20px'} horizontal={true} />
        <Typo.Body1B color={Color.ONYX}>새 작업장 추가</Typo.Body1B>
      </Menu>
      {/* <Modal $maxHeight={30} $maxWidth={40}>
        <Typo.Body1B color={Color.ONYX}>모달입니다.</Typo.Body1B>
      </Modal> */}

      <Footer />
    </>
  );
};
