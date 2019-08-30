package com.speedyao.suanfa.data_structure;

public class HeapTestMain {
    public static void main(String[] args) throws Exception {

        int[]arr=new int[]{1,3,4,2,7,5,9,11,-2,88,23,23,90,12};
        MaxHeap maxHeap=new MaxHeap(arr);
        System.out.println(maxHeap);
        maxHeap.insert(24);
        System.out.println(maxHeap);
        maxHeap.remove();
        System.out.println(maxHeap);
    }

}
