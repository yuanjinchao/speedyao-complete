package com.speedyao.thread.forkjoin;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class SortTest {

    @Test
    public void testSort() throws ExecutionException, InterruptedException {
        int length=10000;
        int[] arr=new int[length];
        Random random=new Random();
        for(int i=0;i<length;i++){
            int value = random.nextInt(length);
            arr[i]=value;
        }
        System.out.println(Arrays.toString(arr));
        int[] arr1 = Arrays.copyOf(arr, arr.length);
        System.out.println(Arrays.toString(arr1));
        long start = System.currentTimeMillis();
        forkJoinSort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println("用时："+(System.currentTimeMillis()-start));
        start = System.currentTimeMillis();
        insertSort(arr1);
        System.out.println(Arrays.toString(arr1));
        System.out.println("用时："+(System.currentTimeMillis()-start));

    }

    private int[] forkJoinSort(int[] arr) throws InterruptedException, ExecutionException {
        SortForkJoinTask task=new SortForkJoinTask(arr);
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        ForkJoinTask<int[]> future = forkJoinPool.submit(task);
        return future.get();
    }


    public void insertSort(int[] arr){
        for(int i=1;i<arr.length;i++){
            int startIndex=i-1;
            int insertValue=arr[i];
            while(startIndex>=0&&arr[startIndex]>insertValue){
                arr[startIndex+1]=arr[startIndex];
                startIndex--;
            }
            arr[startIndex+1]=insertValue;
        }
    }

}
