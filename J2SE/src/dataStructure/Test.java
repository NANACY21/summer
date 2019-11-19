package dataStructure;

import dataStructure.data.SqList;

public class Test {
    @org.junit.Test
    public void SortTest() {

        SqList sqList = new SqList(6, 4, 100, 10, 8, 5);
//        Sort.insertSort(sqList);
//        Sort.bInsertSort(sqList);
//        Sort.shellSort(sqList, new int[100], (int) (Math.log(sqList.getLength() + 1) / Math.log(2)));
//        Sort.quickSort(sqList,0,3);
//        Sort.selectSort(sqList);
//        Sort.heapSort(sqList);
//        Sort.mergeSort(sqList,0,5);
        Sort.bubbleSort(sqList);
        System.out.println(sqList.toString());
    }

    @org.junit.Test
    public void Search() {

    }
}
