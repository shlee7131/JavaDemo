package design.principles.solid;

public class ISP {
    // Interface Segregation Principle
    // 인터페이스는 클래스들이 필요하지 않는 메소드는 구현하지 않게끔 구체적이고 작은 단위로 설정
    public static void main(String[] args) {

        NeedToStudy student = new Student();
        NeedToWork worker = new Worker();
        student.goToSchool();
        worker.goToWork();

        // ISP X
        student.goToWork();     // Don't need
        worker.goToSchool();    // Don't need

        // ISP O
        student.study();
        worker.work();
    }
}

interface People {
    public void goToSchool();
    public void goToWork();
}

interface NeedToStudy extends People {
    public void study();
}

interface  NeedToWork extends People {
    public void work();
}


class Student implements NeedToStudy{

    @Override
    public void goToSchool() {
        System.out.println("I go to school");
    }

    @Override
    public void goToWork() {
        System.out.println("Student don't need to go to work => ISP X");
    }

    @Override
    public void study() {
        System.out.println("Student need to study => ISP O");
    }
}

class Worker implements NeedToWork {

    @Override
    public void goToSchool() {
        System.out.println("Worker don't need to go to school => ISP X");
    }

    @Override
    public void goToWork() {
        System.out.println("I go to work");
    }

    @Override
    public void work() {
        System.out.println("Worker need to work => ISP O");
    }
}

