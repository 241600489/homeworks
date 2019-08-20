package zym.sort.array;

import java.util.Arrays;

public class HeapSort extends BaseSort {
    public void buildHeap(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            // 当前下标
            int currentIndex = i;
            //父下标
            int parentIndex = (currentIndex - 1) / 2;

            while (arr[parentIndex] < arr[currentIndex]) {
                swap(arr, currentIndex, parentIndex);
                currentIndex = parentIndex;
                parentIndex = (currentIndex - 1) / 2;
            }
        }
    }

    public void heapSort(int[] arr) {
        //构建堆
        buildHeap(arr);

        //固定最大值，然后调整堆

        for (int i = arr.length - 1; i >= -0; i--) {
            //将最大值调到末尾
            swap(arr, i, 0);

            //重新调整大根堆
            modifyHeap(arr, i);
        }
    }

    private void modifyHeap(int[] arr, int size) {
        for (int i = 0; i < size; i++) {
            int leftIndex = 2 * i + 1;
            int rightIndex = 2 * i + 2;
            if (leftIndex < size && rightIndex < size) {

                if (arr[leftIndex] > arr[i]) {
                    if (arr[leftIndex] < arr[rightIndex]) {
                        swap(arr, rightIndex, i);
                    } else {
                        swap(arr, leftIndex, i);
                    }
                } else if (arr[rightIndex] > arr[i]) {
                    swap(arr, rightIndex, i);
                }

            } else if (leftIndex < size ) {
                if (arr[leftIndex] > arr[i]) {
                    swap(arr, leftIndex, i);
                }
            }
        }
    }

    public static void main(String[] args) {
        HeapSort heapSort = new HeapSort();
        int[] arr = {1, 5, 9, 10, 6};
        int[] arr1 = {3, 6, 8, 5, 7};
        heapSort.buildHeap(arr);
        heapSort.buildHeap(arr1);

        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(arr1));
        int[] arr2 = {1, 9, 10, 8, 3};
        int[] arr3 = {3, 5, 2, 9, 4, 10};
        heapSort.heapSort(arr2);
        heapSort.heapSort(arr3);
        System.out.println(Arrays.toString(arr2));
        System.out.println(Arrays.toString(arr3));

    }

}
