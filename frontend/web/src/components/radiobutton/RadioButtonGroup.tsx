// import styled from 'styled-components';
// import RadioButton from './radiobutton.tsx';

// interface RadioButtonOption {
//   label: string;
//   value: string;
// }

// interface RadioButtonGroupProps {
//   options: RadioButtonOption[];
//   value: string;
//   onChange: (value: string) => void;
// }

// const RadioButtonGroupContainer = styled.div`
//   display: flex;
//   flex-direction: row;
// `;

// const RadioButtonGroup = (props: RadioButtonGroupProps) => {
//   const handleRadioButtonChange = (selectedValue: string) => {
//     props.onChange(selectedValue);
//   };

//   return (
//     <RadioButtonGroupContainer>
//       {props.options.map((option) => (
//         <RadioButton
//           key={option.value}
//           label={option.label}
//           value={option.value}
//           checked={option.value === props.value}
//           onChange={handleRadioButtonChange}
//         />
//       ))}
//     </RadioButtonGroupContainer>
//   );
// };

// export default RadioButtonGroup;
