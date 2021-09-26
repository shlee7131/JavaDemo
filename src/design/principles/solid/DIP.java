package design.principles.solid;

import javax.xml.crypto.Data;

public class DIP {
    // Dependency Inversion Principle
    // Higher Level Modules <=> Abstract Layer(Interface,Abstract Class) <=> Lower Level Modules
        // 고수준 모듈의 구현을 하위 모듈에서 직접 X -> Abstract Layer 를 활용해서 두 모듈 사이를 느슨하게 만들어주기
    public static void main(String[] args) {
        // DatabaseConnector -> Higher Layer
        // MongoDBDriver -> Lower Layer
        // MySQLDriver -> Lower Layer
        DatabaseConnector mongo = new DatabaseConnector(new MongoDBDriver());
        DatabaseConnector mysql = new DatabaseConnector(new MySQLDriver());
        mongo.connect();
        mysql.connect();
    }
}
// Abstraction Layer
abstract class DatabaseDriver {
    String name;

    abstract public void database();
    abstract public void create();
    abstract public void read();
    abstract public void update();
    abstract public void delete();
}

// Lower
class MongoDBDriver extends DatabaseDriver {
    MongoDBDriver(){
        this.name = "Mongo";
    }
    @Override
    public void database() {
        System.out.println("This is Mongo");
    }
    public void create(){}
    public void read(){}
    public void update(){}
    public void delete(){}
}

// Lower
class MySQLDriver extends DatabaseDriver {
    MySQLDriver(){
        this.name = "MySQL";
    }
    @Override
    public void database() {
        System.out.println("This is MySQL");
    }
    public void create(){}
    public void read(){}
    public void update(){}
    public void delete(){}
}

// MongoDBDriver 나 MySQLDriver 는 DatabaseConnector 에 의존관계
// 이를 abstract layer 로서 DatabaseDriver 인터페이스를 활용해 느슨한 관계 만들어주기
class DatabaseConnector {
    DatabaseDriver databaseDriver;
    DatabaseConnector(DatabaseDriver databaseDriver) {
        this.databaseDriver = databaseDriver;
    }

    public void getDatabaseDriver() {
        this.databaseDriver.database();
    }

    public void create(){}
    public void read(){}
    public void update(){}
    public void delete(){}

    public void connect(){
        getDatabaseDriver();
        System.out.println("Connected to " + databaseDriver.name);
    }
}
