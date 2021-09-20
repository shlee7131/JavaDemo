package async.basic;

public class ChatRoom {

    public static void main(String[] args) throws InterruptedException {
        // 채팅을 동시 입력 가정
            // 유저의 채팅 횟수와 전체 채팅 횟수를 확인하는 예제
        User A = new User("A");
        User B = new User("B");
        Thread userA = new Thread(A);
        Thread userB = new Thread(B);
        userA.start();
        userB.start();

    }
}
