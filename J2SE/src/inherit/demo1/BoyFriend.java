package inherit.demo1;

/**
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
