package com.nazarite.arithmetic;

import java.util.Arrays;

public class BubbleSort {
    /**
     *  冒泡排序
     *  最外层的每一次循环会把最大值交换到最右边，下一次循环会跳过这个值的下标
     *  时间复杂度 O(n^2)
     * @param arr
     */
    public static void bubbleSort(int[] arr){
        int length = arr.length;
        for (int i = 0; i < length-1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if(arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {5, 9, 2, 1, 6, 4, 7, 3, 8};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

}
