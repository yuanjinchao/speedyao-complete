package com.speedyao.suanfa.sort;

import java.util.Arrays;

public class InsertSort {

    public static void main(String[] args) {
        int[] arr=new int[]{2,1,6,23,12,86,32,123,9,22,31,6,2,3,232,12,1,5,67,7,8};
        int count=0;
        for(int i=1;i<arr.length;i++){
            int startIndex=i-1;
            int insertValue=arr[i];
            while(startIndex>=0&&arr[startIndex]>insertValue){
                arr[startIndex+1]=arr[startIndex];
                startIndex--;
                count++;
            }
            arr[startIndex+1]=insertValue;
        }
        System.out.println(Arrays.toString(arr));
        System.out.println("len: "+arr.length+",count:"+count);
    }


}
