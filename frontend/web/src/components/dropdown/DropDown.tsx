import React, { useState } from 'react';
import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';
import Select, { ActionMeta, SingleValue } from 'react-select';

interface OptionType {
  value: string;
  label: string;
}

const DropDownBox = styled.div`
  display: flex;
  width: 19.375rem;
  height: 5rem;
  padding: 1.125rem 0.9375rem;
  justify-content: center;
  align-items: center;
  border-radius: 0.625rem;
  border: 1px solid ${Color.GRAY300};
  background: ${Color.SNOW};
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  box-sizing: border-box;
`;

const StyledSelect = styled(Select<OptionType>).attrs({
  classNamePrefix: 'react-select',
})`
  .react-select__control {
    background-color: ${Color.SNOW};
    width: 17.5rem;
    height: 100%;
    border: 1px solid ${Color.GRAY300};
    display: flex;
    font-size: 1.375rem;
    font-style: normal;
    font-weight: 700;
    line-height: normal;
    cursor: pointer;
  }
  .react-select__single-value {
    color: ${Color.BLACK}; /* 텍스트 색상 지정 */
    font-size: 1.375rem;
    font-weight: 700;
  }
  .react-select__menu {
    background-color: ${Color.WHITE};
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    font-weight: 600;
    text-align: center;
  }
  .react-select__option {
    background-color: transparent; /* option 배경색 */
    color: black; /* option 텍스트 색상 */
  }
  .react-select__option--is-selected {
    background-color: ${Color.SNOW}; /* 클릭된 option 배경색 */
    color: ${Color.GRAY300}; /* 클릭된 option 텍스트 색상 */
  }
  .react-select__option--is-focused {
    border: 1px solid #afaeb7;
    color: black; /* hover 상태의 option 텍스트 색상 */
  }
  .react-select__placeholder {
    color: ${Color.BLACK};
    font-weight: 600;
  }
`;
const Dropdown: React.FC<{
  onClick?: (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => void;
}> = ({ onClick }) => {
  const handleClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    // 클릭 이벤트 전파를 중지
    event.stopPropagation();
    // 추가적인 onClick 로직이 있다면 실행
    if (onClick) {
      onClick(event);
    }
  };
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [selectedOption, setSelectedOption] = useState<OptionType | null>(null);
  const handleOptionChange = (
    newValue: SingleValue<OptionType>,
    actionMeta: ActionMeta<OptionType>,
  ) => {
    console.log('Action: ', actionMeta.action);
    setSelectedOption(newValue);
  };
  const options: OptionType[] = [
    { value: '1공장', label: '1공장' },
    { value: '2공장', label: '2공장' },
    { value: '3공장', label: '3공장' },
    { value: '4공장', label: '4공장' },
    { value: '5공장', label: '5공장' },
    { value: '6공장', label: '6공장' },
    { value: '7공장', label: '7공장' },
    { value: '8공장', label: '8공장' },
    { value: '9공장', label: '9공장' },
    { value: '10공장', label: '10공장' },
    { value: '11공장', label: '11공장' },
  ];
  return (
    <DropDownBox onClick={handleClick}>
      {/* <DropDownContent onClick={onToggle}></DropDownContent>
       */}
      <StyledSelect
        options={options}
        onChange={handleOptionChange} // 옵션 선택 시 처리
        menuIsOpen={isOpen} // 드롭다운 메뉴의 개폐 상태
        placeholder="공장선택"
        onMenuOpen={() => setIsOpen(true)}
        onMenuClose={() => setIsOpen(false)}
        value={selectedOption}
        // getOptionLabel={(option) => option.label}
        // getOptionValue={(option) => option.value}
      />
    </DropDownBox>
  );
};

export default Dropdown;
