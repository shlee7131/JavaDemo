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
### 스레드 실행 순서 제어 연습

## Performance
### 스레드 성능 최적화 연습