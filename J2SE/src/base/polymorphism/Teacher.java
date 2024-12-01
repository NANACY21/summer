package base.polymorphism;

public class Teacher extends Person {

    //teacher依赖于学生
    private Student student;
    private Consumer consumer;
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void homeWork(){

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void fly() {

    }

    @Override
    public void useCredit() {

    }
}
