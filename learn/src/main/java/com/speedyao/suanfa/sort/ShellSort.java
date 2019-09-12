package com.speedyao.suanfa.sort;

import java.util.Arrays;

public class ShellSort {
    public static void main(String[] args) {
        int[] arr=new int[]{1,6,23,12,86,32,123,9,22,31,6,2,3,232,12,1,5,67,7,8};
        int count=0;
        for(int gap=arr.length/2;gap>0;gap/=2){
            for(int i=gap;i<arr.length;i+=gap){
                int insertValue=arr[i];
                int startIndex=i-gap;
                while(startIndex>=0&&arr[startIndex]>insertValue){
                    arr[startIndex+gap]=arr[startIndex];
                    startIndex-=gap;
                    count++;
                }
                arr[startIndex+gap]=insertValue;
            }
        }
        System.out.println(Arrays.toString(arr));
        System.out.println("len: "+arr.length+",count: "+count);
    }
}
