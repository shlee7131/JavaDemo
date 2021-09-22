package async.control.handson.wait;

public class WaitTest {
    public static void main(String[] args) {
        SharedData shared = new SharedData();

        GetThread get1 = new GetThread("GetThread-1", shared);
        GetThread get2 = new GetThread("GetThread-2", shared);
        GetThread get3 = new GetThread("GetThread-3", shared);

        PutThread put1 = new PutThread("PutThread-1",shared, 10);
        PutThread put2 = new PutThread("PutThread-2",shared, 20);
        PutThread put3 = new PutThread("PutThread-3",shared, 30);

        get1.start();
        get2.start();
        get3.start();

        put1.start();
        put2.start();
        put3.start();
    }
}
