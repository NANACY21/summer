package com.matchacloud.basic.designpattern.demo1;

/**水果工厂
 * 从该工厂得到水果
 *
 * 工厂类
 */
public class FruitFactory {


    public static Fruit create(String fruitName){
        if("apple".equals(fruitName)){
            return new Apple();
        }
        else if("pear".equals(fruitName)){
            return new Pear();
        }
        return null;
    }
}
