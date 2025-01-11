package com.matchacloud.basic.datastructure;

import java.util.Arrays;

/**
 * 线性数据结构8种排序算法
 * 给出某一种排序算法要认识是哪种排序算法
 */
public class Sort {

    /**
     * 直接插入排序
     * 一开始默认第一个元素有序，从第二个元素开始遍历，把元素插入已经有序的子序列中。
     *
     * @param array 线性数据结构
     */
    public static int[] insertSort(int[] array) {

        // 数组的长度
        int n = array.length;

        // 从第二个元素开始遍历数组，因为第一个元素默认是已排序的
        for (int i = 1; i < n; i++) {
            // 取出当前要插入的元素
            int current = array[i];
            // 用于比较的位置，从当前元素的前一个位置开始
            int j = i - 1;

            // 从后往前遍历已排序的部分，找到当前元素应该插入的位置
            while (j >= 0 && array[j] > current) {
                // 如果当前比较的元素大于要插入的元素，将其向后移动一位
                array[j + 1] = array[j];
                // 继续向前比较
                j--;
            }
            // 将当前元素插入到正确的位置
            array[j + 1] = current;
        }
        return array;
    }

    /**
     * 折半插入排序
     * 还是设第一个数有序，
     * 将元素插入已有序子表中，由于有序，插入点折半查找
     *
     * @param array
     */
    public static void bInsertSort(int[] array) {

        int i, j;
        int temp;
        for (i = 1; i < array.length; ++i) {
            temp = array[i];//将待插入的记录暂存到监视哨中
            //设置查找区间初值
            int leftIndex=0;
            int rightIndex=i - 1;
            int mid;
            //在查找区间中折半查找插入的位置 缩小插入区间
            while (leftIndex <= rightIndex) {
                mid = (leftIndex + rightIndex) / 2;//折半
                //插入点在前一子表
                if (temp < array[mid]) {
                    rightIndex=mid - 1;
                }
                //插入点在后一子表
                else {
                    leftIndex=mid + 1;
                }
            }
            //后一子表记录后移
            for (j = i - 1; j >= rightIndex + 1; --j) {
                array[j + 1] = array[j];
            }
            //将temp即待插入元素插入到正确位置
            array[rightIndex + 1] = temp;
        }
    }

    /**
     * 希尔排序辅助算法
     *
     * @param array
     * @param dk
     */
    private static void shellInsert(int[] array, int dk) {
        int i, j;
        int temp;
        for (i = dk ; i < array.length; ++i) {
            //需将array[i]插入有序增量子表
            if (array[i] < array[i - dk]) {
                temp = array[i];//暂存在temp
                for (j = i - dk; j > -1 && temp < array[j]; j -= dk) {
                    //记录后移,直到找到插入位置
                    array[j + dk] = array[j];
                }
                //将temp即原array[i],插入到正确位置
                array[j + dk] = temp;
            }
        }
    }

    /**
     * 希尔排序 难
     *
     * @param array
     * @param dt 希尔排序辅助数组 容量要大一些
     * @param t
     */
    public static void shellSort(int[] array, int[] dt, int t) {

        int k;
        int temp = array.length - 1;
        for (k = 0; k < t; ++k) {
            temp = temp / 2;
            dt[k] = temp;
            //一趟增量为dt[t]的希尔插入排序
            shellInsert(array, dt[k]);
        }
    }

