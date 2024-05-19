## MVVM 구조를 활용한 개발

### model
애플리케이션의 데이터와 비즈니스 로직을 담당. 모델은 데이터의 저장, 검색, 수정 등을 처리하며, 데이터베이스, 웹 서비스 호출 등 백엔드와의 통신을 담당.
```
 ┃ ┣ 📂models
 ┃ ┃ ┣ 📂core                             // core logic
 ┃ ┃ ┃ ┣ 📜check_model.dart
 ┃ ┃ ┃ ┣ 📜issue_model.dart
 ┃ ┃ ┃ ┣ 📜locked_model.dart
 ┃ ┃ ┃ ┗ 📜unlock_model.dart
 ┃ ┃ ┣ 📂loto                             // loto 작업 내역 작성
 ┃ ┃ ┃ ┣ 📜checklist_model.dart
 ┃ ┃ ┃ ┣ 📜facility_model.dart
 ┃ ┃ ┃ ┣ 📜machine_model.dart
 ┃ ┃ ┃ ┗ 📜task_templates_model.dart
 ┃ ┃ ┣ 📂main                             // main screen의 news
 ┃ ┃ ┃ ┗ 📜news_model.dart
 ┃ ┃ ┗ 📂user                             // user 관리
 ┃ ┃ ┃ ┣ 📜login_model.dart
 ┃ ┃ ┃ ┣ 📜my_info_model.dart
 ┃ ┃ ┃ ┣ 📜my_tasks_model.dart
 ┃ ┃ ┃ ┣ 📜password_change_model.dart
 ┃ ┃ ┃ ┣ 📜password_check_model.dart
 ┃ ┃ ┃ ┣ 📜pin_change_model.dart
 ┃ ┃ ┃ ┗ 📜pin_login_model.dart
```
### view (screen)
사용자에게 보여지는 UI 부분을 담당.
```
┃ ┣ 📂screens
 ┃ ┃ ┣ 📂bluetooth                           // bluetooth (on/off)
 ┃ ┃ ┃ ┣ 📜bluetooth_off_screen.dart
 ┃ ┃ ┃ ┗ 📜bluetooth_screen.dart
 ┃ ┃ ┣ 📂login                               //로그인 관리 (사번, pin)
 ┃ ┃ ┃ ┣ 📜login_screen.dart
 ┃ ┃ ┃ ┗ 📜pin_login_screen.dart
 ┃ ┃ ┣ 📂lotolock                            // loto 작업 내역 작성
 ┃ ┃ ┃ ┣ 📜checklist_screen.dart
 ┃ ┃ ┃ ┣ 📜day_select_screen.dart
 ┃ ┃ ┃ ┣ 📜facility_select_screen.dart
 ┃ ┃ ┃ ┣ 📜machine_select_screen.dart
 ┃ ┃ ┃ ┣ 📜other_worklist_check_screen.dart
 ┃ ┃ ┃ ┣ 📜result_lock_screen.dart
 ┃ ┃ ┃ ┣ 📜result_unlock_screen.dart
 ┃ ┃ ┃ ┣ 📜task_template_select_screen.dart
 ┃ ┃ ┃ ┣ 📜time_select_screen.dart
 ┃ ┃ ┃ ┗ 📜worklist_check_screen.dart
 ┃ ┃ ┣ 📂loto_process                         // loto 절차
 ┃ ┃ ┃ ┗ 📜loto_process_screen.dart
 ┃ ┃ ┣ 📂main                                 // main 화면
 ┃ ┃ ┃ ┗ 📜main_screen.dart
 ┃ ┃ ┣ 📂profile                              // 프로필 화면
 ┃ ┃ ┃ ┣ 📜change_password_screen.dart
 ┃ ┃ ┃ ┣ 📜change_pin_check_screen.dart
 ┃ ┃ ┃ ┣ 📜change_pin_screen.dart
 ┃ ┃ ┃ ┣ 📜check_password_screen.dart
 ┃ ┃ ┃ ┣ 📜check_pin_screen.dart
 ┃ ┃ ┃ ┗ 📜profile_screen.dart
 ```
 ### viewmodel
  Model과 View 사이의 연결 고리 담당. View로부터 입력을 받아 Model을 업데이트하고, Model의 상태 변화를 View에 반영하기 위한 데이터를 준비
 ```
  ┃ ┣ 📂view_models    
 ┃ ┃ ┣ 📂core                                  // core logic
 ┃ ┃ ┃ ┣ 📜core_check_view_model.dart
 ┃ ┃ ┃ ┣ 📜core_issue_view_model.dart
 ┃ ┃ ┃ ┣ 📜core_locked_view_model.dart
 ┃ ┃ ┃ ┗ 📜core_unlock_view_model.dart
 ┃ ┃ ┣ 📂loto                                 // loto 작업 내역 작성
 ┃ ┃ ┃ ┣ 📜checklist_view_model.dart
 ┃ ┃ ┃ ┣ 📜facility_view_model.dart
 ┃ ┃ ┃ ┣ 📜machine_view_model.dart
 ┃ ┃ ┃ ┗ 📜task_templates_view_model.dart
 ┃ ┃ ┣ 📂main                                 // main news
 ┃ ┃ ┃ ┗ 📜news_view_model.dart
 ┃ ┃ ┗ 📂user                                 // user 관리
 ┃ ┃ ┃ ┣ 📜login_view_model.dart
 ┃ ┃ ┃ ┣ 📜logout_view_model.dart
 ┃ ┃ ┃ ┣ 📜my_info_view_model.dart
 ┃ ┃ ┃ ┣ 📜my_tasks_view_model.dart
 ┃ ┃ ┃ ┣ 📜password_change_view_model.dart
 ┃ ┃ ┃ ┣ 📜password_check_view_model.dart
 ┃ ┃ ┃ ┣ 📜pin_change_view_model.dart
 ┃ ┃ ┃ ┗ 📜pin_login_view_model.dart
 ```
 ### service (api)
 ```
  ┃ ┣ 📂services
 ┃ ┃ ┣ 📜core_service.dart                   // core logic
 ┃ ┃ ┣ 📜loto_service.dart                   // loto 작업내역
 ┃ ┃ ┣ 📜news_service.dart                   // main news
 ┃ ┃ ┗ 📜user_service.dart                   // user관리
 ```
 ### 사용된 image와 font

