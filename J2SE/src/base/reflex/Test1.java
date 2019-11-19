package base.reflex;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**反射
 *
 * 可以操作私有属性（但不这样做）
 * 运行期间，对象的动态改变，（框架的原理：反射、设计模式）
 * 利用反射获取：父类、接口、构造器
 */
public class Test1 {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Dog dog = new Dog();
        dog.wangwang();
        /**
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
            Class<?> dogClass3 = Class.forName("base.reflex.Dog");
            Object o = dogClass3.newInstance();
            Dog dog1 = (Dog) o;

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
            Method m1 = dogClass3.getDeclaredMethod("setAge", Integer.class);

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
