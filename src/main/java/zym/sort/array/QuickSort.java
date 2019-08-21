package zym.sort.array;

import java.util.Arrays;

public class QuickSort extends BaseSort {

    public void sort(int[] arr, int start, int end) {
        if (start < end) {
            //分割数组并返回分割的下标
            int partion=partionAndReturnIndex(arr, start, end);
            sort(arr, start, partion);
            sort(arr, partion + 1, end);
        }
    }

    private int partionAndReturnIndex(int[] arr, int start, int end) {
        //找到基准值
        int benmark = arr[start];
        int low = start;
        int high = end;
        //从后往前
        while (low < high) {

            while (low < high && benmark <= arr[high]) {
                high--;
            }
            arr[low] = arr[high];
            while (low < high && benmark >= arr[high]) {
                low++;
            }
            arr[high] = arr[high];
        }
        arr[low] = benmark;

        return low;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 9, 0,0};
        QuickSort quickSort = new QuickSort();
        quickSort.sort(arr,0,  arr.length-1);
        System.out.println(Arrays.toString(arr));
    }
}
