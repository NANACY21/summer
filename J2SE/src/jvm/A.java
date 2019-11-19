package jvm;
//变量堆栈link
/**
 * jvm是一款软件，c/c++编写，用于运行字节码，不是跨平台的
 * Java是一门语言，编译成字节码，在jvm上运行，Java字节码是跨平台的
 */
public class A {
    String i = "op";//变量i在堆里

    void func(String s) {//变量s在栈里
        s = "" + 9;
    }

    static void test() {
        A a = new A();//变量a在堆里
        a.func(a.i);//执行完a.i不变，还是"op"
    }
}
