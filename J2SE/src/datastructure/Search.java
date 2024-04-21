package datastructure;

import datastructure.data.SqList;

/**
 * 查找算法
 * 查找是通过关键字找到其所属的数据元素
 * 二叉查找树的查找、插入、删除->最坏情况下时间复杂度都是O(n)
 */
public class Search {

    /**
     * 非递归折半查找（二分查找）
     * 针对从小到大有序
     *
     * @param sqList
     * @param key
     * @return key在sqList中的索引
     */
    public static int searchBin(SqList sqList, int key) {
        int low = 0;//最小索引
        int high = sqList.getLength() - 1;//最大的索引
        if (key > sqList.getValue()[high] || key < sqList.getValue()[high]) {
            return -1;
        }
        int middle = (low + high) / 2;//中间索引
        boolean flag = false;
        while (low <= high) {
            middle = (low + high) / 2;
            if (sqList.getValue()[middle] > key) {
                high = middle - 1;
            } else if (sqList.getValue()[middle] < key) {
                low = middle + 1;
            } else {
                flag = true;
                break;
            }
        }
        if (flag) {
            return middle;
        }
        return -1;
    }

    /**
     * 针对从小到大有序
     * 递归折半查找
     *
     * @param sqList
     * @param key    值
     * @param low    索引
     * @param high   索引
     * @return
     */
    public static int searchBin(SqList sqList, int key, int low, int high) {
        if (key > sqList.getValue()[high] || key < sqList.getValue()[low]) {
            return -1;
        }
        if (low > high) {
            return -1;
        }
        int middle = (low + high) / 2;
        if (key < sqList.getValue()[middle]) {
            return searchBin(sqList, key, low, middle - 1);
        } else if (key > sqList.getValue()[middle]) {
            return searchBin(sqList, key, middle + 1, high);
        } else {
            return middle;
        }
    }
}
