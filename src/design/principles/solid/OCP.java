package design.principles.solid;

public class OCP {
    // Open-Closed Principle
    // You should be able to extend a classes behavior, without modifying it.
    // 상위 엔티티의 확장성을 높이기 위해 하위 엔티티와의 의존 관계를 설정하는 방식을 추상화 또는 다형성을 활용
        // 새로운 하위 엔티티를 추가할 때 마다 의존 관계 설정 방식을 변경시킬 필요가 없어짐
    public static void main(String[] args) {
        // Need to use proper detergent for each cloth
        WashingMachine washingMachine = new WashingMachine();
        washingMachine.setOption(new Sweater());
        System.out.println(washingMachine);
        washingMachine.setOption(new WhiteShirt());
        System.out.println(washingMachine);
    }
}

class Detergent {
    public String name = "";
    public void setDetergent(String name){
        this.name = name;
    }
}

class WashingMachine {
    Cloth cloth;

    // 추상 클래스인 Cloth 를 상속받은 어떠한 옷이라도 적용 가능
    // 확장 가능 O and 변경 X => cloth 부분만 관리하면 된다.
    public void setOption(Cloth cloth){
        this.cloth = cloth;
        this.cloth.useProperDetergent();
    }

    @Override
    public String toString() {
        return "WashingMachine {" +
                "cloth=" + cloth.name +
                ", detergent=" + cloth.detergent.name +
                '}';
    }
}

// 다형성을 활용하여 WashingMachine 에서 Cloth 의 하위 엔티티 관련 확장 가능
abstract class Cloth {
    public String name;
    // 세제와 aggregation 관계
    Detergent detergent = new Detergent();
    abstract public void doWash();
    abstract public void useProperDetergent();
}


class WhiteShirt extends Cloth{
    WhiteShirt(){
        this.name = "WhiteShirt";
    }
    @Override
    public void doWash() {
        System.out.println(name);
    }

    @Override
    public void useProperDetergent() {
        this.detergent.setDetergent(this.name);
    }
}

class Sweater extends Cloth {
    Sweater(){
        this.name = "Sweater";
    }
    @Override
    public void doWash() {
        System.out.println(name);
    }

    @Override
    public void useProperDetergent() {
        this.detergent.setDetergent(this.name);
    }
}