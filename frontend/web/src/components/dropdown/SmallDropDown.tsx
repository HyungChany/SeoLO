import React from 'react';
import styled from 'styled-components';
import Select, { SingleValue } from 'react-select';
import * as Color from '@/config/color/Color.ts';

interface OptionType {
  value: string;
  label: string;
}

interface DropdownProps {
  options: OptionType[];
  selectedOption: OptionType | null;
  onOptionChange: (option: OptionType) => void;
  placeholder?: string;
}

const DropDownBox = styled.div`
  display: flex;
  width: 8rem;
  height: 4.59rem;
  padding: 1.125rem 0.9375rem;
  justify-content: center;
  align-items: center;
  border-radius: 8px;
  border: 1px solid ${Color.GRAY300};
  background: ${Color.WHITE};
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  box-sizing: border-box;
`;

const StyledSelect = styled(Select<OptionType>).attrs({
  classNamePrefix: 'react-select',
})`
  .react-select__control {
    background-color: ${Color.WHITE};
    width: 7rem;
    height: 100%;
    border: 1px solid ${Color.GRAY300};
    display: flex;
    font-size: 1rem;
    font-style: normal;
    font-weight: 700;
    line-height: normal;
    cursor: pointer;
  }
  .react-select__single-value {
    color: ${Color.BLACK};
    font-size: 1rem;
    font-weight: 700;
  }
  .react-select__menu {
    background-color: ${Color.WHITE};
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    font-weight: 600;
    text-align: center;
  }
  .react-select__option {
    background-color: transparent;
    color: black;
  }
  .react-select__option--is-selected {
    background-color: ${Color.SNOW};
    color: ${Color.GRAY300};
  }
  .react-select__option--is-focused {
    border: 1px solid #afaeb7;
    color: black;
  }
  .react-select__placeholder {
    color: ${Color.BLACK};
    font-weight: 600;
  }
`;

const SmallDropdown: React.FC<DropdownProps> = ({
  options,
  selectedOption,
  onOptionChange,
  placeholder,
}) => {
  const handleOptionChange = (newValue: SingleValue<OptionType>) => {
    if (newValue) {
      onOptionChange(newValue);
    }
  };
  const handleClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    event.stopPropagation(); // 이벤트 버블링 방지
  };
  return (
    <DropDownBox onClick={handleClick}>
      <StyledSelect
        options={options}
        onChange={handleOptionChange}
        menuIsOpen={undefined} // 메뉴 개폐 상태는 내부적으로 관리되므로 undefined 처리
        placeholder={placeholder || '선택하세요'}
        value={selectedOption}
      />
    </DropDownBox>
  );
};

export default SmallDropdown;
