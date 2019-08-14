package zym.sort.array;

import java.util.Arrays;

public class SelectSort {
    public void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = arr[i];
            int minIndex = i;
            for (int j = i; j < arr.length; j++) {
                if (min >= arr[j]) {
                    minIndex = j;
                    min = arr[j];
                }
            }
            swap(arr, i, minIndex);
        }
    }

    private void swap(int[] arr, int i, int minIndex) {
        int temp = arr[i];
        arr[i] = arr[minIndex];
        arr[minIndex] = temp;
    }

    public static void main(String[] args) {
        SelectSort selectSort = new SelectSort();

        int[] arr = {1, 9, 2, 2, 4};
        int[] arr1 = {1, 9, 2, 6, 4};
        int[] arr3 = {1, 2, 2, 1, 4};
        selectSort.sort(arr);
        selectSort.sort(arr1);
        selectSort.sort(arr3);
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr3));

    }
}
