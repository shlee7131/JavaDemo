package async.control.handson.wait;

public class GetThread extends Thread {
    SharedData shared;
    public GetThread(String name, SharedData shared){
        super.setName(name);
        this.shared = shared;
    }

    @Override
    public void run() {
        while(true){
            // shared.get() 을 할 때 GetThread 가
            System.out.println(getName() + " got " + shared.get());
        }
    }
}
