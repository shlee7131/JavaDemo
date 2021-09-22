# 자바 비동기 연습장
## 환경
- Windows 10
- Intellij
- Amazon Corretto JDK 11 

## Basic
### 공유 자원의 동기화를 연습
- synchronize method
  - non-static, static
- synchronize block
- Mutex, Semaphore
### 설명
- 2명의 유저가 서로 채팅을 10000번 번갈아 치는 것을 가정
- 전체 채팅의 수는 공유 자원
  - 전체 채팅의 수가 20000번이 되는지 확인
  - 스레드 차원의 일괄처리 or 타임 슬라이스 확인
- 동기화 테스트를 위해 User.java 에 14가지 케이스 설정
  - run() 메소드의 button 변수의 값을 설정하면 테스트 가능
- 각 케이스별 주석 작성
  - _"결과 + 추론 + 결론"_  

## Control
### 1. wait hands on
- SharedData 객체에 대해 각각 3개의 멀티 스레드를 활용한 쓰기 및 읽기
- 데이터의 읽기/쓰기 가능 여부를 저장
  - 스레드가 실행가능할 때까지 busy waiting(while 무한 루프)
- 접근 가능 여부를 확인 후 wait 과 notify 활용
  - wait 은 실행중인 스레드를 일시정지 상태로 만든다
    - timeout 설정 또는 notify 에 의해 실행대기 상태 복귀
  - notifyAll() 은 일시정지 상태의 모든 스레드를 실행대기 상태 복귀
    - 모니터(락)을 얻지 못하였기에 꺠어났다고해서 스레드가 바로 실행되는 것이 아님
- 참조: https://www.youtube.com/watch?v=cNPMYBlg974
### 2. 은행 입출금 시스템 mocking
- 계좌별 입금과 출금 작업은 serial 하게 처리
- 계좌별 입금과 출금 요청은 concurrent 하게 처리




## Performance
### 스레드 성능 최적화 연습