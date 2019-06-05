package com.person.ermao.third_problem.structure;

import java.util.Random;

/**
 * 以数组实现一个最大堆
 *
 * @author 冯飞杨
 * @date 2019/6/5
 */
public class MaxHeap {
    private int[] data;
    private int count;

    public MaxHeap(int capacity) {
        this.data = new int[capacity + 1];
        count = 0;
    }

    public int heapSize() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void insert(int item) {
        if (count + 1 > data.length - 1) {
            throw new IndexOutOfBoundsException();
        }
        data[count + 1] = item;
        shiftUp(count + 1);
        count++;
    }

    private void shiftUp(int i) {
        while (i / 2 >= 1 && data[i / 2] < data[i]) {
            int temp = data[i / 2];
            data[i / 2] = data[i];
            data[i] = temp;
            i /= 2;
        }
    }

    public int remove() {
        int ret = data[1];
        data[1] = data[count];
        count--;
        shiftDown(1);
        return ret;
    }

    private void shiftDown(int i) {
        while (i * 2 <= count) {
            int j = i * 2;
            if (j + 1 <= count && data[j + 1] > data[j]) {
                j += 1;
            }
            if (data[i] < data[j]) {
                int temp = data[i];
                data[i] = data[j];
                data[j] = temp;
            }
            i = j;
        }
    }

    public static void main(String[] args) {
        int capacity = 18;
        MaxHeap maxHeap = new MaxHeap(capacity);
        Random random = new Random();
        for (int i = 0; i < capacity; i++) {
            int i1 = random.nextInt(20);
//            System.out.println(i1);
            maxHeap.insert(i1);
        }
        System.out.println("--------------------------------");
        for (int i = 1; i < maxHeap.data.length; i++) {
            System.out.println(maxHeap.data[i]);
        }
        System.out.println("--------------------------------" + maxHeap.heapSize());
        for (int i = 0; i < maxHeap.data.length - 1; i++) {
            System.out.println(maxHeap.remove());
        }

    }
}
