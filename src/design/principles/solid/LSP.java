package design.principles.solid;

import java.util.concurrent.ForkJoinPool;

public class LSP {
    // Liskov Substitution Principle
    // 자식 클래스는 부모 클래스의 행위를 온전히 수행할 수 있어야 함
    // 위배되는 메소드가 생기면
        // 1. 부모 클래스에서 메소드 삭제
            // 자식 클래스에서 메소드를 구분해주기 위해 중간 단계의 클래스 생성
            // or
            // 자식 클래스에 메소드 별도 생성
        // 2. inheritance -> association 로 관계 변경
    public static void main(String[] args) {
        // LSP 위배
        Eagle eagle = new Eagle();
        eagle.fly();
        Penguin penguin = new Penguin();
        penguin.fly();

        // LSP 지킴
        Dog dog = new Dog();
        dog.legs();
        Human human = new Human();
        human.legs();

    }
}

/** LSP 위배 **/
class Bird {
    public void fly() {
        System.out.println("I can fly");
    }
}

class Eagle extends Bird {
    @Override
    public void fly() {
        super.fly();
    }
}

class Penguin extends Bird {
    // LSP 위배
    // fly 의 실행 결과가 다르게 나온다
    @Override
    public void fly() {
        System.out.println("I can't fly");
    }
}

/** LSP 지킴 **/
class Animal {
}

class FourLegsAnimal extends Animal {
    public void legs(){
        System.out.println("I have 4 legs");
    }
}



class TwoLegsAnimal extends Animal {
    public void legs(){
        System.out.println("I have 2 legs");
    }
}

class Dog extends FourLegsAnimal {
}

class Human extends TwoLegsAnimal {
}