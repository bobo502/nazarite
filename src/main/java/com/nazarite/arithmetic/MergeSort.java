package com.nazarite.arithmetic;

import java.util.Arrays;

/**
 * 归并排序
 */
public class MergeSort {

    public static void mergeSort(int[] arr, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSort(arr, start, mid);
            mergeSort(arr, mid+1, end);
            merge(arr, start, mid, end);
        }
    }

    public static void merge(int[] arr, int start, int mid, int end) {
        int[] temp = new int[arr.length];
        int leftIndex = start;
        int rightIndex = mid+1;
        int tempIndex = start;

        while (leftIndex <= mid && rightIndex <= end) {
            if (arr[leftIndex] < arr[rightIndex]) {
                temp[tempIndex++] = arr[leftIndex++];
            } else {
                temp[tempIndex++] = arr[rightIndex++];
            }
        }

        while (leftIndex <= mid) {
            temp[tempIndex++] = arr[leftIndex++];
        }

        while (rightIndex <= end) {
            temp[tempIndex++] = arr[rightIndex++];
        }

        for (int i = start; i <= end; i++) {
            arr[i] = temp[i];
        }
    }

    public static void main(String[] args) {
        int[] arr = {5, 9, 2, 1, 6, 4, 7, 3, 8};
        mergeSort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }
}