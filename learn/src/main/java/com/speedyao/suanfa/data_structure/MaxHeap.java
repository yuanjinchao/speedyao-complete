package com.speedyao.suanfa.data_structure;

public class MaxHeap {
    private int[] arr;

    public MaxHeap(int[] arr) throws Exception {
        if(arr.length<3){
            throw new Exception("arr length must >=3");
        }
        this.arr = arr;
        build();
    }

    public void build(){
        for(int i=0;i<(arr.length-1)/2;i++){
            shiftDown(0);
        }
    }

    public void insert(int value){
        int index=arr.length-1;
        if(value>arr[index]){
            arr[index]=value;
            shiftUp(index);
        }
    }

    public void remove(){
        int[] tmp=new int[arr.length-1];
        swap(0,arr.length-1);
        for(int i=0;i<tmp.length;i++){
            tmp[i]=arr[i];
        }
        arr=tmp;
        shiftDown(0);
    }

    private void shiftUp(int i){
        if(i==0){
            return;
        }
        int parentIndex=(i-1)/2;
        if(arr[i]>arr[parentIndex]){
            swap(i,parentIndex);
        }
        shiftUp(parentIndex);
    }

    private void shiftDown(int i){
        if(i>=arr.length){
            return;
        }
        int childIndex=-1;
        if(i*2+2<arr.length){
            childIndex=arr[i*2+1]>arr[i*2+2]?i*2+1:i*2+2;
        }else if(i*2+1<arr.length){
            childIndex=i*2+1;
        }else{
            return;
        }
        if(arr[childIndex]>arr[i]){
            swap(childIndex,i);
            shiftDown(i*2+1);
            shiftDown(i*2+2);
        }
    }
    private void swap(int i1,int i2){
        int tmp=arr[i1];
        arr[i1]=arr[i2];
        arr[i2]=tmp;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        int n=0;
        for(int i=0;i<arr.length;i++){
            sb.append(arr[i]).append(",");
            if(i==((2<<n)-2)){
                sb.append("\r\n");
                n++;
            }
        }
        return sb.toString();
    }
}
