package design.principles.basic;

public class DRY {
    // Don't Repeat Yourself
    // 반복되는 코드 작성 X -> 유지 보수에 어려움 유발
    // 메소드로 추상화 시키기
    public static void main(String[] args) {
        // Without DRY
        System.out.println("[Do Laundry Without DRY]");
        doLaundryShirtWithoutDRY();
        doLaundrySweaterWithoutDRY();

        System.out.println("-------------------------------------\n");
        // With DRY
        System.out.println("[Do Laundry With DRY]");
        doLaundryWithDRY("Shirt");
        doLaundryWithDRY("Sweater");
    }



    public static void doLaundryShirtWithoutDRY(){
        System.out.println("Turn-on washing machine");
        System.out.println("Put Shirt in the washing machine");
        System.out.println("put detergent for a Shirt in the washing machine");
        System.out.println("Select the Shirt option\n");
    }


    public static void doLaundrySweaterWithoutDRY(){
        System.out.println("Turn-on washing machine");
        System.out.println("Put Sweater in the washing machine");
        System.out.println("put detergent for a Sweater in the washing machine");
        System.out.println("Select the Sweater option\n");
    }

    public static void doLaundryWithDRY(String cloth){
        commonTask();
        taskWithOption(cloth);
    }

    public static void commonTask(){
        System.out.println("Turn-on washing machine");
    }

    public static void taskWithOption(String cloth){
        System.out.println("Put "+ cloth + " in the washing machine");
        System.out.println("put detergent for a "+ cloth +" in the washing machine");
        System.out.println("Select the "+ cloth + " option\n");
    }

}
