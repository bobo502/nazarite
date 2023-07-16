package com.nazarite.arithmetic;

import java.util.Arrays;

public class SelectSort {
    /**
     * 选择排序，和冒泡排序差不多，但只是交换的是下标，而不是元素
     * O(n^2)
     * @param arr
     */
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {     // 控制排序的轮数，一共需要 n-1 轮
            int minIndex = i;                // 记录最小值的下标
            for (int j = i + 1; j < n; j++) { // 在未排序的部分中寻找最小值的下标
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // 将最小值与未排序部分的首元素进行交换
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }
    public static void main(String[] args) {
        int[] arr = {5, 9, 2, 1, 6, 4, 7, 3, 8};
        selectionSort(arr);
        System.out.println(Arrays.toString(arr));
    }

}
