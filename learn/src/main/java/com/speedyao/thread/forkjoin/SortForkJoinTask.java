package com.speedyao.thread.forkjoin;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class SortForkJoinTask extends RecursiveTask<int[]> {
    static final int MIN_LEN=2;
    int arr[];
    public SortForkJoinTask(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected int[] compute() {
        if(arr.length>MIN_LEN){
            int[] arr1 = Arrays.copyOfRange(arr, 0, arr.length / 2 );
            SortForkJoinTask task1=new SortForkJoinTask(arr1);
            int[] arr2 = Arrays.copyOfRange(arr, arr.length / 2, arr.length);
            SortForkJoinTask task2=new SortForkJoinTask(arr2);
            task1.fork();
            task2.fork();
            int[] rs1 = task1.join();
            int[] rs2 = task2.join();
            int index1=0;
            int index2=0;
            int subIndex=0;
            while(index1<rs1.length&&index2<rs2.length){
                if(rs1[index1]<rs2[index2]){
                    arr[subIndex++]=rs1[index1++];
                }else{
                    arr[subIndex++]=rs2[index2++];
                }
            }
            int[] tmpArr=null;
            int startIndex=0;
            if(index1<rs1.length){
                tmpArr=rs1;
                startIndex=index1;
            }else if(index2< rs2.length){
                tmpArr=rs2;
                startIndex=index2;
            }
            if(tmpArr!=null){
                for(int i=startIndex;i<tmpArr.length;i++){
                    arr[subIndex++]=tmpArr[i];
                }
            }
            return arr;
        }else if(arr.length==MIN_LEN){
            if(arr[0]>arr[1]){
                int tmp=arr[0];
                arr[0]=arr[1];
                arr[1]=tmp;
            }
            return arr;
        }else{
            return arr;
        }
    }
}
