package async.control.handson.wait;

public class SharedData {
    private boolean isFullData = false;
    private int data;

    // PutThread 3 개중 어느 한순간에는 하나의 스레드만 put 메소드 진입가능
    // 데이터가 차있으면 wait
    // 데이터를 채우고 일시정지(대기) 상태인 모든 PutThread 를 깨운다.
    // 그 중 하나만 모니터를 얻고 run 상태가 된다.
    public synchronized void put(int data){
        try {
            // busy waiting
            while(isFullData) {     // 데이터가 있으면 대기
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.data = data;           // 데이터 없는 경우 초기화
        isFullData = true;          // 데이터 존재 여부
        notifyAll();                // 일시 정지된 모든 스레드 깨움
    }

    // GetThread 3 개중 어느 한순간에는 하나의 스레드만 get
    // 데이터가 없으면 wait 한다.
    // 데이터를 get 한 후 일시정지(대기) 상태인 GetThread 모두를 깨운다.
    // 그 중 하나만 모니터를 얻고 run 상태가 된다.
    public synchronized int get(){
        try {
            while(!isFullData) {    // 데이터가 없으면 대기
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        isFullData = false;         // 데이터가 없다고 표시
        notifyAll();                // 대기하는 모든 스레드 깨움
        return data;                // 데이터 꺼내기
    }
}
