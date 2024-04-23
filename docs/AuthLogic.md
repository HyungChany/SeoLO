# 인증로직 과정

## 1. 자격검정

- 사용자는 ID/PW 를 입력해 로그인하고 세션을 발급받는다.
- 잠근 자물쇠가 없는 사용자는 행동코드 ‘INIT’을 담고있는다.

1. 사용자는 휴대폰/워치를 잠겨있는 자물쇠에 태그한다.
2. 태그 과정에서 행동코드와 앱에 등록된 회사코드를 자물쇠에 전송한다.
3. 자물쇠는 코드에 따라 아래와 같은 로직을 수행한다.

### NFC 태그 페이지로 이동해서 태그하는 경우
| 경우의 수 | 잠겨진 자물쇠                                                                                                                                                                                                                                                                                                                                                                                       | 열려있는 자물쇠                                                                                                                                                                                                                                                                                                                                                                                      |
| --- |-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 해당 자물쇠를 잠근 사람 | 태그시 자물쇠 UNLOCK 인증로직-태그먼저/LOCKER(worker)/LOCKED (https://www.notion.so/LOCKER-worker-LOCKED-5d761627b82d4ee3acd62e6bd126a2c0?pvs=21)                                                                                                                                                                                                                                                           | 경우의 수 없음                                                                                                                                                                                                                                                                                                                                                                                      |
| 해당 자물쇠를 잠근 적 없는 사람 (→ 어떠한 자물쇠도 잠그지 않았다) | 태그시 정보 조회                                                                                                                            인증로직-태그먼저/NOLOCKER(WORKER)/LOCKED (https://www.notion.so/NOLOCKER-WORKER-LOCKED-90cf5615d5f047f3b6979da1425422fd?pvs=21)                                                                                                                                 | 태그시 LOTO 일지 페이지로 리다이렉트                                                                                                               인증로직-태그먼저/NOLOCKER(WORKER)/OPENED (https://www.notion.so/NOLOCKER-WORKER-OPENED-554e8cd344d445ac86517bb81e40a8a4?pvs=21)                                                                                                                                 |
| 해당 자물쇠는 잠그지 않았지만 다른 자물쇠를 잠그고 있는 사람 | 태그시 정보 조회                                                                                                                                                                                                                                                      인증로직-태그먼저/OTHERLOCKER(worker)/LOCKED (https://www.notion.so/OTHERLOCKER-worker-LOCKED-4f372b247732442785da715dced08865?pvs=21) | 태그시 2개 이상의 자물쇠를 잠굴 수 없습니다 경고                                                                                                                                                                                                                                   인증로직-태그먼저/OTHERLOCKER(worker)/OPENED (https://www.notion.so/OTHERLOCKER-worker-OPENED-3967e824005f4f52babdcf41c2921c47?pvs=21) |
| 관리자(마스터키 소지자) | 태그시 자물쇠 UNLOCK 인증로직-태그먼저/NOLOCKER(MANAGER)/LOCKED (https://www.notion.so/NOLOCKER-MANAGER-LOCKED-a4d72e49e6e14f84a634e6a2ce01fc4b?pvs=21)                                                                                                                                                                                                                                                     | 현재 고려안함 |


### 사용자가 일지작성하고 자물쇠 태그하려는 경우
|                           | 잠겨진 자물쇠                                                                                                                                                   | 열려있는 자물쇠                                                                                                                         |
|---------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------|
| 자물쇠를 잠구고 있지 않은 경우 | 태그시 자물쇠: '이미 잠겨져있는 자물쇠는 잠글 수 없습니다 오류' <br> [인증로직-일지먼저/LOCKER(WORKER)/OPENED](https://www.notion.so/LOCKER-WORKER-OPENED-4d0feb7ee5dd427a9f1e2fcf0bba831f) | 태그시 자물쇠 LOCK <br> [인증로직-일지먼저/LOCKER(WORKER)/OPENED](https://www.notion.so/LOCKER-WORKER-OPENED-4d0feb7ee5dd427a9f1e2fcf0bba831f) |
| 다른 자물쇠를 잠구고 있는 경우   | 일지 작성 불가                                                                                                                                                  | 일지 작성 불가                                                                                                                         |
| 관리자인 경우(마스터키)         | 일지 작성 불가                                                                                                                                                  | 일지 작성 불가                                                                                                                         |


### **코드**

- 상태코드
    - INIT
    - LOCKED
- 행동코드
    - CHECK
    - LOCK
    - UNLOCK
    - WRITE




### sequence diagram - 인증로직

```mermaid
sequenceDiagram
    participant B as BACK
    participant F as FRONT
    participant L as LOCK

    F->>+L: <INIT>상태코드, 외부인증토큰, 유저의 회사코드 전송
    par when Code is <INIT>
        L->>+L: 회사코드 비교
        alt is same company id
            L->>+L: 외부인증토큰 비교
            alt is NULL token
                L->>+F: UID, <WRITE> 전송

                F->>F: 일지 입력받기
                F->>B: 일지 DATA, 유저 SESSION, UID 전송
                B->>B: 일지 저장(상태 <INIT>)
                B->>B: 유저 SESSION과 매치되는 외부인증토큰 여부 확인
                alt is token exists
                    B-->>F: ERROR
                else is token none
                    B-->>B: 새 토큰 발행
                    B->>F: <LOCK>, 외부인증토큰 전송
                    F->>F: 자물쇠에 태크해주세요 화면
                end
            else is different token
                L->>F: 장비ID, <CHECK> 전송
                F->>+B: 장비ID, 유저SESSION 전송
                B->>B: 장비ID를 활용해 가장 최신 작업일지 정보 조회
                B->>B: 유저 SESSION(사원ID)를 활용해 자물쇠에 접근한 로그 기록
                B->>F: 작업정보 전송
            end
        else is different company id
            L->>+F: ERROR
        end
    end

    F->>+L: <LOCKED>상태코드, 외부인증토큰, 유저의 회사코드 전송
	par when Code is <LOCKED>
		L->>+L: 회사코드 비교
        alt is same company id
            alt is same token
                L->>F: <UNLOCK>, UID 전송  
                L->>L: 자물쇠를 열고, 외부인증토큰, 기기번호 삭제
            else is null token
                L->>F: <ALERT>
                L->>B: <ALERT> log
            end
        else is different company id
            L-->>F: ERROR
        end
	end

    par when Code is <LOCK>
        Note right of F: 열린 자물쇠에 NFC 태그하여 해당 행동코드 진행
        F->>L: <LOCK>, 외부인증토큰, 회사코드, 장비ID 전송
        L->>L: 내장 회사코드와 받은 회사코드 비교
        alt is same company id
            L-->>+F: 불일치시 ERROR
        else is different company id
            L-->>L: 일치시 외부인증토큰, 장비ID 저장
            L-->>L: 자물쇠 잠금 로직 수행
            L-->>F: <LOCKED>, UID 전송
        end

        F->>B: <LOCKED>, 유저 SESSION, UID 전송
        B->>B: 로그에서 해당 작업일지를 LOCKED로 변경 후 저장
        B->>F: [HTTP1] 200 OK
    end

    par when User is <MANAGER>
        Note right of B: 관리자는 기계를 선택 후 마스터키 버튼을 눌러 해당 기계의 잠금을 해재할 수 있다. (비밀번호 입력 필수)

        B->>B: 자격 확인
        alt is not MANAGER
            B->>F: Error
        else is MANAGER
            B->>F: 권한 정보(ROLE_ADMIN), Master Token 전송
            F->>F: NFC 태깅 화면
            F->>L: 회사코드, ROLE_ADMIN, Master Token 전송
            L->>L: 회사코드 비교
            alt is different company id
                L->>F: Error
            else is same company ID
                L->>L: Master Token 비교
                alt is different master token
                    L->>F: Error
                else is same master token
                    L->>F: <UNLOCK>. UID, 외부인증토큰, 유저session
                    L->>L: 외부인증토큰, 기기번호 삭제
                    F->>B: <UNLOCK>, UID, 외부인증토큰, 유저session
                    B->>B: 외부인증토큰과 UID를 기준으로 정보를 찾아 로그 작성
                    B->>B: 외부인증토큰 삭제
                end
            end
        end
    end
```

### ENUM LIST

LOTO 목적

- 수리
- 정비
- 청소
- 기타

작업자 상태 코드

- INIT
- LOCKED

사원 직급

- 임원
- 중간관리직
- 실무자
- 비정규직
- 그 외
- 생산직
    - 사원
    - 조장
    - 반장
    - 직장
    - 기원
- 연구직

사원 소속 (부서별)

- 인사
- 총무
- 기획
- 회계
- 연구개발
- 생산관리
- 생산기술
- 전산
- IT
- 영업
- 해외영업
- 품질관리
- 마케팅
- 생산
- 상품관리
- 생산기술

장비별 잠금장치 유형

- 전기 잠금장치
- 기동스위치 잠금장치
- 게이트밸브 잠금장치
- 볼밸브 잠금장치

회사코드 10byte

`AAA12345KR`

→ AAA : (앞 3자리) 회사 이름 이니셜 

→ 12345 : (중간 5자리) 회사분류 코드 (한국표준산업분류코드)

[한국표준산업분류코드](https://www.notion.so/a9496640aa1646a4833fa29eb9ffe626?pvs=21) 

→ KR: (맨 뒤 2자리)  

기계분류

- 공작기계 분류(중분류)는 다음을 따름

[한국공작기계산업협회-공작기계란-공작기계 종류](https://komma.org/user/industrial/type)