    /**
     * 冒泡排序，与选择排序相似
     * 相邻的才比较
     * 时间复杂度O(n^2)
     */
    public static void bubbleSort(int[] array) {

        for (int i = 1; i < array.length; i++) {
//            一趟下来最大的到最后了，不动了，第二趟下来倒数第二大的也固定了
            for (int j = 0; j < array.length - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 快速排序辅助算法
     *
     * @param array
     * @param leftIndex
     * @param rightIndex
     * @return
     */
    private static int partition(int[] array, int leftIndex, int rightIndex) {

        int temp = array[leftIndex];//用子表的第一个记录做枢轴记录
        int pivot = array[leftIndex];//枢轴记录保存在pivot中
        //从表的两端交替地向中间扫描
        while (leftIndex < rightIndex) {
            while (leftIndex < rightIndex && array[rightIndex] >= pivot) {
                --rightIndex;
            }
            array[leftIndex] = array[rightIndex];//将比枢轴记录小的记录移到低端
            while (leftIndex < rightIndex && array[leftIndex] <= pivot) {
                ++leftIndex;
            }
            array[rightIndex] = array[leftIndex];//将比枢轴记录大的记录移到高端
        }
        array[leftIndex] = temp;//枢轴记录到位
        return leftIndex;//返回枢轴位置
    }

    /**
     * 快速排序
     * 是冒泡排序的改进
     * @param array
     * @param leftIndex 首个元素索引
     * @param rightIndex 最末元素索引
     */
    public static void quickSort(int[] array, int leftIndex, int rightIndex) {

        //调用前置初值: left=1;right=L.length;
        //对顺序表L中的子序列L.elem[left...right]做快速排序
        int pivotloc;
        //长度大于1
        if (leftIndex < rightIndex) {
            pivotloc = partition(array, leftIndex, rightIndex);//将L.elem[leftIndex...rightIndex]一分为二,pivotloc是枢轴位置
            quickSort(array, leftIndex, pivotloc - 1);//对左子表递归排序
            quickSort(array, pivotloc + 1, rightIndex);//对右子表递归排序
        }
    }

    /**
     * 简单选择排序
     *
     * @param array
     */
    public static void selectSort(int[] array) {

        int i, j, k, temp;
        //在所有记录中选择最小的记录，一趟循环下来筛出最小的数
        for (i = 0; i < array.length - 1; ++i) {
            k = i;
            //这个数组下标没有越界，见首层for循环
            for (j = i + 1; j < array.length; ++j) {
                //k指向此趟排序中最小的记录
                if (array[j] < array[k]) {
                    k = j;
                }
            }
            if (k != i) {
                //交换array[i]与array[k]
                temp = array[i];
                array[i] = array[k];
                array[k] = temp;
            }
        }
    }

    /**
     * 堆排序辅助算法
     *
     * @param array
     * @param leftIndex
     * @param rightIndex
     */
    private static void heapAdjust(int[] array, int leftIndex, int rightIndex) {
        //假设L.elem[left+1...right]已经是堆,将L.elem[left...right]调整为以L.elem[left]为根的大根堆
        int rc, j;
        rc = array[leftIndex];
        for (j = 2 * leftIndex; j <= rightIndex; j *= 2) {
            if (j < rightIndex && array[j] < array[j + 1])//j为较大的记录的下标
            {
                ++j;
            }
            if (rc >= array[j])//rc应插入在位置right上
            {
                break;
            }
            array[leftIndex] = array[j];
            leftIndex = j;
        }
        array[leftIndex] = rc;//插入
    }

    /**堆排序辅助算法
     * @param array
     */
    private static void creatHeap(int[] array) {

        //把无序序列L.elem[1...L.right]建成大根堆
        int n, i;
        n = array.length - 1;
        //反复调用my_heapadjust函数
        for (i = n / 2; i > -1; --i) {
            heapAdjust(array, i, n);
        }
    }

    /**
     * 堆排序
     *
     * @param array
     */
    public static void heapSort(int[] array) {

        //对顺序表L进行堆排序
        int i, temp;
        creatHeap(array);//把无序序列array[1...L.right]建成大根堆
        for (i = array.length - 1; i > 0; --i) {
            temp = array[0];//将堆顶记录和当前未经排序子序列array[0...i]中最后一个记录互换
            array[0] = array[i];
            array[i] = temp;
            heapAdjust(array, 0, i - 1);//将array[1...i-1]重新调整为大根堆
        }
    }

    /**
     * 2-路归并排序辅助算法
     *
     * @param array
     * @param leftIndex
     * @param midIndex
     * @param rightIndex
     */
    private static void merge(int[] array, int leftIndex, int midIndex, int rightIndex) {

        int i = leftIndex;
        int j = midIndex + 1;
        int k = leftIndex;
        int[] temp = new int[array.length];
        while (i <= midIndex && j <= rightIndex) {
            if (array[i] < array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        while (i <= midIndex) {
            temp[k++] = array[i++];
        }
        while (j <= rightIndex) {
            temp[k++] = array[j++];
        }
        while (leftIndex <= rightIndex) {
            array[leftIndex] = temp[leftIndex++];
        }
    }

    /**
     * 2-路归并排序
     *
     * 时间复杂度O(nlogn) 空间复杂度 O(n) 是稳定排序
     * @param array
     * @param leftIndex
     * @param rightIndex
     */
    public static void mergeSort(int[] array, int leftIndex, int rightIndex) {

        if (leftIndex >= rightIndex) {
            return;
        } else {
            int mid = (leftIndex + rightIndex) / 2;
            mergeSort(array, leftIndex, mid);
            mergeSort(array, mid + 1, rightIndex);
            merge(array, leftIndex, mid, rightIndex);
        }
    }
    /**
     * 插入元素仍有序
     */
    public static int[] insertSort(int[] array, int value) {
        int[] newSqList = new int[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            newSqList[i] = array[i];
        }
//        "a".compareToIgnoreCase("A");
//        "a".compareTo("a");
        int index = -1;
        for (int i = 0; i < newSqList.length; i++) {
            if (newSqList[i] != 0) {
                if (value < newSqList[i]) {
                    index = i;
                    break;
                }
            }
        }
        //元素移动
        if (index == -1) {
            newSqList[newSqList.length - 1] = value;
        } else {
            for (int j = newSqList.length - 2; j >= index; j--) {
                newSqList[j + 1] = newSqList[j];
            }
            newSqList[index] = value;
        }
        return newSqList;
    }

    public static void main(String[] args) {
        int[] array = { 9, 7, 10, 6, 5, 8, 1, 3, 2 };
        System.out.println(Arrays.toString(insertSort(array)));
    }
}