```
┣ 📂assets
 ┃ ┣ 📂fonts
 ┃ ┃ ┣ 📜font_bold.ttf
 ┃ ┃ ┣ 📜font_extrabold.ttf
 ┃ ┃ ┣ 📜font_light.ttf
 ┃ ┃ ┗ 📜font_medium.ttf
 ┃ ┣ 📂images
 ┃ ┃ ┣ 📜bluetooth_icon.png
 ┃ ┃ ┣ 📜clean_icon.png
 ┃ ┃ ┣ 📜clearlock.png
 ┃ ┃ ┣ 📜clearlock2.png
 ┃ ┃ ┣ 📜etc_icon.png
 ┃ ┃ ┣ 📜fail_loto.png
 ┃ ┃ ┣ 📜home_icon.png
 ┃ ┃ ┣ 📜loading_icon.gif
 ┃ ┃ ┣ 📜loading_icon.png
 ┃ ┃ ┣ 📜login.png
 ┃ ┃ ┣ 📜login_logo.png
 ┃ ┃ ┣ 📜loto_lock_icon.png
 ┃ ┃ ┣ 📜loto_process_character.png
 ┃ ┃ ┣ 📜loto_process_icon.png
 ┃ ┃ ┣ 📜maintenance_icon.png
 ┃ ┃ ┣ 📜nfc_icon.png
 ┃ ┃ ┣ 📜nfc_tag_icon.png
 ┃ ┃ ┣ 📜profile_icon.png
 ┃ ┃ ┣ 📜repair_icon.png
 ┃ ┃ ┣ 📜seolo_character.png
 ┃ ┃ ┣ 📜seolo_icon.png
 ┃ ┃ ┣ 📜success_loto.png
 ┃ ┃ ┗ 📜work_report_icon.png
 ┃ ┗ 📂splash
 ┃ ┃ ┗ 📜icon.png
 ```