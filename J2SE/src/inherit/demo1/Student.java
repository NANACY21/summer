package inherit.demo1;
import inherit.demo3.Car;
import inherit.demo3.Vehicle;
import inherit.demo4.Pet;

/**
 * class之间：继承（泛化）关系
 * interface之间：继承关系，并且是多继承
 * class与interface：实现关系
 * <p>
 * <p>
 * 继承 优势：代码复用 侧重于共同特征属性
 * java中一个类只能继承一个直接父类，final类不可以被继承
 * <p>
 * <p>
 * 接口 优势：解耦合（使对象间、类间的依赖、关系少一些） 实现接口侧重于不同类型的相同行为（接口中的方法是行为）
 * 一个类能实现多个接口 -> 类具备了接口中的能力了
 */
public class Student extends Person implements Consumer {
    private String school;//该学生所在学院

    public Student(String school) {
        super();
        this.school = school;
    }

    public void test() {
        System.out.println("这是Student类的方法");
    }

    public void study() {
        System.out.println("该学生在" + this.school + "学习");
    }

    /**
     * 收费
     *
     * @param v 任何车辆
     */
    public void charge(Vehicle v) {
        System.out.println(v.parkingFee());
    }

    /**
     * 买宠物
     *
     * @param pet
     */
    public void buyPet(Pet pet) {

    }

    @Override
    public void fly() {
        //重写
    }

    @Override
    public void useCredit() {

    }

    @Override
    public void newMethod() {

    }

    @Override
    public String getInfo() {
        return null;
    }

    public static void main(String[] args) {

        /**父类/接口来创建子类对象，则该对象具有多态性，
         * 编译期父类不能调用子类特有的方法和属性
         *
         * 没经历过上溯转型则不可以下溯转型？？？
         */
        Consumer consumer = new Student("软件学院");

        /**向上转型后，子类特有的属性和方法用不了了
         *
         */
        ((Student) consumer).test();

        /**instanceof关键字判断以强转
         * 子类可以直接转成父类
         */
        if (consumer instanceof Consumer) {
            System.out.println("a");
        }
        if (consumer instanceof Student) {
            System.out.println("b");
        }

        /**接口多态
         * 多态作用：面向接口/抽象编程
         * 多态目的：解耦合
         * 多态的2种表现形式：方法的重载和重写 Java多态的实现：继承、重载、覆盖
         * 多态的应用1：多态参数（方法参数）
         * 多态的应用2：接口作为属性以依赖，多态参数set该属性，见Teacher类
         * oc原则:对扩展开放，对修改关闭
         */
        new Student("软件学院").charge(new Car());
        //还有继承多态

        BoyFriend b = new BoyFriend();
        GirlFriend g = new GirlFriend();
    }
}