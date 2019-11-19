package dataStructure.data;

import java.util.Arrays;

/**
 * 顺序表 线性
 */
public class SqList {

    private int[] value;//一旦初始化，数组长度不可变
    private int length;//value的长度 元素个数
    private int leftIndex;//第一个数索引
    private int rightIndex;//最后一个数索引

    public SqList(int... value) {//形参相当于数组
        this.value = value;//元素不可=0
        this.length = this.value.length;
        this.leftIndex = 0;
        this.rightIndex = this.length - 1;
    }

    public int[] getValue() {
        return value;
    }

    public int getLength() {
        return length;
    }

    public void setValue(int[] value) {
        this.value = value;
        this.length = this.value.length;
    }

    public int getLeftIndex() {
        return leftIndex;
    }

    public void setLeftIndex(int leftIndex) {
        this.leftIndex = leftIndex;
    }

    public int getRightIndex() {
        return rightIndex;
    }

    public void setRightIndex(int rightIndex) {
        this.rightIndex = rightIndex;
    }

    @Override
    public String toString() {
        return Arrays.toString(value);
    }
}
