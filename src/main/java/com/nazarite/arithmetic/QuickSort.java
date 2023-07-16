package com.nazarite.arithmetic;

public class QuickSort {
    /**
     * 快速排序
     * @param arr
     * @param low
     * @param high
     */
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high);   // 划分数组，找到枢轴所在位置
            quickSort(arr, low, pivot - 1);           // 递归调用，对枢轴左边的子序列进行快速排序
            quickSort(arr, pivot + 1, high);          // 递归调用，对枢轴右边的子序列进行快速排序

        }
    }

    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[low];                       // 以序列第一个元素为枢轴
        while (low < high) {
            while (low < high && arr[high] >= pivot) {
                high--;
            }
            arr[low] = arr[high];                   // 将小于枢轴的元素放入到枢轴的左边
            while (low < high && arr[low] <= pivot) {
                low++;
            }
            arr[high] = arr[low];                   // 将大于枢轴的元素放入到枢轴的右边
        }
        arr[low] = pivot;                           // 将枢轴元素放到最终位置
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+"，");
        }
        System.out.println("\n");
        return low;
    }

    public static void main(String[] args) {
        int[] arr = {30,3,23,4,45,6,3,7};
        quickSort(arr,0,arr.length-1);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }

    }
}
