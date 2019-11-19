package base.java8.demo3;

public class PersonReal implements Person<Cat> {

    @Override
    public Cat get() {
        return new Cat();
    }
}
