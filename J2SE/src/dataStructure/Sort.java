package dataStructure;

import dataStructure.data.SqList;

/**
 * 8种排序算法
 * 给出一种排序算法要认得是哪种排序算法
 * 库函数：Arrays.sort()
 * <p>
 * C语言排序，下标0的没有数据
 */
public class Sort {

    /**
     * 直接插入排序 内部排序 插入排序 ok
     * 设第一个数有序，
     * 将一个记录插入到已经排好序的有序表中，从而得到一个新的、记录数增1的有序表
     *
     * @param sqList
     */
    public static void insertSort(SqList sqList) {

        int temp;
        //依次插入
        for (int i = 1; i < sqList.getLength(); ++i) {
            //"<",需将索引i处元素插入有序子表
            if (sqList.getValue()[i] < sqList.getValue()[i - 1]) {
                temp = sqList.getValue()[i];//待插入的记录暂存到监视哨中
                sqList.getValue()[i] = sqList.getValue()[i - 1];//元素后移
                int j;
                //从后向前寻找插入位置 遍历已有序子表
                for (j = i - 1; j > -1 && temp < sqList.getValue()[j]; --j) {
                    sqList.getValue()[j + 1] = sqList.getValue()[j];//记录逐个后移,直到找到插入位置
                }
                sqList.getValue()[j + 1] = temp;//带插入记录插入到正确位置
            }
        }
    }

    /**
     * 折半插入排序 内部排序 插入排序 ok
     * 还是设第一个数有序，
     * 将元素插入已有序子表中，由于有序，插入点折半查找
     *
     * @param sqList
     */
    public static void bInsertSort(SqList sqList) {

        int i, j;
        int temp;
        for (i = 1; i < sqList.getLength(); ++i) {
            temp = sqList.getValue()[i];//将待插入的记录暂存到监视哨中
            //设置查找区间初值
            sqList.setLeftIndex(0);
            sqList.setRightIndex(i - 1);
            int mid;
            //在查找区间中折半查找插入的位置 缩小插入区间
            while (sqList.getLeftIndex() <= sqList.getRightIndex()) {
                mid = (sqList.getLeftIndex() + sqList.getRightIndex()) / 2;//折半
                //插入点在前一子表
                if (temp < sqList.getValue()[mid]) {
                    sqList.setRightIndex(mid - 1);
                }
                //插入点在后一子表
                else {
                    sqList.setLeftIndex(mid + 1);
                }
            }
            //后一子表记录后移
            for (j = i - 1; j >= sqList.getRightIndex() + 1; --j) {
                sqList.getValue()[j + 1] = sqList.getValue()[j];
            }
            //将temp即待插入元素插入到正确位置
            sqList.getValue()[sqList.getRightIndex() + 1] = temp;
        }
    }

    /**
     * 希尔排序辅助算法
     *
     * @param sqList
     * @param dk
     */
    public static void shellInsert(SqList sqList, int dk) {
        int i, j;
        int temp;
        for (i = dk ; i < sqList.getLength(); ++i) {
            //需将sqList.getValue()[i]插入有序增量子表
            if (sqList.getValue()[i] < sqList.getValue()[i - dk]) {
                temp = sqList.getValue()[i];//暂存在temp
                for (j = i - dk; j > -1 && temp < sqList.getValue()[j]; j -= dk) {
                    //记录后移,直到找到插入位置
                    sqList.getValue()[j + dk] = sqList.getValue()[j];
                }
                //将temp即原sqList.getValue()[i],插入到正确位置
                sqList.getValue()[j + dk] = temp;
            }
        }
    }

    /**
     * 希尔排序 内部排序 插入排序 难
     *
     * @param sqList
     * @param dt 希尔排序辅助数组 容量要大一些
     * @param t
     */
    public static void shellSort(SqList sqList, int[] dt, int t) {

        int k;
        int temp = sqList.getLength() - 1;
        for (k = 0; k < t; ++k) {
            temp = temp / 2;
            dt[k] = temp;
            //一趟增量为dt[t]的希尔插入排序
            shellInsert(sqList, dt[k]);
        }
    }

