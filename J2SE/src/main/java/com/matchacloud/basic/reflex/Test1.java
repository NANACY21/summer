package com.matchacloud.basic.reflex;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * java源码编译后生成.class文件,当new该对象或new子类对象或执行main()或调用静态成员或反射时,该class文件将加载到内存中,
 * jvm会在堆中将加载到内存的class文件创建出一个对应的对象,即class对象
 *
 * 反射:是解剖class对象的一个技术,获取class对象的成员变量、方法名等,能获取一个class对象的方法名列表
 * 反射的好处是让代码更灵活通用,代码没写死
 *
 * 可以操作私有属性（但不这样做）
 * 运行期间，对象的动态改变，（框架的原理：反射、设计模式）
 * 利用反射获取：父类、接口、构造器
 *
 * 注解 反射 动态修改字节码文件，这三者的区别与联系
 */
public class Test1 {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Dog dog = new Dog();
        dog.wangwang();


        /**
         * 描述class对象的类叫Class类
         * 首先,要获取class对象(3种方法),类加载一次,class对象内存中就一个,反射形式创建类对象、对象
         * 获取class对象的方法1：
         */
        Class<? extends Dog> dogClass1 = dog.getClass();

        /**获取class对象的方法2：
         * 万物皆对象，获取类的对象
         * 反射
         * 获取Dog的class对象
         */
        Class<Dog> dogClass2 = Dog.class;
        try {
            /**获取class对象的方法3：
             * 参数：一个类的完整类路径
             */
            Class<?> dogClass3 = Class.forName("reflex.Dog");
            Object o = dogClass3.newInstance();
            Dog dog1 = (Dog) o;

            /**
             * 描述field对象的类叫Field类
             */
            Field[] fs = dogClass3.getDeclaredFields();
            /*
            不建议这么写，破坏私有了
             */
            for (Field f : fs) {
                f.setAccessible(false);//不允许访问
                if (f.getName().equals("name")) {
                    f.set(o, "zhangsan");
                }
                if (f.getName().equals("age")) {
                    f.setInt(o, new Integer(23));
                }
                System.out.println(f.getName());
            }

            System.out.println();
            /**
             * 描述method对象的类叫Method类
             */
            Method m1 = dogClass3.getDeclaredMethod("setAge", Integer.class);
            /**
             * 描述构造方法对象的类叫Constructor类
             */
            Constructor<?>[] constructors = dogClass3.getDeclaredConstructors();

            m1.invoke(o, 14);


            //Java中反射 获取类中方法 并执行
            Method[] methods = dogClass2.getDeclaredMethods();//通过类对象获取类中所有的方法
            for (Method m : methods) {
                if (m.getName().equals("toString")) {
                    m.invoke(o, null);
                }
                System.out.println(m.getName());//打印方法的名字
//            调用方法对象中invoke方法，
                m.invoke(dog1);//该行是为了执行方法。参数dog1是方法所在的对象

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Dog dog1 = dogClass2.newInstance();//获取Dog类对象
        dog1.wangwang();
    }
}
