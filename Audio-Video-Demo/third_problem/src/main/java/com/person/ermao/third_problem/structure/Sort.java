package com.person.ermao.third_problem.structure;

import java.util.Random;

/**
 *
 * @author 冯飞杨
 * @date 2019/6/4
 * 当N很小时，快速排序慢，归并排序快
 * 当N很大时，并且有序程度高时，快速排序最快
 * 当N很大时，并且有序程序低时，堆排序最快
 */
public class Sort {
    //快排，一端遍历
    private static void qsort(int[] arr) {
        __qsort(arr, 0, arr.length - 1);
    }

    private static void __qsort(int[] arr, int l, int h) {
        if (l >= h) {
            return;
        }
        int key = arr[l];
        int j = l;
        for (int i = l + 1; i <= h; i++) {
            if (key > arr[i]) {
                if (i != j + 1) {
                    int temp = arr[i];
                    arr[i] = arr[++j];
                    arr[j] = temp;
                } else {
                    j++;
                }
            }
        }
        int temp = arr[j];
        arr[j] = arr[l];
        arr[l] = temp;
        __qsort(arr, l, j - 1);
        __qsort(arr, j + 1, h);
    }

    //快排改进版，双端遍历
    private static void qsortNew(int[] arr) {
        __qsortNew(arr, 0, arr.length - 1);
    }

    private static void __qsortNew(int[] arr, int l, int h) {
        if (l >= h) return;
        int key = arr[l];
        int i = l;
        int j = h + 1;
        while (true) {
            while (arr[++i] <= key) {
                if (i == h) {
                    break;
                }
            }
            while (arr[--j] > key) {
                if (j == l) {
                    break;
                }
            }

            if (i >= j) {
                break;
            }
            int temp = arr[j];
            arr[j] = arr[i];
            arr[i] = temp;
        }
        int temp = arr[l];
        arr[l] = arr[j];
        arr[j] = temp;
        __qsortNew(arr, l, j - 1);
        __qsortNew(arr, j + 1, h);
    }

    //改进版冒泡排序
    private static void msort(int arr[]) {
        for (int i = 0; i < arr.length - 1; i++) {
            boolean flag = true;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    flag = false;
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
            if (flag) {
                break;
            }
        }
    }

    private static void gsort(int arr[]) {
        __gsort(arr, 0, arr.length - 1);
    }

    private static void __gsort(int[] arr, int low, int high) {
        if (low < high) {
            int mid = (high + low) / 2;
            __gsort(arr, low, mid);
            __gsort(arr, mid + 1, high);
            merge(arr, low, mid, high);
        }
    }

    private static void merge(int[] arr, int low, int mid, int hight) {
        int[] temp = new int[arr.length];
        int t = 0;
        int l = low;
        int r = mid + 1;
        while (l <= mid && r <= hight) {
            if (arr[l] < arr[r]) {
                temp[t++] = arr[l++];
            } else {
                temp[t++] = arr[r++];
            }
        }
        while (l <= mid) {
            temp[t++] = arr[l++];
        }
        while (r <= hight) {
            temp[t++] = arr[r++];
        }
        t = 0;
        while (low <= hight) {
            arr[low++] = temp[t++];
        }
    }

    public static void main(String[] args) {
        int n = 100000;
        int[] arr = new int[n];
        int[] arr1 = new int[n];
        int[] arr2 = new int[n];
        int[] arr3 = new int[n];
//        int[] arr = new int[]{54,1,40,81,61,68};
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(100);
            arr1[i] = arr[i];
            arr2[i] = arr[i];
            arr3[i] = arr[i];
        }
        long qSortTime = System.currentTimeMillis();
        qsort(arr);
        System.out.println("qsort time:" + (System.currentTimeMillis() - qSortTime));

        long qSortNewTime = System.currentTimeMillis();
        qsortNew(arr1);
        System.out.println("qsort new time:" + (System.currentTimeMillis() - qSortNewTime));

//        long mSortTime = System.currentTimeMillis();
//        msort(arr2);
//        System.out.println("msort new time:" + (System.currentTimeMillis() - mSortTime));

        long gSortTime = System.currentTimeMillis();
        gsort(arr3);
        System.out.println("gsort new time:" + (System.currentTimeMillis() - gSortTime));
        int i =0;
        for (; i < n; i++) {
            if (arr3[i] != arr[i]) {
                System.out.println("算法失败！");
                break;
            }
        }
        if (i == n)
            System.out.println("算法成功！");
    }
}
