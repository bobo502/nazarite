package com.nazarite.arithmetic;

/**
 * @author bobo
 */
public class InsertSort {

    /**
     * 插入排序
     * 最外层的是待排序的数字，里面的while是已经排好的数字，每一次循环都是将最外层的数插入到排好序的队列里面
     * O(n^2)
     * @param arr
     */
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {            // 第一层循环，控制待插入元素的位置，假定第一个元素已排好序
            int key = arr[i];                   // 待插入元素
            int j = i - 1;                      // 已排好序的元素的最后一个元素的下标
            // 第二层循环，将待插入元素插入到已排好序的序列中
            while (j >= 0 && arr[j] > key) {    // 向前查找插入位置
                arr[j+1] = arr[j];              // 如果当前比较元素大于待插入元素，则将元素向后移动一位
                j--;
            }
            // 将待插入元素插入到正确的位置
            arr[j+1] = key;
        }
    }


    public static void main(String[] args) {
        int[] arr = {30,3,23,4,45,6,3,7};
        insertionSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }

    }
}
