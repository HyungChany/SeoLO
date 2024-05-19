# Front-End Web

## 파일구조

### 이미지, svg 파일
```
📦assets
┣ 📂icons
┃ ┣ 📜Arrow.svg
┃ ┣ 📜Check.svg
┃ ┣ 📜CheckList.svg
┃ ┣ 📜Company.svg
┃ ┣ 📜Enter.svg
┃ ┣ 📜Equipment.svg
┃ ┣ 📜Exam.svg
┃ ┣ 📜Excel.svg
┃ ┣ 📜Factory.svg
┃ ┣ 📜Id.svg
┃ ┣ 📜ListModify.svg
┃ ┣ 📜Lock.svg
┃ ┣ 📜NonCheck.svg
┃ ┣ 📜Position.svg
┃ ┗ 📜Worker.svg
┗ 📂images
┃ ┣ 📜Logout.png
┃ ┣ 📜equipment.png
┃ ┣ 📜people.png
┃ ┗ 📜workplace.png
```
### source 파일

```📦src
┣ 📂apis // api 호출 로직
┃ ┣ 📜Base.ts                                             // baseURL 설정
┃ ┣ 📜CheckList.ts                                        // 체크리스트 페이지에서 쓴 api 호출 모음
┃ ┣ 📜Employee.ts                                         // 임직원 등록 페이지에서 쓴 api 호출 모음
┃ ┣ 📜Facilities.ts                                       // 회사 내 등록된 공장 호출 api 모음
┃ ┣ 📜Lock.ts                                             // 자물쇠 상태 확인 api 호출
┃ ┣ 📜Login.ts                                            // 로그인 로직 api 호출 모음
┃ ┣ 📜Machine.ts                                          // 장비 등록 및 수정, 조회에 필요한 api 호출 모음
┃ ┣ 📜Main.ts                                             // 메인페이지에서 호출하는 api 모음
┃ ┗ 📜Report.ts                                           // 보고서 조회 페이지에서 호출하는 api 모음       
┣ 📂components // 컴포넌트 저장소
┃ ┣ 📂basic
┃ ┃ ┣ 📜Divider.tsx                                       // 컴포넌트를 구분짓는 컴포넌트
┃ ┃ ┗ 📜Spacer.tsx                                        // 공간을 나누는 컴포넌트  
 ┃ ┣ 📂button
┃ ┃ ┗ 📜Button.tsx                                        // 커스텀 버튼 컴포넌트
┃ ┣ 📂card
┃ ┃ ┗ 📜Card.tsx                                          // 사각형 내에 여러 요소들을 모여 랜더링 시키는 컴포넌트
┃ ┣ 📂dropdown
┃ ┃ ┣ 📜DropDown.tsx                                      // 드롭다운 컴포넌트
┃ ┃ ┗ 📜SmallDropDown.tsx
┃ ┣ 📂footer
┃ ┃ ┗ 📜Footer.tsx                                         
┃ ┣ 📂inputbox
┃ ┃ ┣ 📜InputBox.tsx                                     
┃ ┃ ┗ 📜StyledInputBox.tsx                                // 커스텀 textarea 컴포넌트
┃ ┣ 📂leaflet
┃ ┃ ┗ 📜Leafet.tsx                                        // 화면에 찍는 마커 컴포넌트
┃ ┣ 📂menu
┃ ┃ ┗ 📜Menu.tsx         
┃ ┣ 📂modal
┃ ┃ ┣ 📜CheckListModal.tsx                                // 체크리스트 화면에서 띄워지는 모달 
┃ ┃ ┣ 📜CreateCheckListModal.tsx                          // 체크리스트 생성 모달  
┃ ┃ ┣ 📜DeleteCheckListModal.tsx                          // 체크리스트 삭제 확인 모달
┃ ┃ ┣ 📜EmployeeModal.tsx                                 // 등록 임직원 확인 모달
┃ ┃ ┣ 📜MachineModal.tsx                                  // 등록된 장비 확인 모달
┃ ┃ ┣ 📜Modal.tsx                                         // 모달의 형태를 지정해주는 컴포넌트
┃ ┃ ┣ 📜NotificationModal.tsx                             // 실시간 알림 모달  
┃ ┃ ┗ 📜ReportCheckModal.tsx                              // 보고서 확인 모달
┃ ┣ 📂navigation
┃ ┃ ┗ 📜Navigation.tsx                                    // 네비게이션 바 컴포넌트
┃ ┣ 📂radiobutton
┃ ┃ ┗ 📜RadioButton.tsx
┃ ┣ 📂routesetting
┃ ┃ ┗ 📜PrivateRoute.tsx                                  // route 설정
┃ ┗ 📂typography
┃ ┃ ┗ 📜Typography.tsx                                    // 폰트 사이즈, 두께 별로 모아놓은 컴포넌트
┣ 📂config // 글로벌 폰트 설정, 색상 설정
┃ ┣ 📂color
┃ ┃ ┗ 📜Color.ts
┃ ┗ 📂fontStyle
┃ ┃ ┗ 📜fontStyle.ts
┣ 📂font // 폰트 모음
┃ ┣ 📜NYJGothicB.ttf
┃ ┣ 📜NYJGothicEB.ttf
┃ ┣ 📜NYJGothicM.ttf
┃ ┣ 📜esamanru Bold.ttf
┃ ┣ 📜esamanru Light.ttf
┃ ┣ 📜esamanru Medium.ttf
┃ ┗ 📜fonts.css
┣ 📂hook // 커스텀 훅 모음
┃ ┗ 📜useSSE.tsx                                          // SSE를 위한 커스텀 훅
┣ 📂pages // 페이지 모음
┃ ┣ 📜CheckListPage.tsx
┃ ┣ 📜CurrentLOTO.tsx
┃ ┣ 📜Employee.tsx
┃ ┣ 📜Information.tsx
┃ ┣ 📜LoginPage.tsx
┃ ┣ 📜Machine.tsx
┃ ┣ 📜MainPage.tsx
┃ ┗ 📜Report.tsx
┣ 📂recoil // 상태관리 파일 모음
┃ ┣ 📜DropdownState.tsx                                   // 드롭다운 상태 관리
┃ ┗ 📜sseState.tsx                                        // SSE 커스텀 훅에 사용하는 상태 관리
┣ 📂router
┃ ┗ 📜Router.tsx                                          // Router 모음
┣ 📂types
┃ ┗ 📜event-source-polyfill.d.ts  
 ┣ 📜App.tsx
┣ 📜main.tsx
┗ 📜vite-env.d.ts
```

### 사용 라이브러리

- leaflet
- dayjs
- react-table
- react-select
- react-json-to-csv
- react-datepicker
- react-table-ui
- recoil
- axiosㄴ
- mui/x-date-pickers
- react-table-ui
