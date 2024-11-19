package annotation;//主要!!!

/**
 * 注解解析
 */
public class Test1 {
    public static void main(String[] args) {
        //1.使用反射来获取Bookshelf的class对象
        Class<BookShelf> bookShelfClass = BookShelf.class;
        //2.判断bookshelf上有没有Book注解 book注解必须先进内存 book注解编译后也是字节码文件
        // 获取注解类对象时 可能不进内存
        System.out.println(Book.class);


        if (bookShelfClass.isAnnotationPresent(Book.class)) {
            Book book = bookShelfClass.getAnnotation(Book.class);

            //将注解中的属性值获取出来,拿着这个值做一些事情
            System.out.println(book.bookName());
        }
    }
}
