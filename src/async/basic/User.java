package async.basic;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*** 스레드 활용 예제
 1. 공유 자원 동기화 테스트
 ***/
public class User implements Runnable{

    private String name;
    private int chatCount;

    // 클래스 변수  -> 공유 자원
    private static Integer totalChatCount;
    private static int classCall = 0;

    // 자원 락
    private static Lock lock = new ReentrantLock();                    // Mutex
    private static Semaphore semaphore = new Semaphore(2);      // Semaphore, lock 의 최대 개수를 설정

    public User(String name) {
        this.name = name;
        this.chatCount = 0;
        this.totalChatCount = 0;
    }

    @Override
    public void run() {

        int button = 14;

        printTestName(button,this.name);
        switch(button){
            case 1:
                /*** 결과 ***/
                // 20000 보장 X , 두 스레드의 totalChatCount 의 값이 다름
                plainSyncMethod();
                break;
            case 2:
                /*** 결과 ***/
                // 최종 totalChatCount 가 20000 이 나옴
                staticSyncMethod(this.name);
                break;
            case 3:
                /*** 결과 ***/
                // 두 스레드 모두 10000 번 순회, totalChatCount 20000 보장 X
                plainSyncMethodWithWaitAndNotify();
                break;
            case 4:
                /*** 결과 ***/
                // 일반 static 처리와 같음
                staticSyncMethodWithWaitAndNotify(this.name);
                break;
            case 5:
                /*** 결과 ***/
                // Block 이 없는 경우와 동일
                plainSyncWithWaitAndNotifyWithSyncBlock();
                break;
            case 6:
                /*** 결과 ***/
                // 최종 값은 20000, 하지만 중간 값이 10000 을 넘어간다
                mutexWithoutSyncMethod();
                break;
            case 7:
                /*** 결과 ***/
                // 위와 같음
                mutexWithSyncMethod();
                break;
            case 8:
                /*** 결과 ***/
                // 20000 이 나오지 못함
                semaphoreWithoutSyncMethodAndMakePermitLeft();
                break;
            case 9:
                /*** 결과 ***/
                // 의도한 결과 나옴, 중간값은 10000 을 넘어감
                semaphoreWithoutSyncMethodAndMakePermitNotLeft();
                break;
            case 10:
                /*** 결과 ***/
                // Deadlock 발생 무한 대기 상태
                semaphoreWithoutSyncMethodWithMultipleAcquireWithPermitLeft();
                break;
            case 11:
                /*** 결과 ***/
                // 일반 permit left 와 같음
                semaphoreWithoutSyncMethodWithMultipleAcquireAndReleaseWithPermitLeft();
                break;
            case 12:
                /*** 결과 ***/
                // Permit Not Left 와 같음
                semaphoreWithoutSyncMethodWithMultipleAcquireAndReleaseWithPermitNotLeft();
                break;
            case 13:
                /*** 결과 ***/
                // 하나는 20000 하나는 10000 넘음
                semaphoreWithSyncMethod();
                break;
            case 14:
                /*** 결과 ***/
                // 하나는 20000 하나는 10000, 스레드마다 일괄 처리
                semaphoreWithoutSyncMethodWithSyncBlock();
                break;
        }
        printState();
    }



    // 인스턴스의 chatCount 는 제대로 입력
    // 의도 : 한 번에 하나의 스레드만 이 메소드를 사용하도록
    public synchronized void plainSyncMethod(){
        for(int i = 1; i <= 1000_0; i++){
            chatCount += 1;                 // 객체당 10000이 나온다 -> for loop 은 정확히 각 객체당 의도한 횟수만큼 동작하는 것 확인 가능

            totalChatCount += 1;            // 공유 자원으로서 순서대로 값이 10000, 20000 보장 X

            /*** 결과 ***/
            // 20000 보장 X , 두 스레드의 totalChatCount 의 값이 다름


            /*** 추론 ***/
            // 이 메소드가 static 이 아니기 때문에 각 객체에 의존
                // 객체 차원에서 for loop 은 순서대로 동작
                // 스레드 관점으로는 계속해서 Context Switching 발생
            // 이때 totalChatCount 는 클래스에 속한다
            // 즉, totalChatCount 에 동시 접근 발생
                // 값이 한 번만 올라가는 현상 발생


            /*** 결론 ***/
            // 의도한 것과는 다른 결과 발생
            // 다중 스레드에서 객체에 종속된 synchronized 메소드를 단순하게 사용하는 것은 의미가 없다.

            System.out.println(name + " call " + i + " times");  // 출력해보면 A 와 B 객체가 번갈아 가며 출력되는 거 확인 가능
        }
    }