    /**
     * 冒泡排序，与选择排序相似 内部排序 交换排序 ok
     * 相邻的才比较
     * 时间复杂度O(n^2)
     */
    public static void bubbleSort(SqList sqList) {

        for (int i = 1; i < sqList.getLength(); i++) {
//            一趟下来最大的到最后了，不动了，第二趟下来倒数第二大的也固定了
            for (int j = 0; j < sqList.getLength() - i; j++) {
                if (sqList.getValue()[j] > sqList.getValue()[j + 1]) {
                    int temp = sqList.getValue()[j];
                    sqList.getValue()[j] = sqList.getValue()[j + 1];
                    sqList.getValue()[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 快速排序辅助算法
     *
     * @param sqList
     * @param leftIndex
     * @param rightIndex
     * @return
     */
    public static int partition(SqList sqList, int leftIndex, int rightIndex) {

        int temp = sqList.getValue()[leftIndex];//用子表的第一个记录做枢轴记录
        int pivot = sqList.getValue()[leftIndex];//枢轴记录保存在pivot中
        //从表的两端交替地向中间扫描
        while (leftIndex < rightIndex) {
            while (leftIndex < rightIndex && sqList.getValue()[rightIndex] >= pivot) {
                --rightIndex;
            }
            sqList.getValue()[leftIndex] = sqList.getValue()[rightIndex];//将比枢轴记录小的记录移到低端
            while (leftIndex < rightIndex && sqList.getValue()[leftIndex] <= pivot) {
                ++leftIndex;
            }
            sqList.getValue()[rightIndex] = sqList.getValue()[leftIndex];//将比枢轴记录大的记录移到高端
        }
        sqList.getValue()[leftIndex] = temp;//枢轴记录到位
        return leftIndex;//返回枢轴位置
    }

    /**
     * 快速排序 内部排序 交换排序
     * 是冒泡排序的改进
     * @param sqList
     * @param leftIndex 首个元素索引
     * @param rightIndex 最末元素索引
     */
    public static void quickSort(SqList sqList, int leftIndex, int rightIndex) {

        //调用前置初值: left=1;right=L.length;
        //对顺序表L中的子序列L.elem[left...right]做快速排序
        int pivotloc;
        //长度大于1
        if (leftIndex < rightIndex) {
            pivotloc = partition(sqList, leftIndex, rightIndex);//将L.elem[leftIndex...rightIndex]一分为二,pivotloc是枢轴位置
            quickSort(sqList, leftIndex, pivotloc - 1);//对左子表递归排序
            quickSort(sqList, pivotloc + 1, rightIndex);//对右子表递归排序
        }
    }

    /**
     * 简单选择排序 内部排序 选择排序 ok
     *
     * @param sqList
     */
    public static void selectSort(SqList sqList) {

        int i, j, k, temp;
        //在所有记录中选择最小的记录，一趟循环下来筛出最小的数
        for (i = 0; i < sqList.getLength() - 1; ++i) {
            k = i;
            //这个数组下标没有越界，见首层for循环
            for (j = i + 1; j < sqList.getLength(); ++j) {
                //k指向此趟排序中最小的记录
                if (sqList.getValue()[j] < sqList.getValue()[k]) {
                    k = j;
                }
            }
            if (k != i) {
                //交换sqList.getValue()[i]与sqList.getValue()[k]
                temp = sqList.getValue()[i];
                sqList.getValue()[i] = sqList.getValue()[k];
                sqList.getValue()[k] = temp;
            }
        }
    }

    /**
     * 堆排序辅助算法
     *
     * @param sqList
     * @param leftIndex
     * @param rightIndex
     */
    public static void heapAdjust(SqList sqList, int leftIndex, int rightIndex) {
        //假设L.elem[left+1...right]已经是堆,将L.elem[left...right]调整为以L.elem[left]为根的大根堆
        int rc, j;
        rc = sqList.getValue()[leftIndex];
        for (j = 2 * leftIndex; j <= rightIndex; j *= 2) {
            if (j < rightIndex && sqList.getValue()[j] < sqList.getValue()[j + 1])//j为较大的记录的下标
            {
                ++j;
            }
            if (rc >= sqList.getValue()[j])//rc应插入在位置right上
            {
                break;
            }
            sqList.getValue()[leftIndex] = sqList.getValue()[j];
            leftIndex = j;
        }
        sqList.getValue()[leftIndex] = rc;//插入
    }

    /**堆排序辅助算法
     * @param sqList
     */
    public static void creatHeap(SqList sqList) {

        //把无序序列L.elem[1...L.right]建成大根堆
        int n, i;
        n = sqList.getLength() - 1;
        //反复调用my_heapadjust函数
        for (i = n / 2; i > -1; --i) {
            heapAdjust(sqList, i, n);
        }
    }

    /**
     * 堆排序 内部排序 选择排序
     *
     * @param sqList
     */
    public static void heapSort(SqList sqList) {

        //对顺序表L进行堆排序
        int i, temp;
        creatHeap(sqList);//把无序序列sqList.getValue()[1...L.right]建成大根堆
        for (i = sqList.getLength() - 1; i > 0; --i) {
            temp = sqList.getValue()[0];//将堆顶记录和当前未经排序子序列sqList.getValue()[0...i]中最后一个记录互换
            sqList.getValue()[0] = sqList.getValue()[i];
            sqList.getValue()[i] = temp;
            heapAdjust(sqList, 0, i - 1);//将sqList.getValue()[1...i-1]重新调整为大根堆
        }
    }

    /**
     * 2-路归并排序辅助算法
     *
     * @param sqList
     * @param leftIndex
     * @param midIndex
     * @param rightIndex
     */
    public static void merge(SqList sqList, int leftIndex, int midIndex, int rightIndex) {

        int i = leftIndex;
        int j = midIndex + 1;
        int k = leftIndex;
        int[] temp = new int[sqList.getLength()];
        while (i <= midIndex && j <= rightIndex) {
            if (sqList.getValue()[i] < sqList.getValue()[j]) {
                temp[k++] = sqList.getValue()[i++];
            } else {
                temp[k++] = sqList.getValue()[j++];
            }
        }
        while (i <= midIndex) {
            temp[k++] = sqList.getValue()[i++];
        }
        while (j <= rightIndex) {
            temp[k++] = sqList.getValue()[j++];
        }
        while (leftIndex <= rightIndex) {
            sqList.getValue()[leftIndex] = temp[leftIndex++];
        }
    }

    /**
     * 2-路归并排序 内部排序 归并排序
     *
     * 时间复杂度O(nlogn) 空间复杂度 O(n) 是稳定排序
     * @param sqList
     * @param leftIndex
     * @param rightIndex
     */
    public static void mergeSort(SqList sqList, int leftIndex, int rightIndex) {

        if (leftIndex >= rightIndex) {
            return;
        } else {
            int mid = (leftIndex + rightIndex) / 2;
            mergeSort(sqList, leftIndex, mid);
            mergeSort(sqList, mid + 1, rightIndex);
            merge(sqList, leftIndex, mid, rightIndex);
        }
    }
    /**
     * 插入元素仍有序
     */
    public static void insertSort(SqList sqList, int value) {
        int[] newSqList = new int[sqList.getLength() + 1];
        for (int i = 0; i < sqList.getLength(); i++) {
            newSqList[i] = sqList.getValue()[i];
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
        sqList.setValue(newSqList);
    }
}
