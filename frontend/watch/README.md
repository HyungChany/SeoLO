## Front-End Watch

## 개요
이 프로젝트는 SeoLo 서비스에서 웨어러블 기기 전용으로 작성됐습니다.

이 어플리케이션은 다양한 UI 컴포넌트와 프래그먼트, 데이터 모델, API 호출 서비스를 포함하여 Wear OS 장치와의 상호작용을 지원합니다.


## 주요 기능
	- 자물쇠와 BLE 통신을 통한 자물쇠 Lock / Unlock
	- 서버와 WIFI 통신을 통한 Web, App, Watch 데이터 동기화

## 파일구조
### UI 컴포넌트의 어댑터
```
📦seolo
┣ 📂adapters
┃ ┣ 📜BluetoothAdapter.kt                    // 블루투스 기기와의 상호작용을 위한 어댑터
┃ ┣ 📜BluetoothDeviceAdapter.kt              // 블루투스 디바이스 리스트를 위한 어댑터
┃ ┣ 📜CarouselStateAdapter.kt                // 캐러셀 UI의 상태 관리 어댑터
┃ ┗ 📜WheelPickerAdapter.kt                  // 휠 피커 UI 컴포넌트 어댑터
```

### UI의 각 부분을 담당하는 프래그먼트
```
📦seolo
┣ 📂fragments
┃ ┣ 📜ChecklistFragment.kt                   // 체크리스트 프래그먼트
┃ ┣ 📜LOTOInfoFragment.kt                    // LOTO(위험물 잠금/표지) 정보를 보여주는 프래그먼트
┃ ┣ 📜LOTOInfoLastFragment.kt                // LOTO 정보의 마지막 단계 프래그먼트
┃ ┣ 📜LoginPartOneFragment.kt                // 로그인 첫 번째 단계 프래그먼트
┃ ┣ 📜LoginPartTwoFragment.kt                // 로그인 두 번째 단계 프래그먼트
┃ ┣ 📜MainBluetoothFragment.kt               // 블루투스 메인 프래그먼트
┃ ┣ 📜MainChkFragment.kt                     // 메인 체크리스트 프래그먼트
┃ ┣ 📜RedoPinNumberFragment.kt               // 핀 번호 재설정 프래그먼트
┃ ┣ 📜TaskLastFragment.kt                    // 작업의 확인 프래그먼트
┃ ┗ 📜TasksFragment.kt                       // 작업 목록 프래그먼트
```

### 애플리케이션 전반에서 사용되는 전역 변수 헬퍼 클래스
```
📦seolo
┣ 📂helper                                   // 애플리케이션 전반에서 사용되는 헬퍼 클래스
┃ ┣ 📜ChecklistManager.kt                    // 체크리스트 관련 전역 변수 관리
┃ ┣ 📜LotoManager.kt                         // LOTO 관련 전역 변수 관리
┃ ┣ 📜SessionManager.kt                      // 세션 전역 변수 관리
┃ ┗ 📜TokenManager.kt                        // 토큰 전역 변수 관리
```

### 데이터 모델 정의
```
📦seolo
┣ 📂model
┃ ┣ 📜ChecklistResponse.kt                   // 체크리스트 응답 데이터 모델
┃ ┣ 📜LOTOTaskFlowResponse.kt                // LOTO 작업 흐름 응답 데이터 모델
┃ ┣ 📜PINResponse.kt                         // PIN 응답 데이터 모델
┃ ┗ 📜TokenResponse.kt                       // 토큰 응답 데이터 모델
```

### UI의 각 화면 정의
```
📦seolo
┣ 📂presentation
┃ ┣ 📜BluetoothLOTOActivity.kt               // 블루투스 LOTO 화면
┃ ┣ 📜BluetoothMainActivity.kt               // 블루투스 메인 화면
┃ ┣ 📜ChecklistActivity.kt                   // 체크리스트 화면
┃ ┣ 📜DatePickerActivity.kt                  // 날짜 선택 화면
┃ ┣ 📜EquipmentActivity.kt                   // 장비 관련 화면
┃ ┣ 📜FacilityActivity.kt                    // 시설 관련 화면
┃ ┣ 📜LOTOInfoActivity.kt                    // LOTO 정보 화면
┃ ┣ 📜LockCompleteActivity.kt                // 잠금 완료 화면
┃ ┣ 📜LoginActivity.kt                       // 로그인 화면
┃ ┣ 📜MainActivity.kt                        // 메인 화면
┃ ┣ 📜PinNumberActivity.kt                   // 핀 번호 입력 화면
┃ ┣ 📜SplashActivity.kt                      // 스플래시 화면 화면
┃ ┣ 📜TasksActivity.kt                       // 작업 목록 화면
┃ ┣ 📜TimePickerActivity.kt                  // 시간 선택 화면
┃ ┗ 📜UnLockCompleteActivity.kt              // 잠금 해제 완료 화면
```

### API 호출 담당
```
📦seolo
┗ 📂services
┃ ┣ 📜CoreLogicService.kt                    // Core Logic API 호출 로직
┃ ┣ 📜LOTOWorkFlowService.kt                 // LOTO 작업 흐름 처리 API 호출 로직
┃ ┣ 📜LoginService.kt                        // 로그인 관련 API 호출 로직
┃ ┗ 📜RetrofitClient.kt                      // Retrofit 클라이언트 설정
```


## Dependencies
프로젝트에서 사용된 주요 라이브러리와 플러그인은 다음과 같습니다:
```
`com.google.android.gms:play-services-wearable:18.1.0`
`androidx.compose:compose-bom:2023.08.00`
`androidx.compose.ui:ui`
`androidx.compose.ui:ui-tooling-preview`
`androidx.wear.compose:compose-material:1.2.1`
`androidx.wear.compose:compose-foundation:1.2.1`
`androidx.activity:activity-compose:1.7.2`
`androidx.core:core-splashscreen:1.0.1`
`androidx.constraintlayout:constraintlayout:2.1.4`
`androidx.appcompat:appcompat:1.6.1`
`androidx.viewpager2:viewpager2:1.0.0`
`com.google.android.material:material:1.11.0`
`androidx.security:security-crypto:1.0.0`
`com.github.bumptech.glide:glide:4.12.0`
`com.squareup.retrofit2:retrofit:2.9.0`
`com.google.code.gson:gson:2.8.6`
`com.squareup.retrofit2:converter-gson:2.9.0`
`com.squareup.okhttp3:okhttp:4.9.0`
`com.squareup.okhttp3:logging-interceptor:4.9.0`
```