    // static 메소드 -> 클래스 메소드 -> 객체들이 공유
    public synchronized static void staticSyncMethod(String name){
        for(int i = 1 ; i <= 1000_0; i++){
            totalChatCount += 1;    // 스레드 차원에서 10000 단위로 일괄 처리

            /*** 결과 ***/
            // 최종 totalChatCount 가 20000 이 나옴
            // A,B 의 실행순서가 바뀔 때가 있음
            // 스레드별 일괄 처리 진행
            // 정말 간혹 첫 스레드가 실행이 종료된 순간 10000 보다 살짝 큰 경우 있음

            /*** 추론 ***/
            // static 메소드이기 때문에 모든 객체들이 이 메소드를 공유
            // 이 메소드는 한 번에 하나의 스레드만 접근 가능
                // 하지만 어떤 스레드의 메소드가 먼저 호출될 지는 JVM Scheduler 의 몫
                    // 그래서 A 일때 20000 , B 에서 10000 이 되는 경우 존재
            // 스레드간 switching 이 일어나는 순간에 동시 접근이 발생하는 경우가 있는 듯


            /*** 결론 ***/
            // totalChatCount 은 의도한 20000 의 값이 나옴
                // 하지만 static 이기 때문에 인스턴스 멤버들을 파라미터로 받아야 함
            // 게다가, Switching 이 되는 순간
            // Serial 하게 처리해야 하는 작업에서는 그냥 쓰면 안됨
                // Thread 처리 순서를 제어해야 함
                    // join(), wait(), notify() 등
            // 최종 결과가 아니라 중간 과정들에서 나온 결과도 중요하다면 100% 안전 보장 어려울 듯

             System.out.println(name + " call " + i + " times");  // 출력해보면 객체 차원으로 일괄 처리되었음을 확인 가능
        }
    }

