package com.fastcampus.ch3.aop;

import org.springframework.stereotype.Component;

@Component
public class MyMath {
    public int add(int a, int b){
        int res = a+b;
        return res;
    }
    public int add(int a, int b,int c){
        int res = a+b+c;
        return res;
    }
    public int subtract(int a, int b){
        int res = a-b;
        return res;
    }
    public int multiply(int a, int b){
        int res = a*b;
        return res;
    }
}
