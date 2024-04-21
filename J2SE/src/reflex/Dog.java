package reflex;

public class Dog {

    private String name;
    private Integer age;

    public void wangwang() {
        System.out.println("9527 旺旺...");
    }

    public void eat() {
        System.out.println("dog eat()");
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
