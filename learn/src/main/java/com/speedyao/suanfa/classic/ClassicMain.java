package com.speedyao.suanfa.classic;

public class ClassicMain {

    public static void main(String[] args) {

        for(int i=0;i<100;i++){
            if(isPrime(i)){
                System.out.println(i);
            }
        }

    }

    public static boolean isPrime(int n){
        if(n<2)return true;
        for(int i=2;i*i<=n;i++){
            if(n%i==0) return false;
        }
        return  true;
    }
}


