package com.matchacloud.basic.datastructure;

/**
 * 线性数据结构的查找算法
 */
public class Search {

    /**
     * 非递归折半查找(二分查找)
     * 针对从小到大有序的线性数据结构
     *
     * @param arr 线性数据结构
     * @param key 关键字
     * @return key在arr中的索引
     */
    public static int searchBin(int[] arr, int key) {
        int low = 0;//最小索引
        int high = arr.length - 1;//最大的索引
        if (key > arr[high] || key < arr[low]) {
            return -1;
        }
        int middle = (low + high) / 2;//中间索引
        boolean flag = false;
        while (low <= high) {
            middle = (low + high) / 2;
            if (arr[middle] > key) {
                high = middle - 1;
            } else if (arr[middle] < key) {
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
     * 针对从小到大有序的线性数据结构
     * 递归折半查找(二分查找)
     *
     * @param arr 线性数据结构
     * @param key  关键字
     * @param low  左边索引
     * @param high 右边索引
     * @return
     */
    public static int searchBin(int[] arr, int key, int low, int high) {
        if (key > arr[high] || key < arr[low]) {
            return -1;
        }
        if (low > high) {
            return -1;
        }
        int middle = (low + high) / 2;
        if (key < arr[middle]) {
            return searchBin(arr, key, low, middle - 1);
        } else if (key > arr[middle]) {
            return searchBin(arr, key, middle + 1, high);
        } else {
            return middle;
        }
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6};
        System.out.println(searchBin(array, 4, 0, 5));
    }
}
