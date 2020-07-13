package com.speedyao.thread.forkjoin;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {

    class SumForkJoinTask extends RecursiveTask<Integer>{
        int max=100000;
        int[] arr;
        int startIndex;
        int endIndex;

        public SumForkJoinTask(int[] arr, int startIndex, int endIndex) {
            this.arr = arr;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        protected Integer compute() {
            if(endIndex-startIndex< max){
                int sumValue=0;
                for(int i=startIndex;i<=endIndex;i++){
                    sumValue+=arr[i]%2;
                }
                return sumValue;
            }else{
                SumForkJoinTask task1=new SumForkJoinTask(arr,startIndex,(endIndex+startIndex)/2);
                SumForkJoinTask task2=new SumForkJoinTask(arr,(endIndex+startIndex)/2+1,endIndex);
                task1.fork();
                task2.fork();
                return task1.join()+task2.join();

            }
        }
    }

    @Test
    public void testSum() throws ExecutionException, InterruptedException {
        int length=100000000;
        int[] arr=new int[length];
        int sum=0;
        Random random=new Random();
        for(int i=0;i<length;i++){
            int value = random.nextInt(10);
            arr[i]=value;
        }

        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        SumForkJoinTask task=new SumForkJoinTask(arr,0,length-1);
        ForkJoinTask<Integer> submit = forkJoinPool.submit(task);
        System.out.println(submit.get());
        System.out.println(System.currentTimeMillis()-start);
        start = System.currentTimeMillis();
        for(int value:arr){
            sum+=value%2;
        }
        System.out.println(sum);
        System.out.println(System.currentTimeMillis()-start);

    }
}