    // 두 스레드가 공유하는 객체에 대한 lock 제어
    // notifyAll()  : 일시 정지 상태의 모든 스레드를 실행 대기 상태로 전환
    // wait()       : 스레드를 일시 정지(실행 대기 큐에서 빠짐) -> 별도의 공간에 임시 저장, 호출하는 객체의 모니터를 가지고 있는 경우에만 정지
    public synchronized void plainSyncMethodWithWaitAndNotify(){
        notifyAll();
        for(int i = 1; i <= 1000_0; i++){
            chatCount += 1;
            totalChatCount += 1;
            /*** 결과 ***/
            // 두 스레드 모두 10000 번 순회
                // wait 시간 조정 안하면 마지막에 두 스레드가 wait 상태에 빠짐
            // wait 시간 조건에 따라 20000 이 나옴 그러나 100% 보장 X

            /*** 추론 ***/
            // synchronized 메소드가 각 객체에 종속된 메소드
                // 각 객체에서 별도로 이 메소드가 실행
                // wait 과 notifyAll 이 상호 연계되는 스레드가 없음
                // 결국 현 스레드 자기 자신을 wait 상태로 조정
            // 스레드는 일시정지 -> 실행 대기 -> 실행 상태로 제어
                // 이 과정에서 wait 의 충분히 길지 못한 경우
                    // 두 스레드가 동시 접근하는 경우 발생 가능
                // notify 하였다고 해서 바로 실행이 되는 것이 아니기 때문


            /*** 결론 ***/
            //  notify 와 wait 사용 경우
                // 공유 객체에 대한 주의
                // wait 시간 주의

            System.out.println(name + " call " + i + " times");  // 출력해보면 A 와 B 객체가 번갈아 가며 출력되는 거 확인 가능
        }
        try {
            wait(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // static 메소드 -> 클래스 메소드 -> 객체들이 공유
    public synchronized static void staticSyncMethodWithWaitAndNotify(String name) {
        for(int i = 1 ; i <= 1000_0; i++){
            totalChatCount += 1;    // 스레드 차원에서 10000 단위로 일괄 처리
            /*** 결과 ***/
            // 일반 static 처리와 같음

            /*** 추론 ***/
            // 처음부터 일괄처리 방식
                // 스레드가 끝나게 해주기만 하면 된다.

            /*** 결론 ***/
            // wait 을 1 만 줘도 가능
            System.out.println(name + " call " + i + " times");  // 출력해보면 객체 차원으로 일괄 처리되었음을 확인 가능
        }
        User.class.notifyAll();
        try {
            User.class.wait(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void plainSyncWithWaitAndNotifyWithSyncBlock(){
        // 공유 객체 totalChatCount 지정
        synchronized (totalChatCount) {
            notifyAll();
            for(int i = 1; i <= 1000_0; i++){
                chatCount += 1;
                totalChatCount += 1;
                /*** 결과 ***/
                // Block 이 없는 경우와 동일

                /*** 추론 ***/
                // 동시 접근에 의해 값 덮어쓰기가 발생
                    // 결국 wait 상태에서 notify 가 되어 ready 가 되고 run 으로 이어지는 과정
                    // 각 스레드가 run -> wait 과 ready -> run 되는 과정이 겹치는 경우 발생

                /*** 결론 ***/
                // 이 예제에서는 100% 보장 불가
                // wait 과 notifyAll 의 위치를 바꾸면 원하는 결과 얻을 수 있다 => 위치 바꿔서 해보기 권장
                    // wait 에 의해 반드시 둘 중 하나가 lock 을 가진 상태에서 진행하기 때문
                    // Serial 한 작업일 경우는 지양해야 한다.

                System.out.println(name + " call " + i + " times");  // 출력해보면 A 와 B 객체가 번갈아 가며 출력되는 거 확인 가능
            }
            try {
                wait(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


    // 객체에 종속된 메소드
    // 임계 영역을 설정하였지만
    public void mutexWithoutSyncMethod(){
        for(int i = 1 ; i <= 1000_0; i++){
            chatCount += 1;

            // 임계 영역 설정
            lock.lock();
            totalChatCount += 1;        // 한 번에 하나의 스레드만 들어올 수 있음
            lock.unlock();
            /*** 결과 ***/
            // 최종 값은 20000, 하지만 중간 값이 10000 을 넘어간다
                // 결과적으로 임계 영역에 20000 번 접근한 것은 맞음

            /*** 추론 ***/
            // 한 스레드가 먼저 종료된 상황


            /*** 결론 ***/
            // 중간값도 중요한 경우는 단순 mutex 설정 지양
                // thread 순서 제어 필요하면 사용

            System.out.println(name + " call " + i + " times");
        }
    }

    public synchronized void mutexWithSyncMethod(){
        for(int i = 1 ; i <= 1000_0; i++){
            chatCount += 1;

            // 임계 영역 설정
            lock.lock();
            totalChatCount += 1;        // 한 번에 하나의 스레드만 들어올 수 있음
            lock.unlock();
            /*** 결과 ***/
            // 위와 같음

            /*** 추론 ***/
            // 어차피 각 객체에 종속된 메소드

            /*** 결론 ***/


            System.out.println(name + " call " + i + " times");
        }
    }


    public void semaphoreWithoutSyncMethodAndMakePermitLeft(){
        for(int i = 1 ; i <= 1000_0; i++){
            chatCount += 1;
            try {
                semaphore.acquire(1);       // Semaphore, 매개변수 개수만큼 lock 획득, 최대 값 넘으면 실행 안됨
                totalChatCount += 1;
                semaphore.release(1);       // lock 을 파라미터 개수만큼 해제
                /*** 결과 ***/
                // 20000 이 나오지 못함

                /*** 추론 ***/
                // 현재 설정한 Permit 의 개수 2 개
                    // 각 스레드가 임계 영역에 접근 시 1 개 의 Permit 을 획득
                        // 즉, 두 스레드가 동시 접근이 가능해져 버림

                /*** 결론 ***/
                // Permit 의 개수 주의

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void semaphoreWithoutSyncMethodAndMakePermitNotLeft(){
        for(int i = 1 ; i <= 1000_0; i++){
            chatCount += 1;
            try {
                semaphore.acquire(2);       // Semaphore, 매개변수 개수만큼 lock 획득, 최대 값 넘으면 실행 안됨
                totalChatCount += 1;
                semaphore.release(2);       // lock 을 파라미터 개수만큼 해제
                /*** 결과 ***/
                // 의도한 결과 나옴, 중간값은 10000 을 넘어감

                /*** 추론 ***/
                // 한 스레드가 임계 영역에 들어가면 남아있는 Permit 없음
                    // 다른 스레드가 접근 불가

                /*** 결론 ***/
                // Permit 의 개수 주의

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void semaphoreWithoutSyncMethodWithMultipleAcquireWithPermitLeft(){
        for(int i = 1 ; i <= 1000_0; i++){
            chatCount += 1;
            try {
                semaphore.acquire(1);       // Semaphore, 매개변수 개수만큼 lock 획득, 최대 값 넘으면 실행 안됨
                totalChatCount += 2;
                semaphore.acquire(1);
                totalChatCount -= 1;
                semaphore.release(2);       // lock 을 해제

                /*** 결과 ***/
                // Deadlock 발생 무한 대기 상태

                /*** 추론 ***/
                // 두 스레드가 첫 acquire 에서 동시에 permit 을 취하게 되는 상황 발생
                    // 다음 acquire 에서 넘어갈 수가 없음

                /*** 결론 ***/
                // acquire 연계와 Permit 개수 활용 주의
                    // Permit 개수를 늘리면 결국 남는 Permit 이 꾸준히 발생
                        // 임계영역 동시 접근 발생 -> 의도한 결과 X
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void semaphoreWithoutSyncMethodWithMultipleAcquireAndReleaseWithPermitLeft(){
        for(int i = 1 ; i <= 1000_0; i++){
            chatCount += 1;
            try {
                semaphore.acquire(1);
                totalChatCount += 2;
                semaphore.release(1);       // lock 을 해제
                semaphore.acquire(1);
                totalChatCount -= 1;
                semaphore.release(1);       // lock 을 해제

                /*** 결과 ***/
                // 일반 permit left 와 같음

                /*** 추론 ***/
                // 일반 permit left 와 같음

                /*** 결론 ***/
                // permit 의 개수 주의

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void semaphoreWithoutSyncMethodWithMultipleAcquireAndReleaseWithPermitNotLeft(){
        for(int i = 1 ; i <= 1000_0; i++){
            chatCount += 1;
            try {
                semaphore.acquire(2);
                totalChatCount += 2;
                semaphore.release(2);       // lock 을 해제
                semaphore.acquire(2);
                totalChatCount -= 1;
                semaphore.release(2);       // lock 을 해제

                /*** 결과 ***/
                // Permit Not Left 와 같음

                /*** 추론 ***/
                // Permit Not Left 와 같음

                /*** 결론 ***/
                // Permit 의 개수 주의

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void semaphoreWithSyncMethod(){
        // 스레드 동기화 설정
        for(int i = 1 ; i <= 1000_0; i++) {
            chatCount += 1;
            try {
                semaphore.acquire(2);       // Semaphore, 매개변수 개수만큼 lock 획득, 최대 값 넘으면 실행 안됨
                totalChatCount += 1;
                semaphore.release(2);       // lock 을 해제

                /*** 결과 ***/
                // 하나는 20000 하나는 10000 넘음

                /*** 추론 ***/
                // synchronized method 는 객체에 종속
                    // totalChatCount 는 클래스에 종속

                /*** 결론 ***/
                // 동기화를 하고자 하는 자원의 스코프에 주의

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " call " + i + " times");
        }
    }


    // Semaphore 사용
    public void semaphoreWithoutSyncMethodWithSyncBlock(){
        // totalChatCount 에 대하여 스레드 동기화 설정
        synchronized (totalChatCount) {
            for(int i = 1 ; i <= 1000_0; i++){
                chatCount += 1;
                try {
                    semaphore.acquire(2);       // Semaphore, 매개변수 개수만큼 lock 획득, 최대 값 넘으면 실행 안됨
                    totalChatCount += 1;
                    semaphore.release(2);       // lock 을 해제

                    /*** 결과 ***/
                    // 하나는 20000 하나는 10000
                        // 스레드마다 일괄 처리
                            // 그러나 스케줄러에 의해 스레드 실행 순서가 다를 순 있음


                    /*** 추론 ***/
                    // 한 스레드가 totalChatCount 에 대한 모니터(락)을 가지고 됨
                        // 이 스레드가 락을 해제하기 전에 다른 스레드가 접근을 못함
                        // 일괄처리

                    /*** 결론 ***/
                    // 스레드 차원에서 serial 한 작업이 필요시 Thread 순서 제어하기

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name + " call " + i + " times");
            }
        }
    }

    public void printState(){
        System.out.println(name+"'s chat count is " + chatCount + " and total chat count is " + totalChatCount);
    }




    // static 메소드 -> 각 객체가 공유
    public static void printTestName(int func, String name){
        // Static 메소드 안에 있지만, instanceCall 은 각 객체에 속함
        // 즉, 공유 변수가 아님
        int instanceCall = 0;

        classCall += 1;
        instanceCall += 1;

        switch(func){
            case 1:
                if(classCall == 2) System.out.println("[Plain Sync Method]");
                break;
            case 2:
                if(classCall == 2) System.out.println("[Static Sync Method]");
                break;
            case 3:
                if(classCall == 2) System.out.println("[Plain Sync Method With Wait And Notify]");
            case 4:
                if(classCall == 2) System.out.println("[Static Sync Method With Wait And Notify]");
                break;
            case 5:
                if(classCall == 2) System.out.println("[Plain Sync With Wait And Notify With Sync Block]");
                break;
            case 6:
                if(classCall == 2) System.out.println("[Mutex Without Sync Method]");
                break;
            case 7:
                if(classCall == 2) System.out.println("[Mutex With Sync Method]");
                break;
            case 8:
                if(classCall == 2) System.out.println("[Semaphore Without Sync Method And Make Permit Left]");
                break;
            case 9:
                if(classCall == 2) System.out.println("[Semaphore Without Sync Method And Make Permit Not Left]");
                break;
            case 10:
                if(classCall == 2) System.out.println("[Semaphore Without Sync Method With Multiple Acquire With Permit Left]");
                break;
            case 11:
                if(classCall == 2) System.out.println("[Semaphore Without Sync Method With Multiple Acquire And Release With Permit Left]");
                break;
            case 12:
                if(classCall == 2) System.out.println("[Semaphore Without Sync Method With Multiple Acquire And Release With Permit Not Left]");
                break;
            case 13:
                if(classCall == 2) System.out.println("[Semaphore With Sync Method]");
                break;
            case 14:
                if(classCall == 2) System.out.println("[Semaphore Without Sync Method With Sync Block]");
                break;
        }

        System.out.println(">>> Class call count is " + classCall);
        System.out.println(">>> Instance " + name + " call count is " + instanceCall);
    }

}
