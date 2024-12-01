package base.polymorphism;

/**抽象类示例
 *
 * 抽象类实现接口，抽象类中可以不重写，抽象类的子类必须重写
 *
 * 人
 */
public abstract class Person implements Consumer{
    private String name;//姓名
    private int age;//年龄
    private String sex;//性别
    private String profession;//职业
    private int income;//收入


    /**
     * 子类可以继承但不可修改父类的常量
     */
    private static final String ID = "230107199802221213";


    /**抽象类不能实例化，它是供继承的，
     * 但抽象类可以有构造器，其子类可以隐式调用抽象类构造器
     * @param name
     * @param age
     * @param sex
     */
    public Person(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }

    /**
     * 抽象方法，子类必须重写，抽象方法只能写在抽象类中，不能写在普通类里
     * @return
     */
    public abstract String getInfo();

    /**
     * 抽象类中也可以写普通方法，子类可以不重写
     */
    public void sayHello() {

    }


    /**
     * @param p
     */
    public void findFriend(Person p) {
        p.toString();
    }
}
