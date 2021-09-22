package async.control.handson.wait;

public class PutThread extends Thread {
    private SharedData shared;
    private int data;

    public PutThread(String name, SharedData shared, int data){
        super.setName(name);
        this.shared = shared;
        this.data = data;
    }

    @Override
    public void run() {
        shared.put(data);
    }
}
