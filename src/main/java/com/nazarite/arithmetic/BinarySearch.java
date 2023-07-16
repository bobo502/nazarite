package com.nazarite.arithmetic;

/**
 * binarySearch()方法是二分查找的主要方法，该方法使用左右两个指针left和right分别指向数组的最左边和最右边，
 * 使用一个循环不断缩小指针范围，直到找到目标元素或者左右指针相遇。在每一次循环中，首先计算中间指针mid的值，
 * 然后将目标元素与mid指针所指向的元素进行比较，如果相同则返回mid，如果目标元素比mid小，则将右指针right向左移动一位，缩小查找范围，
 * 否则将左指针left向右移动一位。最终，如果整个数组遍历完了仍然没有找到目标元素，则返回-1表示没找到。
 * main()方法中使用该方法在有序数组中查找目标元素6在数组中的位置，并输出结果。
 */
public class BinarySearch {
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length-1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int target = 6;
        int index = binarySearch(arr, target);
        if (index == -1) {
            System.out.println("Element not found in the array");
        } else {
            System.out.printf("Element found at index: %d", index);
        }
    }
}
