package base.polymorphism;

/**
 * 体会Java面向对象编程的理念 Java三大特性之 继承(Inheritance)、多态(Polymorphism)
 *
 * 多态是指允许不同类的对象对同一消息做出不同的响应
 * 在 Java 中，多态主要有两种实现方式：编译时多态（方法重载）和运行时多态（方法重写）
 * 编译时多态（方法重载）不一定需要继承和接口实现
 * 运行时多态（方法重写）通常涉及继承或接口实现
 *
 * 男朋友
 */
public class BoyFriend extends Person {
    private String houseArea;
    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void findFriend(Person p) {

    }

    @Override
    public String toString() {
        return "BoyFriend{" +
                "house='" + houseArea + '\'' +
                '}';
    }

    @Override
    public void fly() {

    }

    @Override
    public void useCredit() {
    }
}
