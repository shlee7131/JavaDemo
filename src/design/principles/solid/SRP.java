package design.principles.solid;

public class SRP {
    // Solid Responsibility Principle
    // 각 클래스가 어떤 행위를 하는지 명확하게 확인이 가능
    public static void main(String[] args) {
        SchedulingApplicationWithoutSRP schedulingApplicationWithoutSRP = new SchedulingApplicationWithoutSRP(new TaskScheduling());
        schedulingApplicationWithoutSRP.schedule();

        SchedulingApplicationWithSRP schedulingApplicationWithSRP = new SchedulingApplicationWithSRP(new Compute(),new Save(), new Notification());
        schedulingApplicationWithSRP.schedule();
    }
}

// SRP X
class TaskScheduling {
    public String compute(){
        return "computing data based on user input";
    }

    public String save(){
        return "save the object to database";
    }

    public String sendNotification(){
        return "send the notification to the user";
    }
}

// SRP O
class Compute{
    public String compute(){
        return "computing data based on user input";
    }
}

class Save{
    public String save(){
        return "save the object to database";
    }
}

class Notification {
    public String sendNotification(){
        return "send the notification to the user";
    }
}


class SchedulingApplicationWithoutSRP {
    TaskScheduling taskScheduling;
    public SchedulingApplicationWithoutSRP(TaskScheduling taskScheduling){
        this.taskScheduling = taskScheduling;
    }

    public void schedule(){
        System.out.println(taskScheduling.compute());
        System.out.println(taskScheduling.save());
        System.out.println(taskScheduling.sendNotification());
    }
}

class SchedulingApplicationWithSRP {
    Compute compute;
    Save save;
    Notification notification;

    public SchedulingApplicationWithSRP(Compute compute, Save save, Notification notification){
        this.compute = compute;
        this.save = save;
        this.notification = notification;
    }

    public void schedule(){
        System.out.println(compute.compute());
        System.out.println(save.save());
        System.out.println(notification.sendNotification());
    }
}
